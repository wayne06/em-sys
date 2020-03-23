package xyz.qzpx.em.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.qzpx.em.dao.CourseStudentDOMapper;
import xyz.qzpx.em.dao.StudentDOMapper;
import xyz.qzpx.em.dataObject.StudentDO;
import xyz.qzpx.em.service.StudentService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentDOMapper studentDOMapper;

    @Autowired
    private CourseStudentDOMapper courseStudentDOMapper;

    @Override
    public void addStudent(StudentDO studentDO) {
        studentDO.setCreatedAt(new Date());
        studentDO.setUpdatedAt(new Date());
        studentDOMapper.insertSelective(studentDO);
    }

    @Override
    public void deleteStudentById(Integer id) {
        studentDOMapper.deleteByPrimaryKey(id);
        courseStudentDOMapper.deleteByStudentId(id);
    }

    @Override
    public void updateStudent(StudentDO studentDO) {
        studentDO.setUpdatedAt(new Date());
        studentDOMapper.updateByPrimaryKeySelective(studentDO);
    }

    @Override
    public List<StudentDO> getAllStudent() {
        return studentDOMapper.selectAll();
    }

    @Override
    public StudentDO getStudentById(Integer id) {
        return studentDOMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<StudentDO> getStudentByName(String name) {
        if ("".equals(name)) {
            return studentDOMapper.selectAll();
        } else {
            return studentDOMapper.selectByName(name);
        }
    }

    @Override
    public void deleteStudentsByIds(Integer[] ids) {
        for (Integer id : ids) {
            deleteStudentById(id);
        }
    }

    @Override
    public Integer getStudentCount() {
        return studentDOMapper.selectCount();
    }

    @Override
    public List<StudentDO> getStudentByCourseId(Integer courseId) {
        List<Integer> stuIds = courseStudentDOMapper.selectByCourseId(courseId);
        List<StudentDO> studentDOS = new ArrayList<>();
        for (Integer stuId : stuIds) {
            StudentDO studentDO = studentDOMapper.selectByPrimaryKey(stuId);
            studentDOS.add(studentDO);
        }
        return studentDOS;
    }


}
