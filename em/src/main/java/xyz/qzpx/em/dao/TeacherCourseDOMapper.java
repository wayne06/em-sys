package xyz.qzpx.em.dao;

import xyz.qzpx.em.dataObject.TeacherCourseDO;

import java.util.List;

public interface TeacherCourseDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table teacher_course
     *
     * @mbg.generated Thu Apr 09 11:10:46 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table teacher_course
     *
     * @mbg.generated Thu Apr 09 11:10:46 CST 2020
     */
    int insert(TeacherCourseDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table teacher_course
     *
     * @mbg.generated Thu Apr 09 11:10:46 CST 2020
     */
    int insertSelective(TeacherCourseDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table teacher_course
     *
     * @mbg.generated Thu Apr 09 11:10:46 CST 2020
     */
    TeacherCourseDO selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table teacher_course
     *
     * @mbg.generated Thu Apr 09 11:10:46 CST 2020
     */
    int updateByPrimaryKeySelective(TeacherCourseDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table teacher_course
     *
     * @mbg.generated Thu Apr 09 11:10:46 CST 2020
     */
    int updateByPrimaryKey(TeacherCourseDO record);

    List<TeacherCourseDO> selectAll();

    List<Integer> selectCourseIdsByTeacherId(Integer id);

    List<TeacherCourseDO> selectBySignupId(Integer signupId);
}
