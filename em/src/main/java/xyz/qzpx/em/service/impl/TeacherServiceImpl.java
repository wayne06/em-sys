package xyz.qzpx.em.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.qzpx.em.dao.TeacherDOMapper;
import xyz.qzpx.em.dataObject.TeacherDO;
import xyz.qzpx.em.dataObject.Selection;
import xyz.qzpx.em.service.TeacherService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherDOMapper teacherDOMapper;

    @Override
    public void addTeacher(TeacherDO teacherDO) {
        teacherDO.setCreatedAt(new Date());
        teacherDO.setUpdatedAt(new Date());
        teacherDOMapper.insertSelective(teacherDO);
    }

    @Override
    public void deleteTeacherById(Integer id) {
        teacherDOMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void updateTeacher(TeacherDO teacherDO) {
        teacherDO.setUpdatedAt(new Date());
        teacherDOMapper.updateByPrimaryKeySelective(teacherDO);
    }

    @Override
    public List<TeacherDO> getAllTeacher() {
        return teacherDOMapper.selectAll();
    }

    @Override
    public TeacherDO getTeacherById(Integer id) {
        return teacherDOMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<TeacherDO> getTeacherByName(String name) {
        if ("".equals(name)) {
            return teacherDOMapper.selectAll();
        } else {
            return teacherDOMapper.selectByName(name);
        }
    }

    @Override
    public Integer getTeacherCount() {
        return teacherDOMapper.selectCount();
    }

    @Override
    public List<Selection> getTeachers() {
        List<Selection> selections = new ArrayList<>();

        for (TeacherDO teacherDO : teacherDOMapper.selectAll()) {
            Selection selection = new Selection();
            selection.setLabel(teacherDO.getName() + " , "
                    + teacherDO.getGender() + " , "
                    + teacherDO.getTelephone() + " , "
                    + teacherDO.getType() + " , "
                    + teacherDO.getProfession() + " , "
                    + teacherDO.getLevel());
            selection.setValue(teacherDO.getId().toString());
            selections.add(selection);
        }
        return selections;
    }

}
