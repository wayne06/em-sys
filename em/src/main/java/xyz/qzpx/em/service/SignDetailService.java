package xyz.qzpx.em.service;

import xyz.qzpx.em.dataObject.CourseStudentDO;
import xyz.qzpx.em.dataObject.GraphDO;
import xyz.qzpx.em.dataObject.SignDetailDO;
import xyz.qzpx.em.dataObject.StatisticsDO;

import java.util.List;
import java.util.Map;

public interface SignDetailService {

    List<String> getStudentIdsByCourseId(Integer id);

    List<String> getStudentIdsNotInCourseByCourseId(Integer id);

    void addSingleStuToCourse(Integer courseId, Integer stuId);

    List<Integer> getStudentIdsNotInCourse(Integer courseId);

    void rmStuToCourse(Integer id, Integer valueOf);

    List<SignDetailDO> getCoursesByStuId(Integer id);

    Integer add(SignDetailDO signDetailDO);

    void update(SignDetailDO signDetailDO);

    void deleteCourseById(Integer id);

    Map<String, List<StatisticsDO>> getStatistics();

    Map<String, GraphDO> getGraph();

    List<SignDetailDO> getCoursesByStuAndSignUpId(Integer studentId, Integer signupId);

    SignDetailDO getById(Integer id);
}
