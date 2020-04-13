package xyz.qzpx.em.service.impl;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.qzpx.em.dao.*;
import xyz.qzpx.em.dataObject.*;
import xyz.qzpx.em.service.SignUpService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SignUpServiceImpl implements SignUpService {

    @Autowired
    private SignUpDOMapper signUpDOMapper;

    @Autowired
    private CourseStudentDOMapper courseStudentDOMapper;

    @Autowired
    private TeacherCourseDOMapper teacherCourseDOMapper;

    @Autowired
    private StudentDOMapper studentDOMapper;

    @Autowired
    private ScoreDOMapper scoreDOMapper;

    @Override
    public SignUpDO add() {
        String title = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(System.currentTimeMillis()));
        String username = SecurityUtils.getSubject().getPrincipal().toString();
        SignUpDO signUpDO = new SignUpDO();
        signUpDO.setTitle(title);
        signUpDO.setUsername(username);
        signUpDO.setStatus(0);
        signUpDO.setCreatedAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())));
        signUpDOMapper.insertSelective(signUpDO);
        return signUpDO;
    }

    @Override
    public List<SignUpDO> getByStatus(Integer status) {
        String username = SecurityUtils.getSubject().getPrincipal().toString();
        List<SignUpDO> signUpDOS = signUpDOMapper.selectByStatus(username, status);
        return signUpDOS;
    }

    @Override
    public void submit(Integer id) {
        SignUpDO signUpDO = signUpDOMapper.selectByPrimaryKey(id);
        signUpDO.setStatus(1);
        signUpDO.setSubmit1At(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())));
        signUpDOMapper.updateByPrimaryKeySelective(signUpDO);
    }

    @Override
    public Map<String, List<SignUpDO>> collectByStatus() {
        Map<String, List<SignUpDO>> result = new HashMap<>();
        result.put("data0", getByStatus(0));
        result.put("data1", getByStatus(1));
        result.put("data2", getByStatus(2));
        result.put("data3", getByStatus(3));
        result.put("data4", getByStatus(4));
        return result;
    }

    @Override
    public void approve(Integer id) {
        // 获取Signup id下的course id
        List<Integer> courseIds = courseStudentDOMapper.selectCourseIdsBySignupId(id);
        // 根据 course id 新建 teacherCourseDO
        TeacherCourseDO teacherCourseDO = new TeacherCourseDO();
        for (Integer courseId : courseIds) {
            teacherCourseDO.setCreatedAt(new Date());
            teacherCourseDO.setUpdatedAt(new Date());
            teacherCourseDO.setCourseId(courseId);
            teacherCourseDO.setSignupId(id);
            teacherCourseDOMapper.insertSelective(teacherCourseDO);
        }

        SignUpDO signUpDO = signUpDOMapper.selectByPrimaryKey(id);
        signUpDO.setStatus(2);
        signUpDO.setApproved1At(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())));
        signUpDOMapper.updateByPrimaryKeySelective(signUpDO);
    }

    @Override
    public void submit2(Integer id) {
        SignUpDO signUpDO = signUpDOMapper.selectByPrimaryKey(id);
        signUpDO.setStatus(3);
        signUpDO.setSubmit2At(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())));
        signUpDOMapper.updateByPrimaryKeySelective(signUpDO);
    }

    @Override
    public void approve2(Integer id) {
        List<CourseStudentDO> courseStudentDOS = courseStudentDOMapper.selectBySignupId(id);
        for (CourseStudentDO courseStudentDO : courseStudentDOS) {
            ScoreDO scoreDO = new ScoreDO();
            scoreDO.setCreatedAt(new Date());
            scoreDO.setUpdatedAt(new Date());
            scoreDO.setCourseId(courseStudentDO.getCourseId());
            scoreDO.setStudentId(courseStudentDO.getStudentId());
            scoreDO.setName(studentDOMapper.selectByPrimaryKey(courseStudentDO.getStudentId()).getName());
            scoreDOMapper.insertSelective(scoreDO);
        }

        SignUpDO signUpDO = signUpDOMapper.selectByPrimaryKey(id);
        signUpDO.setStatus(4);
        signUpDO.setApproved2At(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())));
        signUpDOMapper.updateByPrimaryKeySelective(signUpDO);
    }

    @Override
    public void delMsg(Integer id) {
        signUpDOMapper.deleteByPrimaryKey(id);
    }


    public static void main(String[] args) {
        String s = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(System.currentTimeMillis()));
        System.out.println(s);
    }

}
