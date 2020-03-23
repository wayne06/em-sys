package xyz.qzpx.em.service;

import xyz.qzpx.em.dataObject.RecordDO;

import java.util.List;

public interface RecordService {
    List<RecordDO> getByCourseId(Integer courseId);

    void update(RecordDO recordDO);

    void delete(RecordDO recordDO);

    void add(RecordDO recordDO);
}
