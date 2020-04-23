package xyz.qzpx.em.service.impl;

import org.apache.logging.log4j.util.Strings;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.qzpx.em.dao.*;
import xyz.qzpx.em.dataObject.CourseStudentDO;
import xyz.qzpx.em.dataObject.ScoreDO;
import xyz.qzpx.em.dataObject.SignUpDO;
import xyz.qzpx.em.dataObject.TeacherCourseDO;
import xyz.qzpx.em.service.SignUpService;

import java.text.SimpleDateFormat;
import java.util.*;

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
        //signUpDO.setCreatedAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())));
        String timeline = "{\"name\": \"" + username
                + "\", \"content\": \"" + "创建报名信息"
                + "\", \"timestamp\": \"" + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(System.currentTimeMillis())) + "\"}";
        signUpDO.setTimeline(timeline);
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
    public List<Integer> submit(Integer id) {
        // 提交前检查是否有学生还未添加报名信息
        List<CourseStudentDO> courseStudentDOS = courseStudentDOMapper.selectBySignupId(id);
        Map<Integer, List<Integer>> studentCourseMap = new HashMap<>();
        for (CourseStudentDO courseStudentDO : courseStudentDOS) {
            if (!studentCourseMap.containsKey(courseStudentDO.getStudentId())) {
                List<Integer> courseIds = new ArrayList<>();
                courseIds.add(courseStudentDO.getCourseId());
                studentCourseMap.put(courseStudentDO.getStudentId(), courseIds);
            } else {
                studentCourseMap.get(courseStudentDO.getStudentId()).add(courseStudentDO.getCourseId());
            }
        }
        List<Integer> noCourseList = new ArrayList<>();
        for (Map.Entry<Integer, List<Integer>> entry : studentCourseMap.entrySet()) {
            if (entry.getValue().size() == 1 && entry.getValue().contains(0)) {
                noCourseList.add(entry.getKey());
            }
        }
        if (noCourseList.size() == 0) {
            // 提交
            SignUpDO signUpDO = signUpDOMapper.selectByPrimaryKey(id);
            signUpDO.setStatus(1);
            //todo
            StringBuffer stringBuffer = new StringBuffer(signUpDO.getTimeline());
            String username = SecurityUtils.getSubject().getPrincipal().toString();
            stringBuffer.append("&{\"name\": \"" + username
                    + "\", \"content\": \"" + "提交报名信息"
                    + "\", \"timestamp\": \"" + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(System.currentTimeMillis())) + "\"}");
            signUpDO.setTimeline(stringBuffer.toString());
            signUpDOMapper.updateByPrimaryKeySelective(signUpDO);
        }
        return noCourseList;
    }

    @Override
    public Map<String, List<SignUpDO>> collectByStatus() {
        Map<String, List<SignUpDO>> result = new HashMap<>();
        List<SignUpDO> signUpDo0 = getByStatus(0);
        List<SignUpDO> signUpDo1 = getByStatus(1);
        List<SignUpDO> signUpDo2 = getByStatus(2);
        List<SignUpDO> signUpDo3 = getByStatus(3);
        List<SignUpDO> signUpDo4 = getByStatus(4);
        List<SignUpDO> signUpDo5 = new ArrayList<>();
        signUpDo5.addAll(signUpDo1);
        signUpDo5.addAll(signUpDo2);
        signUpDo5.addAll(signUpDo3);
        List<SignUpDO> signUpDo6 = new ArrayList<>();
        signUpDo6.addAll(signUpDo3);
        signUpDo6.addAll(signUpDo4);
        result.put("data0", signUpDo0);
        result.put("data1", signUpDo1);
        result.put("data2", signUpDo2);
        result.put("data3", signUpDo3);
        result.put("data4", signUpDo4);
        result.put("data5", signUpDo5);
        result.put("data6", signUpDo6);
        return result;
    }

    //@Override
    //public void approve(Integer id) {
    //    // 获取Signup id下的course id
    //    List<Integer> courseIds = courseStudentDOMapper.selectCourseIdsBySignupId(id);
    //    // 根据 course id 新建 teacherCourseDO
    //    TeacherCourseDO teacherCourseDO = new TeacherCourseDO();
    //    for (Integer courseId : courseIds) {
    //        teacherCourseDO.setCreatedAt(new Date());
    //        teacherCourseDO.setUpdatedAt(new Date());
    //        teacherCourseDO.setCourseId(courseId);
    //        teacherCourseDO.setSignupId(id);
    //        teacherCourseDOMapper.insertSelective(teacherCourseDO);
    //    }
    //
    //    SignUpDO signUpDO = signUpDOMapper.selectByPrimaryKey(id);
    //    signUpDO.setStatus(2);
    //    //todo
    //    //signUpDO.setApproved1At(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())));
    //    signUpDOMapper.updateByPrimaryKeySelective(signUpDO);
    //}

    @Override
    public List<Integer> submit2(Integer id) {
        // 提交前检查是否有课程还未添加教师信息
        List<TeacherCourseDO> teacherCourseDOS = teacherCourseDOMapper.selectBySignupId(id);
        List<Integer> noTeacherList = new ArrayList<>();
        for (TeacherCourseDO teacherCourseDO : teacherCourseDOS) {
            if (teacherCourseDO.getTeacherId() == 0) {
                noTeacherList.add(teacherCourseDO.getId());
            }
        }
        if (noTeacherList.size() == 0) {
            SignUpDO signUpDO = signUpDOMapper.selectByPrimaryKey(id);
            signUpDO.setStatus(3);
            StringBuffer stringBuffer = new StringBuffer(signUpDO.getTimeline());
            String username = SecurityUtils.getSubject().getPrincipal().toString();
            stringBuffer.append("&{\"name\": \"" + username
                    + "\", \"content\": \"" + "提交排课信息"
                    + "\", \"timestamp\": \"" + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(System.currentTimeMillis())) + "\"}");
            signUpDO.setTimeline(stringBuffer.toString());
            signUpDOMapper.updateByPrimaryKeySelective(signUpDO);
        }

        return noTeacherList;
    }

    @Override
    public void approve2(Integer id, String feedback) {
        List<CourseStudentDO> courseStudentDOS = courseStudentDOMapper.selectBySignupId(id);
        for (CourseStudentDO courseStudentDO : courseStudentDOS) {
            if (courseStudentDO.getCourseId() == 0) {
                continue;
            }
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
        StringBuffer stringBuffer = new StringBuffer(signUpDO.getTimeline());
        String username = SecurityUtils.getSubject().getPrincipal().toString();
        stringBuffer.append("&{\"name\": \"" + username
                + "\", \"content\": \"" + "排课信息过审"
                + "\", \"feedback\": \"" + feedback
                + "\", \"timestamp\": \"" + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(System.currentTimeMillis())) + "\"}");
        stringBuffer.append("&{\"content\": \"" + "完成"
                + "\", \"timestamp\": \"" + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(System.currentTimeMillis())) + "\"}");
        signUpDO.setTimeline(stringBuffer.toString());
        signUpDOMapper.updateByPrimaryKeySelective(signUpDO);
    }

    @Override
    public void delMsg(Integer id) {
        signUpDOMapper.deleteByPrimaryKey(id);
    }

    @Override
    public String getTimelineById(Integer id) {
        SignUpDO signUpDO = signUpDOMapper.selectByPrimaryKey(id);
        return signUpDO.getTimeline();
    }

    @Override
    public void approve(Integer id, String feedback) {
        // 获取Signup id下的course id
        List<Integer> courseIds = courseStudentDOMapper.selectCourseIdsBySignupId(id);
        // 根据 course id 新建 teacherCourseDO
        TeacherCourseDO teacherCourseDO = new TeacherCourseDO();
        for (Integer courseId : courseIds) {
            if (courseId == 0) {
                continue;
            }
            teacherCourseDO.setCreatedAt(new Date());
            teacherCourseDO.setUpdatedAt(new Date());
            teacherCourseDO.setCourseId(courseId);
            teacherCourseDO.setSignupId(id);
            teacherCourseDOMapper.insertSelective(teacherCourseDO);
        }

        SignUpDO signUpDO = signUpDOMapper.selectByPrimaryKey(id);
        signUpDO.setStatus(2);
        StringBuffer stringBuffer = new StringBuffer(signUpDO.getTimeline());
        String username = SecurityUtils.getSubject().getPrincipal().toString();
        stringBuffer.append("&{\"name\": \"" + username
                + "\", \"content\": \"" + "报名信息过审"
                + "\", \"feedback\": \"" + feedback
                + "\", \"timestamp\": \"" + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(System.currentTimeMillis())) + "\"}");
        signUpDO.setTimeline(stringBuffer.toString());
        signUpDOMapper.updateByPrimaryKeySelective(signUpDO);
    }

    @Override
    public void reject(Integer id, String feedback) {
        SignUpDO signUpDO = signUpDOMapper.selectByPrimaryKey(id);
        signUpDO.setStatus(0);
        StringBuffer stringBuffer = new StringBuffer(signUpDO.getTimeline());
        String username = SecurityUtils.getSubject().getPrincipal().toString();
        stringBuffer.append("&{\"name\": \"" + username
                + "\", \"content\": \"" + "报名信息驳回"
                + "\", \"feedback\": \"" + feedback
                + "\", \"timestamp\": \"" + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(System.currentTimeMillis())) + "\"}");
        signUpDO.setTimeline(stringBuffer.toString());
        signUpDOMapper.updateByPrimaryKeySelective(signUpDO);
    }

    @Override
    public void reject2(Integer id, String feedback) {
        SignUpDO signUpDO = signUpDOMapper.selectByPrimaryKey(id);
        signUpDO.setStatus(2);
        StringBuffer stringBuffer = new StringBuffer(signUpDO.getTimeline());
        String username = SecurityUtils.getSubject().getPrincipal().toString();
        stringBuffer.append("&{\"name\": \"" + username
                + "\", \"content\": \"" + "排课信息驳回"
                + "\", \"feedback\": \"" + feedback
                + "\", \"timestamp\": \"" + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(System.currentTimeMillis())) + "\"}");
        signUpDO.setTimeline(stringBuffer.toString());
        signUpDOMapper.updateByPrimaryKeySelective(signUpDO);
    }

    public static void main(String[] args) {
        String s = "{\"name\": \"admin\", \"content\": \"创建\", \"timestamp\": \"2020-04-20 09:40\"}";
        String s1 = "{\"name\": \"admin\", \"content\": \"创建\", \"timestamp\": \"2020-04-20 09:40\"}&{\"name\": \"admin1\", \"content\": \"创建1\", \"timestamp\": \"2020-04-20 09:41\"}";
        String[] ss = s.split("&");
        String[] s1s = s1.split("&");
        for (String sss : ss) {
            System.out.println(s);
        }
        for (String sss1 : s1s) {
            System.out.println(sss1);
        }
    }

}
