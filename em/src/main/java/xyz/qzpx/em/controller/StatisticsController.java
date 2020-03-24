package xyz.qzpx.em.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.qzpx.em.dataObject.GraphDO;
import xyz.qzpx.em.dataObject.StatisticsDO;
import xyz.qzpx.em.service.CourseStudentService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    @Autowired
    private CourseStudentService courseStudentService;

    @RequestMapping("/data")
    public Map<String, List<StatisticsDO>> all() {
        return courseStudentService.getStatistics();
    }

    @RequestMapping("/graph")
    public Map<String, GraphDO> graph() {
        return courseStudentService.getGraph();
    }

}
