package xyz.qzpx.em.service;

import xyz.qzpx.em.dataObject.SignUpDO;

import java.util.List;
import java.util.Map;

public interface SignUpService {
    SignUpDO add();

    List<SignUpDO> getByStatus(Integer status);

    void submit(Integer id);

    Map<String, List<SignUpDO>> collectByStatus();

    void approve(Integer id);

    void submit2(Integer id);

    void approve2(Integer id);

    void delMsg(Integer id);
}
