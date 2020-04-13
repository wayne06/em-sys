package xyz.qzpx.em.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.qzpx.em.dataObject.*;
import xyz.qzpx.em.service.TeacherCourseService;

import java.util.List;

@RestController
@RequestMapping("/api/teachercourse")
public class TeacherCourseController {

    @Autowired
    private TeacherCourseService teacherCourseService;

    @PostMapping("/add")
    public TeacherCourseDO add(@RequestBody TeacherCourseDO teacherCourseDO) {
        teacherCourseService.add(teacherCourseDO);
        return teacherCourseDO;
    }

    @PostMapping("/initial")
    public void initial() {
        teacherCourseService.initial();
    }

    @PostMapping("/update")
    public TeacherCourseDO update(@RequestBody TeacherCourseDO teacherCourseDO) {
        teacherCourseService.update(teacherCourseDO);
        return teacherCourseDO;
    }

    @GetMapping("/getAll")
    public List<TeacherCourseVO> getAll() {
        return teacherCourseService.getAll();
    }

    @PostMapping("/byMid")
    public List<TeacherCourseVO> listByMid(@RequestBody TeacherCourseDO teacherCourseDO) {
        return teacherCourseService.getByMid(teacherCourseDO.getSignupId());
    }

}
