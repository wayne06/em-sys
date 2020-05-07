package xyz.qzpx.em.dao;

import xyz.qzpx.em.dataObject.NewsDO;

import java.util.List;

public interface NewsDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table news
     *
     * @mbg.generated Wed May 06 15:41:41 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table news
     *
     * @mbg.generated Wed May 06 15:41:41 CST 2020
     */
    int insert(NewsDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table news
     *
     * @mbg.generated Wed May 06 15:41:41 CST 2020
     */
    int insertSelective(NewsDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table news
     *
     * @mbg.generated Wed May 06 15:41:41 CST 2020
     */
    NewsDO selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table news
     *
     * @mbg.generated Wed May 06 15:41:41 CST 2020
     */
    int updateByPrimaryKeySelective(NewsDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table news
     *
     * @mbg.generated Wed May 06 15:41:41 CST 2020
     */
    int updateByPrimaryKey(NewsDO record);

    List<NewsDO> selectAll();

    List<NewsDO> selectAllForHomepage();

}
