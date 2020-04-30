package xyz.qzpx.em.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.qzpx.em.dataObject.AcitivityDO;
import xyz.qzpx.em.dataObject.SignUpDO;
import xyz.qzpx.em.dataObject.StudentDO;
import xyz.qzpx.em.service.SignUpService;
import xyz.qzpx.em.service.StudentService;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/signup")
public class SignUpController {

    @Autowired
    private SignUpService signUpService;

    @Autowired
    private StudentService studentService;

    /**
     * 根据signupId获取course_student数据
     * @param signUpDO
     * @return
     * @throws IOException
     */
    @PostMapping("/getById")
    public Map<String, List> getById(@RequestBody SignUpDO signUpDO) throws IOException {
        Map<String, List> resultMap = new HashMap<>();
        String activitiesStr = signUpService.getTimelineById(signUpDO.getId());
        String[] actArr = activitiesStr.split("&");
        List<AcitivityDO> activities = new LinkedList<>();
        for (String s : actArr) {
            ObjectMapper objectMapper = new ObjectMapper();
            AcitivityDO acitivityDO = objectMapper.readValue(s, AcitivityDO.class);
            activities.add(acitivityDO);
        }

        List<StudentDO> tableData = studentService.getByMid(signUpDO.getId());
        resultMap.put("activities", activities);
        resultMap.put("tableData", tableData);
        return resultMap;
    }

    /**
     * 根据signupId获取course_student数据
     * @param signUpDO
     * @return
     * @throws IOException
     */
    @PostMapping("/getById2")
    public Map<String, List> getById2(@RequestBody SignUpDO signUpDO) throws IOException {
        Map<String, List> resultMap = new HashMap<>();
        String activitiesStr = signUpService.getTimelineById(signUpDO.getId());
        String[] actArr = activitiesStr.split("&");
        List<AcitivityDO> activities = new LinkedList<>();
        for (String s : actArr) {
            ObjectMapper objectMapper = new ObjectMapper();
            AcitivityDO acitivityDO = objectMapper.readValue(s, AcitivityDO.class);
            activities.add(acitivityDO);
        }

        List<StudentDO> tableData = studentService.getByMid(signUpDO.getId());
        resultMap.put("activities", activities);
        resultMap.put("tableData", tableData);
        return resultMap;
    }

    @PostMapping("/add")
    public SignUpDO add() {
        SignUpDO signUpDO = signUpService.add();
        return signUpDO;
    }

    @PostMapping("/del")
    public void delete(@RequestBody SignUpDO signUpDO) {
        signUpService.delMsg(signUpDO.getId());
    }

    @PostMapping("/getByStatus")
    public List<SignUpDO> get(@RequestBody SignUpDO signUpDO) {
        List<SignUpDO> signUpDOS = signUpService.getByStatus(signUpDO.getStatus());
        return signUpDOS;
    }

    @GetMapping("/collectByStatus")
    public Map<String, List<SignUpDO>> collectByStatus() {
        Map<String, List<SignUpDO>> result = signUpService.collectByStatus();
        return result;
    }

    @PostMapping("/submit")
    public List<Integer> submit(@RequestBody SignUpDO signUpDO) {
        List<Integer> result = signUpService.submit(signUpDO.getId());
        return result;
    }

    @PostMapping("/submit2")
    public List<Integer> submit2(@RequestBody SignUpDO signUpDO) {
        List<Integer> result = signUpService.submit2(signUpDO.getId());
        return result;
    }

    @PostMapping("/approve")
    public void approve(@RequestBody SignUpDO signUpDO) {
        signUpService.approve(signUpDO.getId(), signUpDO.getTimeline());
    }

    @PostMapping("/reject")
    public void reject(@RequestBody SignUpDO signUpDO) {
        signUpService.reject(signUpDO.getId(), signUpDO.getTimeline());
    }

    @PostMapping("/approve2")
    public void approve2(@RequestBody SignUpDO signUpDO) {
        signUpService.approve2(signUpDO.getId(), signUpDO.getTimeline());
    }

    @PostMapping("/reject2")
    public void reject2(@RequestBody SignUpDO signUpDO) {
        signUpService.reject2(signUpDO.getId(), signUpDO.getTimeline());
    }

}
