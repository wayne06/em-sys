package xyz.qzpx.em.dao;

import xyz.qzpx.em.dataObject.ScoreDO;

import java.util.List;

public interface ScoreDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table score
     *
     * @mbg.generated Mon Mar 23 15:56:18 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table score
     *
     * @mbg.generated Mon Mar 23 15:56:18 CST 2020
     */
    int insert(ScoreDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table score
     *
     * @mbg.generated Mon Mar 23 15:56:18 CST 2020
     */
    int insertSelective(ScoreDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table score
     *
     * @mbg.generated Mon Mar 23 15:56:18 CST 2020
     */
    ScoreDO selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table score
     *
     * @mbg.generated Mon Mar 23 15:56:18 CST 2020
     */
    int updateByPrimaryKeySelective(ScoreDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table score
     *
     * @mbg.generated Mon Mar 23 15:56:18 CST 2020
     */
    int updateByPrimaryKey(ScoreDO record);

    List<ScoreDO> selectByCourseId(Integer courseId);
}
