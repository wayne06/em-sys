package xyz.qzpx.em.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.qzpx.em.dataObject.ScoreDO;
import xyz.qzpx.em.service.ScoreService;

import java.util.List;

@RestController
@RequestMapping("/api/score")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    @PostMapping("/initial")
    public void initial() {
        scoreService.initial();
    }

    @PostMapping("/update")
    public void update(@RequestBody ScoreDO scoreDO) {
        scoreService.update(scoreDO);
    }

    @PostMapping("/getByCourseId")
    public List<ScoreDO> getByCourseId(@RequestBody ScoreDO scoreDO) {
        return scoreService.getByCourseId(scoreDO.getCourseId());
    }

}
