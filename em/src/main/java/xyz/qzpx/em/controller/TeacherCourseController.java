package xyz.qzpx.em.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.qzpx.em.dataObject.*;
import xyz.qzpx.em.service.SignUpService;
import xyz.qzpx.em.service.TeacherCourseService;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/teachercourse")
public class TeacherCourseController {

    @Autowired
    private TeacherCourseService teacherCourseService;

    @Autowired
    private SignUpService signUpService;

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

    @PostMapping("/getByMid")
    public Map<String, List> getByMid(@RequestBody TeacherCourseDO teacherCourseDO) throws IOException {
        Map<String, List> resultMap = new HashMap<>();

        String activitiesStr = signUpService.getTimelineById(teacherCourseDO.getSignupId());
        String[] actArr = activitiesStr.split("&");
        List<AcitivityDO> activities = new LinkedList<>();
        for (String s : actArr) {
            ObjectMapper objectMapper = new ObjectMapper();
            AcitivityDO acitivityDO = objectMapper.readValue(s, AcitivityDO.class);
            activities.add(acitivityDO);
        }

        List<TeacherCourseVO> tableData = teacherCourseService.getByMid(teacherCourseDO.getSignupId());
        resultMap.put("activities", activities);
        resultMap.put("tableData", tableData);

        return resultMap;
    }



}
