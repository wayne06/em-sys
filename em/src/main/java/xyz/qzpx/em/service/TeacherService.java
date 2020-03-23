package xyz.qzpx.em.service;

import xyz.qzpx.em.dataObject.TeacherDO;
import xyz.qzpx.em.dataObject.Selection;

import java.util.List;

public interface TeacherService {

    void addTeacher(TeacherDO teacherDO);

    void deleteTeacherById(Integer id);

    void updateTeacher(TeacherDO teacherDO);

    List<TeacherDO> getAllTeacher();

    TeacherDO getTeacherById(Integer id);

    List<TeacherDO> getTeacherByName(String name);

    Integer getTeacherCount();

    List<Selection> getTeachers();
}
