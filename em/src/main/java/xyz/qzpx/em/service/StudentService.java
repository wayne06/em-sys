package xyz.qzpx.em.service;

import xyz.qzpx.em.dataObject.StudentDO;

import java.util.List;

public interface StudentService {

    void addStudent(StudentDO studentDO);

    void deleteStudentById(Integer id);

    void updateStudent(StudentDO studentDO);

    List<StudentDO> getAllStudent();

    StudentDO getStudentById(Integer id);

    List<StudentDO> getStudentByName(String name);


    void deleteStudentsByIds(Integer[] ids);

    Integer getStudentCount();
}
