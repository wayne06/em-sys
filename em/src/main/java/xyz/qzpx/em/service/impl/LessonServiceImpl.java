package xyz.qzpx.em.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.qzpx.em.dao.LessonDOMapper;
import xyz.qzpx.em.dataObject.LessonDO;
import xyz.qzpx.em.service.LessonService;

import java.util.Date;
import java.util.List;

@Service
public class LessonServiceImpl implements LessonService {

    @Autowired
    private LessonDOMapper lessonDOMapper;

    @Override
    public void addLesson(LessonDO lessonDO) {
        lessonDO.setCreatedAt(new Date());
        lessonDO.setUpdatedAt(new Date());
        lessonDOMapper.insertSelective(lessonDO);
    }

    @Override
    public void deleteLessonById(Integer id) {
        lessonDOMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void updateLesson(LessonDO lessonDO) {
        lessonDO.setUpdatedAt(new Date());
        lessonDOMapper.updateByPrimaryKeySelective(lessonDO);
    }

    @Override
    public List<LessonDO> getAllLesson() {
        return lessonDOMapper.selectAll();
    }

    @Override
    public void deleteLessonsByIds(Integer[] ids) {
        for (Integer id : ids) {
            deleteLessonById(id);
        }

    }

    @Override
    public Integer getLessonCount() {
        return lessonDOMapper.selectCount();
    }
}
