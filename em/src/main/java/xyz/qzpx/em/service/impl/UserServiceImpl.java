package xyz.qzpx.em.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.qzpx.em.dao.UserDOMapper;
import xyz.qzpx.em.dataObject.UserDO;
import xyz.qzpx.em.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDOMapper userDOMapper;

    @Override
    public UserDO getByName(String username) {
        UserDO userDO = userDOMapper.selectByUsername(username);
        return userDO;
    }

    @Override
    public boolean isExist(String username) {
        return getByName(username) != null;
    }

    @Override
    public void add(UserDO userDO) {
        userDOMapper.insertSelective(userDO);
    }
}