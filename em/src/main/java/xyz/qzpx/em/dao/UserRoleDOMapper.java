package xyz.qzpx.em.dao;

import xyz.qzpx.em.dataObject.UserRoleDO;

import java.util.List;

public interface UserRoleDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_role
     *
     * @mbg.generated Sun Mar 29 21:13:41 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_role
     *
     * @mbg.generated Sun Mar 29 21:13:41 CST 2020
     */
    int insert(UserRoleDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_role
     *
     * @mbg.generated Sun Mar 29 21:13:41 CST 2020
     */
    int insertSelective(UserRoleDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_role
     *
     * @mbg.generated Sun Mar 29 21:13:41 CST 2020
     */
    UserRoleDO selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_role
     *
     * @mbg.generated Sun Mar 29 21:13:41 CST 2020
     */
    int updateByPrimaryKeySelective(UserRoleDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_role
     *
     * @mbg.generated Sun Mar 29 21:13:41 CST 2020
     */
    int updateByPrimaryKey(UserRoleDO record);

    List<UserRoleDO> selectByUid(Integer id);
}