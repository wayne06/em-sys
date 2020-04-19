package xyz.qzpx.em.service.impl;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.qzpx.em.dao.*;
import xyz.qzpx.em.dataObject.*;
import xyz.qzpx.em.service.CourseService;

import java.util.*;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseDOMapper courseDOMapper;

    @Autowired
    private CourseStudentDOMapper courseStudentDOMapper;

    @Autowired
    private TeacherCourseDOMapper teacherCourseDOMapper;

    @Autowired
    private UserDOMapper userDOMapper;

    @Autowired
    private TeacherDOMapper teacherDOMapper;

    @Override
    public void addCourse(CourseDO courseDO) {
        courseDO.setCreatedAt(new Date());
        courseDO.setUpdatedAt(new Date());
        courseDOMapper.insertSelective(courseDO);
    }

    @Override
    public void deleteCourseById(Integer id) {
        courseDOMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void updateCourse(CourseDO courseDO) {
        courseDO.setUpdatedAt(new Date());
        courseDOMapper.updateByPrimaryKeySelective(courseDO);
    }

    @Override
    public List<CourseDO> getAllCourse() {
        return courseDOMapper.selectAll();
    }

    @Override
    public void deleteCoursesByIds(Integer[] ids) {
        for (Integer id : ids) {
            deleteCourseById(id);
        }

    }

    @Override
    public Integer getCourseCount() {
        return courseDOMapper.selectCount();
    }

    @Override
    public List<String> getAllType() {
        return courseDOMapper.selectAllType();
    }

    @Override
    public List<String> getGradeByType(String type) {
        return courseDOMapper.selectGradeByType(type);
    }

    @Override
    public List<String> getSubjectByTypeAndGrade(String type, String grade) {
        return courseDOMapper.selectSubjectByTypeAndGrade(type, grade);
    }

    @Override
    public List<Map<String, Map<String, List<String>>>> getTreeData() {
        List<Map<String, Map<String, List<String>>>> res = new ArrayList<>();

        List<String> typeList = courseDOMapper.selectAllType();
        List<String> gradeList;
        List<String> subjectList;


        Map<String, Map<String, List<String>>> typeGradeMap = new LinkedHashMap<>();

        for (String type : typeList) {
            Map<String, List<String>> gradeSubjectMap = new LinkedHashMap<>();
            gradeList = courseDOMapper.selectGradeByType(type);
            for (String grade : gradeList) {
                subjectList = courseDOMapper.selectSubjectByTypeAndGrade(type, grade);
                gradeSubjectMap.put(grade, subjectList);
            }
            typeGradeMap.put(type, gradeSubjectMap);
        }
        for (Map.Entry<String, Map<String, List<String>>> entry : typeGradeMap.entrySet()) {
            Map<String, Map<String, List<String>>> map = new LinkedHashMap<>();
            map.put(entry.getKey(), entry.getValue());
            System.out.println(entry.getKey());
            res.add(map);
        }
        return res;
    }

    @Override
    public List<TreeItem> getTree() {
        List<TreeItem> treeItems = new ArrayList<>();

        for (String term : courseDOMapper.selectAllTerm()) {
            TreeItem item0 = new TreeItem();
            item0.setValue(term);
            item0.setLabel(term);
            List<TreeItem> child0 = new ArrayList<>();
            for (String type : courseDOMapper.selectAllTypeByTerm(term)) {
                TreeItem item1 = new TreeItem();
                item1.setValue(type);
                item1.setLabel(type);
                List<TreeItem> child1 = new ArrayList<>();
                for (String grade : courseDOMapper.selectGradeByTermAndType(term, type)) {
                    TreeItem item2 = new TreeItem();
                    item2.setValue(grade);
                    item2.setLabel(grade);
                    List<TreeItem> child2 = new ArrayList<>();
                    for (CourseDO courseDO : courseDOMapper.selectSubjectByTermAndTypeAndGrade(term, type, grade)) {
                        TreeItem item3 = new TreeItem();
                        item3.setValue(courseDO.getId().toString());
                        item3.setLabel(courseDO.getSubject());
                        child2.add(item3);
                    }
                    item2.setChildren(child2);
                    child1.add(item2);
                }
                item1.setChildren(child1);
                child0.add(item1);
            }
            item0.setChildren(child0);
            treeItems.add(item0);
        }
        return treeItems;
    }

    @Override
    public AllFilter getFiltersData() {
        AllFilter allFilter = new AllFilter();

        List<FiltersItem> termFilters = new ArrayList<>();
        for (String type : courseDOMapper.selectAllTerm()) {
            FiltersItem filtersItem = new FiltersItem();
            filtersItem.setText(type);
            filtersItem.setValue(type);
            termFilters.add(filtersItem);
        }
        allFilter.setTerm(termFilters);

        List<FiltersItem> typeFilters = new ArrayList<>();
        for (String type : courseDOMapper.selectAllType()) {
            FiltersItem filtersItem = new FiltersItem();
            filtersItem.setText(type);
            filtersItem.setValue(type);
            typeFilters.add(filtersItem);
        }
        allFilter.setType(typeFilters);

        List<FiltersItem> gradeFilters = new ArrayList<>();
        for (String type : courseDOMapper.selectAllGrade()) {
            FiltersItem filtersItem = new FiltersItem();
            filtersItem.setText(type);
            filtersItem.setValue(type);
            gradeFilters.add(filtersItem);
        }
        allFilter.setGrade(gradeFilters);

        List<FiltersItem> subjectFilters = new ArrayList<>();
        for (String type : courseDOMapper.selectAllSubject()) {
            FiltersItem filtersItem = new FiltersItem();
            filtersItem.setText(type);
            filtersItem.setValue(type);
            subjectFilters.add(filtersItem);
        }
        allFilter.setSubject(subjectFilters);

        return allFilter;
    }

    @Override
    public String getIdByOthers(CourseDO courseDO) {
        String id = courseDOMapper.
                selectId(courseDO.getTerm(),
                        courseDO.getType(),
                        courseDO.getGrade(),
                        courseDO.getSubject()).toString();
        return id;
    }

    @Override
    public List<CourseDO> getSignUpCourse() {
        List<Integer> courseIds = courseStudentDOMapper.selectCourseIds();
        List<CourseDO> courseDOS = new ArrayList<>();
        for (Integer courseId : courseIds) {
            CourseDO courseDO = courseDOMapper.selectByPrimaryKey(courseId);
            courseDOS.add(courseDO);
        }
        return courseDOS;
    }

    @Override
    public List<Selection> getCoursesByTeacherId(Integer id) {
        List<Integer> courseIds = teacherCourseDOMapper.selectCourseIdsByTeacherId(id);
        Collections.sort(courseIds);
        List<Selection> selections = new ArrayList<>();
        for (Integer courseId : courseIds) {
            CourseDO courseDO = courseDOMapper.selectByPrimaryKey(courseId);
            Selection selection = new Selection();
            selection.setLabel(courseDO.getTerm() + "/" + courseDO.getType() + "/" + courseDO.getGrade() + "/" + courseDO.getSubject());
            selection.setValue(courseId.toString());
            selections.add(selection);
        }
        return selections;
    }

    @Override
    public List<Selection> getCoursesByTeacherId() {
        String username = SecurityUtils.getSubject().getPrincipal().toString();
        TeacherDO teacherDO = teacherDOMapper.selectByUname(username);
        return getCoursesByTeacherId(teacherDO.getId());
    }


}
