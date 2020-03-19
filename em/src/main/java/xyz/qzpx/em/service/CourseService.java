package xyz.qzpx.em.service;

import xyz.qzpx.em.dataObject.AllFilter;
import xyz.qzpx.em.dataObject.CourseDO;
import xyz.qzpx.em.dataObject.FiltersItem;
import xyz.qzpx.em.dataObject.TreeItem;

import java.util.List;
import java.util.Map;

public interface CourseService {

    void addCourse(CourseDO courseDO);

    void deleteCourseById(Integer id);

    void updateCourse(CourseDO courseDO);

    List<CourseDO> getAllCourse();

    void deleteCoursesByIds(Integer[] ids);

    Integer getCourseCount();


    List<String> getAllType();

    List<String> getGradeByType(String type);

    List<String> getSubjectByTypeAndGrade(String type, String grade);

    List<Map<String, Map<String, List<String>>>> getTreeData();

    List<TreeItem> getTree();

    AllFilter getFiltersData();

    String getIdByOthers(CourseDO courseDO);
}
