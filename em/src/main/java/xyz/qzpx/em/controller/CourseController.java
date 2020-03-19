package xyz.qzpx.em.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.qzpx.em.dataObject.AllFilter;
import xyz.qzpx.em.dataObject.CourseDO;
import xyz.qzpx.em.dataObject.FiltersItem;
import xyz.qzpx.em.dataObject.TreeItem;
import xyz.qzpx.em.service.CourseService;
import xyz.qzpx.em.service.impl.CourseServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping("/add")
    public CourseDO add(@RequestBody CourseDO courseDO) {
        courseService.addCourse(courseDO);
        return courseDO;
    }

    @PostMapping("/del")
    public void delete(@RequestBody CourseDO courseDO) {
        courseService.deleteCourseById(courseDO.getId());
    }

    @PostMapping("/delbatch")
    public void deleteBatch(@RequestBody CourseDO courseDO) {
        String ids = courseDO.getRemark();
        String[] idStrs = ids.split(",");
        Integer[] idInts = new Integer[idStrs.length];
        for (int i = 0; i < idStrs.length; i++) {
            idInts[i] = Integer.parseInt(idStrs[i]);
        }
        courseService.deleteCoursesByIds(idInts);
    }

    @PostMapping("/update")
    public CourseDO update(@RequestBody CourseDO courseDO) {
        courseService.updateCourse(courseDO);
        return courseDO;
    }

    @GetMapping("/all")
    public List<CourseDO> list() {
        return courseService.getAllCourse();
    }

    @GetMapping("/type")
    public List<String> getType() {
        return courseService.getAllType();
    }

    @GetMapping("/grade")
    public List<String> getGrade(@RequestBody CourseDO courseDO) {
        return courseService.getGradeByType(courseDO.getType());
    }

    @GetMapping("/subject")
    public List<String> getSubject(@RequestBody CourseDO courseDO) {
        return courseService.getSubjectByTypeAndGrade(courseDO.getType(), courseDO.getGrade());
    }

    //@GetMapping("/res")
    //public List<Map<String, Map<String, List<String>>>> res() {
    //    return courseService.getTreeData();
    //}

    @GetMapping("/tree")
    public List<TreeItem> getTree() {
        return courseService.getTree();
    }

    @GetMapping("/count")
    public Integer getCount() {
        return courseService.getCourseCount();
    }

    @GetMapping("/filters")
    public AllFilter getFilters() {
        return courseService.getFiltersData();
    }

}
