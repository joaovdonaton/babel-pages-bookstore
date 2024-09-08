package edu.kent.babelpages.rest.statistics;

import edu.kent.babelpages.rest.statistics.DTO.GeneralStatsDTO;
import org.springframework.stereotype.Service;

@Service
public class StatisticsService {
    final private StatisticsDAO statisticsDAO;

    public StatisticsService(StatisticsDAO statisticsDAO) {
        this.statisticsDAO = statisticsDAO;
    }

    public GeneralStatsDTO getGeneralStats() {
        return new GeneralStatsDTO(
                statisticsDAO.countReviews(),
                statisticsDAO.countUsers(),
                statisticsDAO.countBooks(),
                statisticsDAO.computeMostCommonGenre()
        );
    }
}
