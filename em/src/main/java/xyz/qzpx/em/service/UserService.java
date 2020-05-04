package xyz.qzpx.em.service;

import xyz.qzpx.em.dataObject.UserDO;

import java.util.List;

public interface UserService {
    UserDO getByName(String username);

    boolean isExist(String username);

    void add(UserDO userDO);

    void changePass(UserDO userDO);

    List<UserDO> getAll();
}
