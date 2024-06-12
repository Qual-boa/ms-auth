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

    @Query("SELECT CAST(ac.accessedAt AS LocalDate) AS date, COUNT(ac) AS count " +
            "FROM AccessCounter ac " +
            "WHERE ac.accessedAt >= :startDate " +
            "GROUP BY CAST(ac.accessedAt AS LocalDate) " +
            "ORDER BY CAST(ac.accessedAt AS LocalDate)")
    List<Map<String, Object>> findClicksPerDayLast7Days(LocalDateTime startDate);
}
