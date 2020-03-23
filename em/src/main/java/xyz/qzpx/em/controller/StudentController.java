package xyz.qzpx.em.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.qzpx.em.dataObject.CourseDO;
import xyz.qzpx.em.dataObject.StudentDO;
import xyz.qzpx.em.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/add")
    public StudentDO add(@RequestBody StudentDO studentDO) {
        studentService.addStudent(studentDO);
        return studentDO;
    }

    @PostMapping("/del")
    public void delete(@RequestBody StudentDO studentDO) {
        studentService.deleteStudentById(studentDO.getId());
    }

    @PostMapping("/delbatch")
    public void deleteBatch(@RequestBody StudentDO studentDO) {
        String ids = studentDO.getRemark();
        String[] idStrs = ids.split(",");
        Integer[] idInts = new Integer[idStrs.length];
        for (int i = 0; i < idStrs.length; i++) {
            idInts[i] = Integer.parseInt(idStrs[i]);
        }
        studentService.deleteStudentsByIds(idInts);
    }

    @PostMapping("/update")
    public StudentDO update(@RequestBody StudentDO studentDO) {
        studentService.updateStudent(studentDO);
        return studentDO;
    }

    @GetMapping("/all")
    public List<StudentDO> list() {
        return studentService.getAllStudent();
    }

    @PostMapping("/bynameorphone")
    public List<StudentDO> getByNameOrPhone(@RequestBody StudentDO studentDO) {
        return studentService.getStudentByName(studentDO.getName());
    }

    @GetMapping("/byid")
    public StudentDO getById(@RequestBody Integer id) {
        return studentService.getStudentById(id);
    }

    @GetMapping("/count")
    public Integer getCount() {
        return studentService.getStudentCount();
    }

    @PostMapping("/getStuByCourse")
    public List<StudentDO> getStuByCourse(@RequestBody CourseDO courseDO) {
        List<StudentDO> studentDOS = studentService.getStudentByCourseId(courseDO.getId());
        return studentDOS;
    }


}
