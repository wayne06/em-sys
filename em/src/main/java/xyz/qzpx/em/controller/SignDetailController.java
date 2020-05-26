package xyz.qzpx.em.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.qzpx.em.dataObject.CourseStudentDO;
import xyz.qzpx.em.dataObject.SignDetailDO;
import xyz.qzpx.em.dataObject.StudentDO;
import xyz.qzpx.em.service.CourseStudentService;
import xyz.qzpx.em.service.SignDetailService;

import java.util.List;

@RestController
@RequestMapping("/api/signdetail")
public class SignDetailController {

    @Autowired
    private SignDetailService signDetailService;

    @Autowired
    private CourseStudentService courseStudentService;

    @PostMapping("/getCoursesByStu")
    public List<SignDetailDO> getCoursesByStu(@RequestBody StudentDO studentDO) {
        return signDetailService.getCoursesByStuId(studentDO.getId());
    }

    @PostMapping("/getById")
    public SignDetailDO getById(@RequestBody SignDetailDO signDetailDO) {
        return signDetailService.getById(signDetailDO.getId());
    }

    @PostMapping("/getCoursesByStuAndSignUpId")
    public List<SignDetailDO> getCoursesByStuAndSignUpId(@RequestBody SignDetailDO signDetailDO) {
        return signDetailService.getCoursesByStuAndSignUpId(signDetailDO.getStudentId(), signDetailDO.getSignupId());
    }

    @PostMapping("/add")
    public Integer add(@RequestBody SignDetailDO signDetailDO) {
        Integer result = signDetailService.add(signDetailDO);
        return result;
    }

    @PostMapping("/update")
    public SignDetailDO update(@RequestBody SignDetailDO signDetailDO) {
        signDetailService.update(signDetailDO);
        return signDetailDO;
    }

    @PostMapping("/del")
    public void delete(@RequestBody SignDetailDO signDetailDO) {
        courseStudentService.delBySigndetailId(signDetailDO.getId());
        signDetailService.deleteCourseById(signDetailDO.getId());
    }

}
