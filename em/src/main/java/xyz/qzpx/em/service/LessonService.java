package xyz.qzpx.em.service;

import xyz.qzpx.em.dataObject.LessonDO;

import java.util.List;

public interface LessonService {

    void addLesson(LessonDO lessonDO);

    void deleteLessonById(Integer id);

    void updateLesson(LessonDO lessonDO);

    List<LessonDO> getAllLesson();

    void deleteLessonsByIds(Integer[] ids);

    Integer getLessonCount();

}
