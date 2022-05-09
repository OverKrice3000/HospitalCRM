package com.hoscrm.statistics;

import java.util.List;

public interface StatisticsService {
    List<Statistics> getStatistics();
    List<Statistics> collectStatistics();
}
