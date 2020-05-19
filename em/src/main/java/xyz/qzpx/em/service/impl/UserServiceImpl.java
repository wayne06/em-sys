package xyz.qzpx.em.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.qzpx.em.dao.RoleDOMapper;
import xyz.qzpx.em.dao.UserDOMapper;
import xyz.qzpx.em.dao.UserRoleDOMapper;
import xyz.qzpx.em.dataObject.Selection;
import xyz.qzpx.em.dataObject.UserDO;
import xyz.qzpx.em.dataObject.UserVO;
import xyz.qzpx.em.service.UserService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDOMapper userDOMapper;

    @Autowired
    private UserRoleDOMapper userRoleDOMapper;

    @Autowired
    private RoleDOMapper roleDOMapper;

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

    @Override
    public void changePass(UserDO userDO) {
        userDOMapper.updateByPrimaryKeySelective(userDO);
    }

    @Override
    public List<UserVO> getAll() {
        List<UserVO> result = new ArrayList<>();
        List<UserDO> userDOS = userDOMapper.selectAll();
        for (UserDO userDO : userDOS) {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(userDO, userVO);
            if (userDO.getRoleId() != null) {
                userVO.setRole(roleDOMapper.selectByPrimaryKey(userDO.getRoleId()).getNameZh());
            }
            userVO.setCreatedAt(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(userDO.getCreatedAt()));
            result.add(userVO);
        }
        return result;
    }

    @Override
    public void update(UserDO userDO) {
        userDOMapper.updateByPrimaryKeySelective(userDO);
    }

    @Override
    public List<Selection> getSchedulers() {
        List<Selection> selections = new ArrayList<>();

        for (UserDO userDO : userDOMapper.selectSchedulers()) {
            Selection selection = new Selection();
            selection.setLabel(userDO.getUsername());
            selection.setValue(userDO.getUsername());
            selections.add(selection);
        }
        return selections;
    }

    @Override
    public String getRoleByName(String username) {
        Integer roleId = userDOMapper.selectByUsername(username).getRoleId();
        if (roleId == null) {
            return "";
        } else {
            return roleDOMapper.selectByPrimaryKey(roleId).getNameZh();
        }
    }

}
