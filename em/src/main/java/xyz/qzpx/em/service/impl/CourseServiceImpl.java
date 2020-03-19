package xyz.qzpx.em.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.qzpx.em.dao.CourseDOMapper;
import xyz.qzpx.em.dataObject.AllFilter;
import xyz.qzpx.em.dataObject.CourseDO;
import xyz.qzpx.em.dataObject.FiltersItem;
import xyz.qzpx.em.dataObject.TreeItem;
import xyz.qzpx.em.service.CourseService;

import java.util.*;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseDOMapper courseDOMapper;

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
                    for (String subject : courseDOMapper.selectSubjectByTermAndTypeAndGrade(term, type, grade)) {
                        TreeItem item3 = new TreeItem();
                        item3.setValue(subject);
                        item3.setLabel(subject);
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
        allFilter.setType(termFilters);

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


}
