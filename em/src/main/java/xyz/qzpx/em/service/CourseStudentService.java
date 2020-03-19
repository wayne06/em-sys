package xyz.qzpx.em.service;

import xyz.qzpx.em.dataObject.CourseStudentDO;
import xyz.qzpx.em.dataObject.CourseStudentVO;

import java.util.List;

public interface CourseStudentService {

    List<String> getStudentIdsByCourseId(Integer id);

    List<String> getStudentIdsNotInCourseByCourseId(Integer id);

    void addSingleStuToCourse(Integer courseId, Integer stuId);

    List<Integer> getStudentIdsNotInCourse(Integer courseId);

    void rmStuToCourse(Integer id, Integer valueOf);

    List<CourseStudentVO> getCoursesByStuId(Integer id);

    void add(CourseStudentDO courseStudentDO);

    void update(CourseStudentDO courseStudentDO);

    void deleteCourseById(Integer id);
}
