package xyz.qzpx.em.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.qzpx.em.dao.CourseDOMapper;
import xyz.qzpx.em.dao.TeacherCourseDOMapper;
import xyz.qzpx.em.dao.TeacherDOMapper;
import xyz.qzpx.em.dataObject.CourseDO;
import xyz.qzpx.em.dataObject.TeacherCourseDO;
import xyz.qzpx.em.dataObject.TeacherCourseVO;
import xyz.qzpx.em.dataObject.TeacherDO;
import xyz.qzpx.em.service.CourseService;
import xyz.qzpx.em.service.TeacherCourseService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TeacherCourseServiceImpl implements TeacherCourseService {

    @Autowired
    private TeacherCourseDOMapper teacherCourseDOMapper;

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseDOMapper courseDOMapper;

    @Autowired
    private TeacherDOMapper teacherDOMapper;

    @Override
    public void add(TeacherCourseDO teacherCourseDO) {
        teacherCourseDO.setCreatedAt(new Date());
        teacherCourseDO.setUpdatedAt(new Date());
        teacherCourseDOMapper.insertSelective(teacherCourseDO);
    }

    @Override
    public void update(TeacherCourseDO teacherCourseDO) {
        teacherCourseDO.setUpdatedAt(new Date());
        teacherCourseDOMapper.updateByPrimaryKeySelective(teacherCourseDO);
    }

    @Override
    public void initial() {
        List<CourseDO> courseDOS = courseService.getSignUpCourse();
        TeacherCourseDO teacherCourseDO = new TeacherCourseDO();
        for (CourseDO courseDO : courseDOS) {
            teacherCourseDO.setCreatedAt(new Date());
            teacherCourseDO.setUpdatedAt(new Date());
            teacherCourseDO.setCourseId(courseDO.getId());
            teacherCourseDOMapper.insertSelective(teacherCourseDO);
        }
    }

    @Override
    public List<TeacherCourseVO> getAll() {
        List<TeacherCourseDO> teacherCourseDOS = teacherCourseDOMapper.selectAll();
        List<TeacherCourseVO> teacherCourseVOS = new ArrayList<>();
        for (TeacherCourseDO teacherCourseDO : teacherCourseDOS) {
            TeacherCourseVO teacherCourseVO = new TeacherCourseVO();
            BeanUtils.copyProperties(teacherCourseDO, teacherCourseVO);

            CourseDO courseDO = courseDOMapper.selectByPrimaryKey(teacherCourseDO.getCourseId());
            String courseName = courseDO.getTerm() + "/" + courseDO.getType() + "/" + courseDO.getGrade() + "/" + courseDO.getSubject();
            teacherCourseVO.setCourseName(courseName);

            if (teacherCourseDO.getTeacherId() != 0) {
                TeacherDO teacherDO = teacherDOMapper.selectByPrimaryKey(teacherCourseDO.getTeacherId());
                teacherCourseVO.setTeacherName(teacherDO.getName());
                teacherCourseVO.setTelephone(teacherDO.getTelephone());
            } else {
                teacherCourseVO.setTeacherName("");
                teacherCourseVO.setTelephone("");
            }

            teacherCourseVOS.add(teacherCourseVO);
        }
        return teacherCourseVOS;
    }

    @Override
    public List<TeacherCourseVO> getByMid(Integer signupId) {
        List<TeacherCourseDO> teacherCourseDOS = teacherCourseDOMapper.selectBySignupId(signupId);
        List<TeacherCourseVO> teacherCourseVOS = new ArrayList<>();
        for (TeacherCourseDO teacherCourseDO : teacherCourseDOS) {
            TeacherCourseVO teacherCourseVO = new TeacherCourseVO();
            BeanUtils.copyProperties(teacherCourseDO, teacherCourseVO);

            CourseDO courseDO = courseDOMapper.selectByPrimaryKey(teacherCourseDO.getCourseId());
            String courseName = courseDO.getTerm() + "/" + courseDO.getType() + "/" + courseDO.getGrade() + "/" + courseDO.getSubject();
            teacherCourseVO.setCourseName(courseName);

            if (teacherCourseDO.getTeacherId() != 0) {
                TeacherDO teacherDO = teacherDOMapper.selectByPrimaryKey(teacherCourseDO.getTeacherId());
                teacherCourseVO.setTeacherName(teacherDO.getName());
                teacherCourseVO.setTelephone(teacherDO.getTelephone());
            } else {
                teacherCourseVO.setTeacherName("");
                teacherCourseVO.setTelephone("");
            }

            teacherCourseVOS.add(teacherCourseVO);
        }
        return teacherCourseVOS;
    }
}
