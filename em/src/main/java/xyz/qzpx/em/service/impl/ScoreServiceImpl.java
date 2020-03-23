package xyz.qzpx.em.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.qzpx.em.dao.CourseStudentDOMapper;
import xyz.qzpx.em.dao.ScoreDOMapper;
import xyz.qzpx.em.dao.StudentDOMapper;
import xyz.qzpx.em.dataObject.CourseStudentDO;
import xyz.qzpx.em.dataObject.ScoreDO;
import xyz.qzpx.em.service.ScoreService;

import java.util.Date;
import java.util.List;

@Service
public class ScoreServiceImpl implements ScoreService {

    @Autowired
    private ScoreDOMapper scoreDOMapper;

    @Autowired
    private CourseStudentDOMapper courseStudentDOMapper;

    @Autowired
    private StudentDOMapper studentDOMapper;

    @Override
    public void initial() {
        List<CourseStudentDO> courseStudentDOS = courseStudentDOMapper.selectAll();
        for (CourseStudentDO courseStudentDO : courseStudentDOS) {
            ScoreDO scoreDO = new ScoreDO();
            scoreDO.setCreatedAt(new Date());
            scoreDO.setUpdatedAt(new Date());
            scoreDO.setCourseId(courseStudentDO.getCourseId());
            scoreDO.setStudentId(courseStudentDO.getStudentId());
            scoreDO.setName(studentDOMapper.selectByPrimaryKey(courseStudentDO.getStudentId()).getName());
            scoreDOMapper.insertSelective(scoreDO);
        }
    }

    @Override
    public void update(ScoreDO scoreDO) {
        scoreDO.setUpdatedAt(new Date());
        scoreDOMapper.updateByPrimaryKeySelective(scoreDO);
    }

    @Override
    public List<ScoreDO> getByCourseId(Integer courseId) {
        List<ScoreDO> scoreDOS = scoreDOMapper.selectByCourseId(courseId);
        return scoreDOS;
    }
}
