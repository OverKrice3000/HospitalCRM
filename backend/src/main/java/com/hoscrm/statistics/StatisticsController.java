package com.hoscrm.statistics;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path="api/statistics")
public class StatisticsController {

    private StatisticsServiceImpl sServ;

    public StatisticsController(StatisticsServiceImpl sServ){
        this.sServ = sServ;
    }

    @GetMapping(path = "get")
    public ResponseEntity<?> getStatistics(){
        return ResponseEntity.ok(sServ.getStatistics());
    }

    @GetMapping(path="collect")
    public ResponseEntity<?> collectStatistics(){
        return ResponseEntity.ok(sServ.collectStatistics());
    }
}
