package xyz.qzpx.em.service;

import xyz.qzpx.em.dataObject.CourseStudentDO;
import xyz.qzpx.em.dataObject.CourseStudentVO;
import xyz.qzpx.em.dataObject.GraphDO;
import xyz.qzpx.em.dataObject.StatisticsDO;

import java.util.List;
import java.util.Map;

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

    Map<String, List<StatisticsDO>> getStatistics();

    Map<String, GraphDO> getGraph();

    List<CourseStudentVO> getCoursesByStuAndSignUpId(Integer studentId, Integer signupId);
}
