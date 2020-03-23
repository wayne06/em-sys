package xyz.qzpx.em.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.qzpx.em.dataObject.TeacherDO;
import xyz.qzpx.em.dataObject.Selection;
import xyz.qzpx.em.service.TeacherService;

import java.util.List;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @PostMapping("/add")
    public TeacherDO add(@RequestBody TeacherDO teacherDO) {
        teacherService.addTeacher(teacherDO);
        return teacherDO;
    }

    @PostMapping("/del")
    public void delete(@RequestBody TeacherDO teacherDO) {
        teacherService.deleteTeacherById(teacherDO.getId());
    }

    @PostMapping("/update")
    public TeacherDO update(@RequestBody TeacherDO teacherDO) {
        teacherService.updateTeacher(teacherDO);
        return teacherDO;
    }

    @GetMapping("/all")
    public List<TeacherDO> list() {
        return teacherService.getAllTeacher();
    }

    @PostMapping("/bynameorphone")
    public List<TeacherDO> getByNameOrPhone(@RequestBody TeacherDO teacherDO) {
        return teacherService.getTeacherByName(teacherDO.getName());
    }

    @GetMapping("/byid")
    public TeacherDO getById(@RequestBody Integer id) {
        return teacherService.getTeacherById(id);
    }

    @GetMapping("/count")
    public Integer getCount() {
        return teacherService.getTeacherCount();
    }

    @GetMapping("/selection")
    public List<Selection> getTeachers() {
        return teacherService.getTeachers();
    }

}
