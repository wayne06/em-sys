package temporary;

import org.apache.ibatis.annotations.Param;
import xyz.qzpx.em.dataObject.CourseStudentDO;

import java.util.List;

public interface CourseStudentDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table course_student
     *
     * @mbg.generated Thu Mar 05 19:05:32 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table course_student
     *
     * @mbg.generated Thu Mar 05 19:05:32 CST 2020
     */
    int insert(CourseStudentDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table course_student
     *
     * @mbg.generated Thu Mar 05 19:05:32 CST 2020
     */
    int insertSelective(CourseStudentDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table course_student
     *
     * @mbg.generated Thu Mar 05 19:05:32 CST 2020
     */
    CourseStudentDO selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table course_student
     *
     * @mbg.generated Thu Mar 05 19:05:32 CST 2020
     */
    int updateByPrimaryKeySelective(CourseStudentDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table course_student
     *
     * @mbg.generated Thu Mar 05 19:05:32 CST 2020
     */
    int updateByPrimaryKey(CourseStudentDO record);

    List<String> selectByCourseId(Integer id);

    List<String> selectOthersByCourseId(Integer id);

    List<CourseStudentDO> selectIds();

    void deleteByCourseIdAndStudentId(@Param("courseId") Integer courseId,
                                      @Param("stuId") Integer stuId);

    void deleteByCourseId(Integer id);

    void deleteByStudentId(Integer id);
}
