package xyz.qzpx.em.dao;

import org.apache.ibatis.annotations.Mapper;
import xyz.qzpx.em.dataObject.CourseStudentDO;

import java.util.List;

public interface CourseStudentDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table course_student
     *
     * @mbg.generated Thu Mar 19 10:21:48 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table course_student
     *
     * @mbg.generated Thu Mar 19 10:21:48 CST 2020
     */
    int insert(CourseStudentDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table course_student
     *
     * @mbg.generated Thu Mar 19 10:21:48 CST 2020
     */
    int insertSelective(CourseStudentDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table course_student
     *
     * @mbg.generated Thu Mar 19 10:21:48 CST 2020
     */
    CourseStudentDO selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table course_student
     *
     * @mbg.generated Thu Mar 19 10:21:48 CST 2020
     */
    int updateByPrimaryKeySelective(CourseStudentDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table course_student
     *
     * @mbg.generated Thu Mar 19 10:21:48 CST 2020
     */
    int updateByPrimaryKey(CourseStudentDO record);

    List<CourseStudentDO> selectByStuId(Integer id);

    void deleteByStudentId(Integer id);
}
