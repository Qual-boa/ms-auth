package com.qualaboa.msauth.dataContract.dtos.Access;

import com.qualaboa.msauth.dataContract.dtos.establishment.CategoryResponseDTO;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Data
public class DashboardDataDTO {
    private Double averageClicksPerMonth;
    private Double rate;
    private List<Map<String, Object>> clicksPerDayLast7Days;
    private List<Map<String, Object>> dayOfWeekWithMostClicks;
    private List<Map<String, Object>> findHourWithMostClicks;
    private Integer favoriteCount;
    private List<CategoryResponseDTO> categoriesSearches;

    public String toCSV() {
        StringBuilder sb = new StringBuilder();

        // Headers
        sb.append("Average Clicks Per Month,Rate");
        clicksPerDayLast7Days.forEach(day -> {
            LocalDate date = (LocalDate) day.get("date");
            sb.append(",Clicks at ").append(date);
        });
        sb.append(",dayOfWeekWithMostClicks,HourWithMostClicks,favoriteCount");
        categoriesSearches.forEach(cat -> sb.append(",").append(cat.getName()));
        sb.append("\n");

        // Data
        StringJoiner sj = new StringJoiner(",");
        sj.add(String.valueOf(averageClicksPerMonth))
                .add(String.valueOf(rate));
        clicksPerDayLast7Days.forEach(day -> sj.add(day.get("count").toString()));

        // Find the day of the week with the most clicks
        String mostClicksDay = dayOfWeekWithMostClicks.stream()
                .max((day1, day2) -> Long.compare((Long) day1.get("count"), (Long) day2.get("count")))
                .map(day -> day.get("dayOfWeek").toString().trim())
                .orElse("");

        // Find the hour with the most clicks
        String mostClicksHour = findHourWithMostClicks.stream()
                .max((hour1, hour2) -> Long.compare((Long) hour1.get("count"), (Long) hour2.get("count")))
                .map(hour -> hour.get("hour").toString())
                .orElse("");

        sj.add(mostClicksDay);
        sj.add(mostClicksHour);
        sj.add(String.valueOf(favoriteCount));

        categoriesSearches.forEach(cat -> sj.add(String.valueOf(cat.getSearchesCount())));

        sb.append(sj.toString()).append("\n");
        return sb.toString();
    }
}
