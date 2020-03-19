package xyz.qzpx.em.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.qzpx.em.dao.CourseStudentDOMapper;
import xyz.qzpx.em.dao.StudentDOMapper;
import xyz.qzpx.em.dataObject.CourseStudentDO;
import xyz.qzpx.em.service.CourseStudentService;

import java.util.*;

@Service
public class CourseStudentServiceImpl implements CourseStudentService {

    @Autowired
    private CourseStudentDOMapper courseStudentDOMapper;

    @Autowired
    private StudentDOMapper studentDOMapper;

    @Override
    public List<String> getStudentIdsByCourseId(Integer id) {
        return courseStudentDOMapper.selectByCourseId(id);
    }

    @Override
    public List<String> getStudentIdsNotInCourseByCourseId(Integer id) {
        return courseStudentDOMapper.selectOthersByCourseId(id);
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

        List<CourseStudentDO> courseStudentDOS = courseStudentDOMapper.selectIds();
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
        courseStudentDOMapper.deleteByCourseIdAndStudentId(courseId, stuId);
    }
}
