package xyz.qzpx.em.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.qzpx.em.dataObject.CourseStudentDO;
import xyz.qzpx.em.dataObject.CourseStudentVO;
import xyz.qzpx.em.dataObject.StudentDO;
import xyz.qzpx.em.service.CourseStudentService;

import java.util.List;

@RestController
@RequestMapping("/api/coursestudent")
public class CourseStudentController {

    @Autowired
    private CourseStudentService courseStudentService;

    @PostMapping("/getCoursesByStu")
    public List<CourseStudentVO> getCoursesByStu(@RequestBody StudentDO studentDO) {
        return courseStudentService.getCoursesByStuId(studentDO.getId());
    }

    @PostMapping("/getCoursesByStuAndSignUpId")
    public List<CourseStudentVO> getCoursesByStuAndSignUpId(@RequestBody CourseStudentDO courseStudentDO) {
        return courseStudentService.getCoursesByStuAndSignUpId(courseStudentDO.getStudentId(), courseStudentDO.getSignupId());
    }

    @PostMapping("/add")
    public CourseStudentDO add(@RequestBody CourseStudentDO courseStudentDO) {
        courseStudentService.add(courseStudentDO);
        return courseStudentDO;
    }

    @PostMapping("/update")
    public CourseStudentDO update(@RequestBody CourseStudentDO courseStudentDO) {
        courseStudentService.update(courseStudentDO);
        return courseStudentDO;
    }

    @PostMapping("/del")
    public void delete(@RequestBody CourseStudentDO courseStudentDO) {
        courseStudentService.deleteCourseById(courseStudentDO.getId());
    }

}
