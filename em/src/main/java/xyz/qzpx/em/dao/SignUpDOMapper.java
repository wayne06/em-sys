package xyz.qzpx.em.dao;

import org.apache.ibatis.annotations.Param;
import xyz.qzpx.em.dataObject.SignUpDO;

import java.util.List;

public interface SignUpDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sign_up
     *
     * @mbg.generated Sun Apr 19 21:23:39 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sign_up
     *
     * @mbg.generated Sun Apr 19 21:23:39 CST 2020
     */
    int insert(SignUpDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sign_up
     *
     * @mbg.generated Sun Apr 19 21:23:39 CST 2020
     */
    int insertSelective(SignUpDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sign_up
     *
     * @mbg.generated Sun Apr 19 21:23:39 CST 2020
     */
    SignUpDO selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sign_up
     *
     * @mbg.generated Sun Apr 19 21:23:39 CST 2020
     */
    int updateByPrimaryKeySelective(SignUpDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sign_up
     *
     * @mbg.generated Sun Apr 19 21:23:39 CST 2020
     */
    int updateByPrimaryKey(SignUpDO record);

    List<SignUpDO> selectByStatus(@Param("username") String username,
                                  @Param("status") Integer status);
}