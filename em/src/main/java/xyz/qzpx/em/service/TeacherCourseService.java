package xyz.qzpx.em.service;

import xyz.qzpx.em.dataObject.TeacherCourseDO;
import xyz.qzpx.em.dataObject.TeacherCourseVO;

import java.util.List;

public interface TeacherCourseService {
    void add(TeacherCourseDO teacherCourseDO);

    void update(TeacherCourseDO teacherCourseDO);

    void initial();

    List<TeacherCourseVO> getAll();
}
