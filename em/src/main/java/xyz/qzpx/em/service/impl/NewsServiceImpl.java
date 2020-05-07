package xyz.qzpx.em.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.qzpx.em.dao.NewsDOMapper;
import xyz.qzpx.em.dataObject.NewsDO;
import xyz.qzpx.em.service.NewsService;

import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsDOMapper newsDOMapper;

    @Override
    public void add(NewsDO newsDO) {
        newsDO.setCreatedAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));
        if (newsDO.getEnabled()) {
            newsDO.setEnabledAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));
        }
        newsDOMapper.insertSelective(newsDO);
    }

    @Override
    public void del(NewsDO newsDO) {
        newsDOMapper.deleteByPrimaryKey(newsDO.getId());
    }

    @Override
    public void update(NewsDO newsDO) {
        NewsDO newsDO1 = newsDOMapper.selectByPrimaryKey(newsDO.getId());
        if (newsDO.getEnabled() && !newsDO1.getEnabled()) {
            newsDO.setEnabledAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));
        }
        if (!newsDO.getEnabled() && newsDO1.getEnabled()) {
            newsDO.setEnabledAt("");
        }
        newsDOMapper.updateByPrimaryKeySelective(newsDO);
    }

    @Override
    public List<NewsDO> getAll() {
        List<NewsDO> newsDOS = newsDOMapper.selectAll();
        return newsDOS;
    }

    @Override
    public List<NewsDO> allForHomepage() {
        List<NewsDO> newsDOS = newsDOMapper.selectAllForHomepage();
        return newsDOS;
    }
}
