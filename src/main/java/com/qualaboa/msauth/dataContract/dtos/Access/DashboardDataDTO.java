package com.qualaboa.msauth.dataContract.dtos.Access;

import com.qualaboa.msauth.dataContract.dtos.establishment.CategoryResponseDTO;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class DashboardDataDTO {
    private Double averageClicksPerMonth;
    private List<Object[]> clicksPerDayLast30Days;
    private List<Map<String, Object>> dayOfWeekWithMostClicks;
    private List<Map<String, Object>> findHourWithMostClicks;
    private Integer favoriteCount;
    List<CategoryResponseDTO> categoriesSearches;
}
