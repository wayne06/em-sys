package xyz.qzpx.em.service;

import xyz.qzpx.em.dataObject.ScoreDO;

import java.util.List;

public interface ScoreService {
    void initial();

    void update(ScoreDO scoreDO);

    List<ScoreDO> getByCourseId(Integer courseId);

}
