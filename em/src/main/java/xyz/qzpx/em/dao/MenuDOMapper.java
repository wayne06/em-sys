package xyz.qzpx.em.dao;

import xyz.qzpx.em.dataObject.MenuDO;

import java.util.List;

public interface MenuDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table menu
     *
     * @mbg.generated Sun Mar 29 21:13:41 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table menu
     *
     * @mbg.generated Sun Mar 29 21:13:41 CST 2020
     */
    int insert(MenuDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table menu
     *
     * @mbg.generated Sun Mar 29 21:13:41 CST 2020
     */
    int insertSelective(MenuDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table menu
     *
     * @mbg.generated Sun Mar 29 21:13:41 CST 2020
     */
    MenuDO selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table menu
     *
     * @mbg.generated Sun Mar 29 21:13:41 CST 2020
     */
    int updateByPrimaryKeySelective(MenuDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table menu
     *
     * @mbg.generated Sun Mar 29 21:13:41 CST 2020
     */
    int updateByPrimaryKey(MenuDO record);

    List<MenuDO> selectByParentId(Integer id);
}
