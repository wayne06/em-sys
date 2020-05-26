package xyz.qzpx.em.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.qzpx.em.dataObject.CourseStudentDO;
import xyz.qzpx.em.dataObject.CourseStudentVO;
import xyz.qzpx.em.dataObject.SignDetailDO;
import xyz.qzpx.em.dataObject.StudentDO;
import xyz.qzpx.em.service.CourseStudentService;
import xyz.qzpx.em.service.SignDetailService;

import java.util.List;

@RestController
@RequestMapping("/api/coursestudent")
public class CourseStudentController {

    @Autowired
    private CourseStudentService courseStudentService;

    //@PostMapping("/getCoursesByStu")
    //public List<CourseStudentVO> getCoursesByStu(@RequestBody StudentDO studentDO) {
    //    return courseStudentService.getCoursesByStuId(studentDO.getId());
    //}
    //
    //@PostMapping("/getCoursesByStuAndSignUpId")
    //public List<CourseStudentVO> getCoursesByStuAndSignUpId(@RequestBody CourseStudentDO courseStudentDO) {
    //    return courseStudentService.getCoursesByStuAndSignUpId(courseStudentDO.getStudentId(), courseStudentDO.getSignupId());
    //}

    @PostMapping("/add")
    public void add(@RequestBody CourseStudentVO courseStudentVO) {
        String ids = courseStudentVO.getCourseIds();
        String[] idsArr = ids.substring(0, ids.length() - 1).split(",");
        CourseStudentDO courseStudentDO = new CourseStudentDO();
        courseStudentDO.setSignupId(courseStudentVO.getSignupId());
        courseStudentDO.setStudentId(courseStudentVO.getStudentId());
        courseStudentDO.setSigndetailId(courseStudentVO.getSigndetailId());
        for (String id : idsArr) {
            courseStudentDO.setCourseId(Integer.parseInt(id));
            courseStudentService.add(courseStudentDO);
        }
    }

    @PostMapping("/update")
    public void update(@RequestBody CourseStudentVO courseStudentVO) {
        courseStudentService.delOriginalCourStu(courseStudentVO);


        String ids = courseStudentVO.getCourseIds();
        String[] idsArr = ids.substring(0, ids.length() - 1).split(",");
        CourseStudentDO courseStudentDO = new CourseStudentDO();
        courseStudentDO.setSignupId(courseStudentVO.getSignupId());
        courseStudentDO.setStudentId(courseStudentVO.getStudentId());
        courseStudentDO.setSigndetailId(courseStudentVO.getSigndetailId());
        for (String id : idsArr) {
            courseStudentDO.setCourseId(Integer.parseInt(id));
            courseStudentService.add(courseStudentDO);
        }
    }

    //@PostMapping("/update")
    //public CourseStudentDO update(@RequestBody CourseStudentDO courseStudentDO) {
    //    courseStudentService.update(courseStudentDO);
    //    return courseStudentDO;
    //}
    //
    //@PostMapping("/del")
    //public void delete(@RequestBody CourseStudentDO courseStudentDO) {
    //    courseStudentService.deleteCourseById(courseStudentDO.getId());
    //}

}
