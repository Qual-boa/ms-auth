package com.qualaboa.msauth.repositories;

import com.qualaboa.msauth.dataContract.entities.AccessCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface AccessCounterRepository extends JpaRepository<AccessCounter, UUID> {
    List<AccessCounter> findAllByEstablishmentId(UUID establishmentId);

    @Query("SELECT AVG(monthlyClicks.count) " +
            "FROM (SELECT COUNT(ac) AS count " +
            "FROM AccessCounter ac " +
            "GROUP BY FUNCTION('MONTH', ac.accessedAt)) AS monthlyClicks")
    Double findAverageClicksPerMonth();

    @Query("SELECT DATE_TRUNC('day', ac.accessedAt) AS date, COALESCE(COUNT(ac), 0) AS count " +
            "FROM AccessCounter ac " +
            "WHERE ac.accessedAt >= :startDate " +
            "GROUP BY DATE_TRUNC('day', ac.accessedAt) " +
            "ORDER BY DATE_TRUNC('day', ac.accessedAt)")
    List<Object[]> findClicksPerDayLast30Days(@Param("startDate") LocalDateTime startDate);

    @Query("SELECT FUNCTION('DAY_OF_WEEK', ac.accessedAt) AS dayOfWeek, COUNT(ac) AS count " +
            "FROM AccessCounter ac " +
            "GROUP BY FUNCTION('DAY_OF_WEEK', ac.accessedAt) " +
            "ORDER BY COUNT(ac) DESC")
    List<Map<String, Object>> findDayOfWeekWithMostClicks();

    @Query("SELECT FUNCTION('HOUR', ac.accessedAt) AS hour, COUNT(ac) AS count " +
            "FROM AccessCounter ac " +
            "GROUP BY FUNCTION('HOUR', ac.accessedAt) " +
            "ORDER BY COUNT(ac) DESC")
    List<Map<String, Object>> findHourWithMostClicks();

    @Query("""
            SELECT ac.accessedAt, COUNT(ac) \
            FROM AccessCounter ac \
            WHERE ac.accessedAt BETWEEN :startDate AND :endDate \
            GROUP BY ac.accessedAt \
            ORDER BY ac.accessedAt DESC""")
    List<Object[]> findClicksPerDay(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
