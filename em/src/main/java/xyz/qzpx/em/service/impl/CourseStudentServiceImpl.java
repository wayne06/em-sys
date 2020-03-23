package xyz.qzpx.em.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.qzpx.em.dao.CourseDOMapper;
import xyz.qzpx.em.dao.CourseStudentDOMapper;
import xyz.qzpx.em.dao.StudentDOMapper;
import xyz.qzpx.em.dataObject.CourseDO;
import xyz.qzpx.em.dataObject.CourseStudentDO;
import xyz.qzpx.em.dataObject.CourseStudentVO;
import xyz.qzpx.em.service.CourseStudentService;

import java.util.*;

@Service
public class CourseStudentServiceImpl implements CourseStudentService {

    @Autowired
    private CourseStudentDOMapper courseStudentDOMapper;

    @Autowired
    private StudentDOMapper studentDOMapper;

    @Autowired
    private CourseDOMapper courseDOMapper;

    @Override
    public List<String> getStudentIdsByCourseId(Integer id) {
        //courseStudentDOMapper.selectByCourseId(id);
        return null;
    }

    @Override
    public List<String> getStudentIdsNotInCourseByCourseId(Integer id) {
        //courseStudentDOMapper.selectOthersByCourseId(id);
        return null;
    }

    @Override
    public void addSingleStuToCourse(Integer courseId, Integer stuId) {
        CourseStudentDO courseStudentDO = new CourseStudentDO();
        courseStudentDO.setCourseId(courseId);
        courseStudentDO.setStudentId(stuId);
        courseStudentDO.setCreatedAt(new Date());
        courseStudentDO.setUpdatedAt(new Date());
        courseStudentDOMapper.insert(courseStudentDO);
    }

    @Override
    public List<Integer> getStudentIdsNotInCourse(Integer id) {
        List<Integer> allId = studentDOMapper.selectAllId();
        Map<Integer, List<Integer>> stu_cor_map = new HashMap<>(allId.size());
        for (Integer i : allId) {
            stu_cor_map.put(i, new ArrayList<>());
        }

        List<CourseStudentDO> courseStudentDOS = null;
        //courseStudentDOS = courseStudentDOMapper.selectIds()
        for (CourseStudentDO c : courseStudentDOS) {
            stu_cor_map.get(c.getStudentId()).add(c.getCourseId());
        }

        List<Integer> notInList = new ArrayList<>(stu_cor_map.size());
        for (Map.Entry<Integer, List<Integer>> entry : stu_cor_map.entrySet()) {
            if (!entry.getValue().contains(id)) {
                notInList.add(entry.getKey());
            }
        }
        return notInList;
    }

    @Override
    public void rmStuToCourse(Integer courseId, Integer stuId) {
        //courseStudentDOMapper.deleteByCourseIdAndStudentId(courseId, stuId);
    }

    @Override
    public List<CourseStudentVO> getCoursesByStuId(Integer id) {
        List<CourseStudentDO> courseStudentDOs = courseStudentDOMapper.selectByStuId(id);
        List<CourseStudentVO> courseStudentVOS = new ArrayList<>();
        for (CourseStudentDO courseStudentDO : courseStudentDOs) {
            CourseStudentVO courseStudentVO = new CourseStudentVO();
            BeanUtils.copyProperties(courseStudentDO, courseStudentVO);
            courseStudentVO.setCourseName(getCourseNameById(courseStudentDO.getCourseId()));
            courseStudentVOS.add(courseStudentVO);
        }
        return courseStudentVOS;
    }

    @Override
    public void add(CourseStudentDO courseStudentDO) {
        courseStudentDO.setCreatedAt(new Date());
        courseStudentDO.setUpdatedAt(new Date());
        courseStudentDOMapper.insertSelective(courseStudentDO);
    }

    @Override
    public void update(CourseStudentDO courseStudentDO) {
        courseStudentDO.setUpdatedAt(new Date());
        courseStudentDOMapper.updateByPrimaryKeySelective(courseStudentDO);
    }

    @Override
    public void deleteCourseById(Integer id) {
        courseStudentDOMapper.deleteByPrimaryKey(id);
    }

    private String getCourseNameById(Integer courseId) {
        CourseDO courseDO = courseDOMapper.selectByPrimaryKey(courseId);
        String courseName = courseDO.getTerm() + "/" + courseDO.getType() + "/" + courseDO.getGrade() + "/" + courseDO.getSubject();
        return courseName;
    }
}
