package xyz.qzpx.em.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.qzpx.em.dataObject.CourseDO;
import xyz.qzpx.em.dataObject.CourseStudentDO;
import xyz.qzpx.em.dataObject.StudentDO;
import xyz.qzpx.em.dataObject.TeacherCourseDO;
import xyz.qzpx.em.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/add")
    public StudentDO add(@RequestBody StudentDO studentDO) {
        StudentDO res = studentService.addStudent(studentDO);
        return res;
    }

    @PostMapping("/del")
    public void delete(@RequestBody StudentDO studentDO) {
        studentService.deleteStudentById(studentDO.getId());
    }

    @PostMapping("/rm")
    public void remove(@RequestBody CourseStudentDO courseStudentDO) {
        studentService.rmCourseStudent(courseStudentDO.getSignupId(), courseStudentDO.getStudentId());
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

    /**
     * 搜索学生：从当前报名message中的学生中所搜
     * 用于：1. 已提交的报名信息
     * @param courseStudentDO
     * @return
     */
    @PostMapping("/bymidandnameorphone")
    public List<StudentDO> bymidandnameorphone(@RequestBody CourseStudentDO courseStudentDO) {
        if ("".equals(courseStudentDO.getRemark())) {
            return studentService.getByMid(courseStudentDO.getSignupId());
        }
        return studentService.getStudentByMidAndNameOrPhone(courseStudentDO.getSignupId(), courseStudentDO.getRemark());
    }

    /**
     * 搜索学生：有关键字则从所有学生中搜索，无关键字则展示当前报名message中的学生
     * 用于：1. 处理中的报名信息
     * @param courseStudentDO
     * @return
     */
    @PostMapping("/bynameorphone")
    public List<StudentDO> getByNameOrPhone(@RequestBody CourseStudentDO courseStudentDO) {
        if ("".equals(courseStudentDO.getRemark())) {
            return studentService.getByMid(courseStudentDO.getSignupId());
        }
        return studentService.getStudentByName(courseStudentDO.getRemark());
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

    @PostMapping("/byMid")
    public List<StudentDO> listByMid(@RequestBody CourseStudentDO courseStudentDO) {
        return studentService.getByMid(courseStudentDO.getSignupId());
    }

    @PostMapping("/getStuByCourseIdAndSignUpId")
    public List<StudentDO> getStuByCourseIdAndSignUpId(@RequestBody TeacherCourseDO teacherCourseDO) {
        List<StudentDO> studentDOS = studentService.getStudentByCourseIdAndSignupId(teacherCourseDO.getSignupId(), teacherCourseDO.getCourseId());
        return studentDOS;
    }


}
