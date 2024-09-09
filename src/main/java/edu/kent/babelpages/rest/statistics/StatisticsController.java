package edu.kent.babelpages.rest.statistics;

import edu.kent.babelpages.rest.statistics.DTO.GeneralStatsDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stats/")
public class StatisticsController {
    final private StatisticsService service;

    public StatisticsController(StatisticsService service) {
        this.service = service;
    }

    @GetMapping("/")
    @Tag(name = "Statistics")
    @Operation(
            summary = "Get general statistic numbers from database."
    )
    public GeneralStatsDTO getStats(){
        return service.getGeneralStats();
    }
}
