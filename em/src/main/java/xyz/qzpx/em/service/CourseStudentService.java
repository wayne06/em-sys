package xyz.qzpx.em.service;

import java.util.List;

public interface CourseStudentService {

    List<String> getStudentIdsByCourseId(Integer id);

    List<String> getStudentIdsNotInCourseByCourseId(Integer id);

    void addSingleStuToCourse(Integer courseId, Integer stuId);

    List<Integer> getStudentIdsNotInCourse(Integer courseId);

    void rmStuToCourse(Integer id, Integer valueOf);
}
