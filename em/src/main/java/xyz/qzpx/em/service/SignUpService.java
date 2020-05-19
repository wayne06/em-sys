package xyz.qzpx.em.service;

import xyz.qzpx.em.dataObject.SignUpDO;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface SignUpService {
    SignUpDO add();

    List<SignUpDO> getByStatus(Integer status);

    List<Integer> submit(Integer id);

    Map<String, List<SignUpDO>> collectByStatus();


    List<Integer> submit2(Integer id);

    void approve2(Integer id, String feedback);

    void delMsg(Integer id);

    String getTimelineById(Integer id);

    void approve(Integer id, String feedback, String nextProcessor);

    void reject(Integer id, String feedback) throws IOException;

    void reject2(Integer id, String feedback) throws IOException;
}
