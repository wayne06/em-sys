package xyz.qzpx.em.service;

import xyz.qzpx.em.dataObject.Selection;
import xyz.qzpx.em.dataObject.UserDO;
import xyz.qzpx.em.dataObject.UserVO;

import java.util.List;

public interface UserService {
    UserDO getByName(String username);

    boolean isExist(String username);

    void add(UserDO userDO);

    void changePass(UserDO userDO);

    List<UserVO> getAll();

    void update(UserDO userDO);

    List<Selection> getSchedulers();

    String getRoleByName(String username);
}
