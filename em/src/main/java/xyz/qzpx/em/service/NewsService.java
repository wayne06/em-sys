package xyz.qzpx.em.service;

import xyz.qzpx.em.dataObject.NewsDO;

import java.util.List;

public interface NewsService {
    void add(NewsDO newsDO);

    void del(NewsDO newsDO);

    void update(NewsDO newsDO);

    List<NewsDO> getAll();

    List<NewsDO> allForHomepage();

}
