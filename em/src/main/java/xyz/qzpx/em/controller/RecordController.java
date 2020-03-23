package xyz.qzpx.em.controller;

import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.qzpx.em.dataObject.RecordDO;
import xyz.qzpx.em.service.RecordService;

import java.util.List;

@RestController
@RequestMapping("/api/record")
public class RecordController {

    @Autowired
    private RecordService recordService;

    @PostMapping("/getByCourseId")
    public List<RecordDO> getByCourseId(@RequestBody RecordDO recordDO) {
        return recordService.getByCourseId(recordDO.getCourseId());
    }

    @PostMapping("/update")
    public void update(@RequestBody RecordDO recordDO) {
        recordService.update(recordDO);
    }

    @PostMapping("/delete")
    public void delete(@RequestBody RecordDO recordDO) {
        recordService.delete(recordDO);
    }

}
