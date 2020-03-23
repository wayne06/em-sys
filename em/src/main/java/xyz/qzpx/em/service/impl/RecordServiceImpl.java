package xyz.qzpx.em.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.qzpx.em.dao.RecordDOMapper;
import xyz.qzpx.em.dataObject.RecordDO;
import xyz.qzpx.em.service.RecordService;

import java.util.Date;
import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    private RecordDOMapper recordDOMapper;

    @Override
    public List<RecordDO> getByCourseId(Integer courseId) {
        return recordDOMapper.selectByCourseId(courseId);
    }

    @Override
    public void update(RecordDO recordDO) {
        recordDO.setUpdatedAt(new Date());
        recordDOMapper.updateByPrimaryKeySelective(recordDO);
    }

    @Override
    public void delete(RecordDO recordDO) {
        recordDOMapper.deleteByPrimaryKey(recordDO.getId());
    }

    @Override
    public void add(RecordDO recordDO) {
        recordDO.setCreatedAt(new Date());
        recordDO.setUpdatedAt(new Date());
        recordDOMapper.insertSelective(recordDO);
    }
}
