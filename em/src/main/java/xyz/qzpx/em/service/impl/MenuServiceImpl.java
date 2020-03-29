package xyz.qzpx.em.service.impl;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.qzpx.em.dao.MenuDOMapper;
import xyz.qzpx.em.dao.RoleMenuDOMapper;
import xyz.qzpx.em.dao.UserDOMapper;
import xyz.qzpx.em.dao.UserRoleDOMapper;
import xyz.qzpx.em.dataObject.MenuDO;
import xyz.qzpx.em.dataObject.RoleMenuDO;
import xyz.qzpx.em.dataObject.UserDO;
import xyz.qzpx.em.dataObject.UserRoleDO;
import xyz.qzpx.em.service.MenuService;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private UserDOMapper userDOMapper;

    @Autowired
    private UserRoleDOMapper userRoleDOMapper;

    @Autowired
    private RoleMenuDOMapper roleMenuDOMapper;

    @Autowired
    private MenuDOMapper menuDOMapper;


    @Override
    public List<MenuDO> getMenusByCurrentUser() {
        String username = SecurityUtils.getSubject().getPrincipal().toString();
        UserDO userDO = userDOMapper.selectByUsername(username);

        List<UserRoleDO> userRoleDOS = userRoleDOMapper.selectByUid(userDO.getId());

        List<MenuDO> menuDOS = new ArrayList<>();
        for (UserRoleDO userRoleDO : userRoleDOS) {
            List<RoleMenuDO> roleMenuDOS = roleMenuDOMapper.selectByRid(userRoleDO.getRid());
            for (RoleMenuDO roleMenuDO : roleMenuDOS) {
                MenuDO menuDO = menuDOMapper.selectByPrimaryKey(roleMenuDO.getMid());
                if (!menuDOS.contains(menuDO)) {
                    menuDOS.add(menuDO);
                }
            }
        }
        handleMenus(menuDOS);
        return menuDOS;
    }

    private void handleMenus(List<MenuDO> menuDOS) {
        for (MenuDO menuDO : menuDOS) {
            List<MenuDO> children = menuDOMapper.selectByParentId(menuDO.getId());
            menuDO.setChildren(children);
        }
        menuDOS.removeIf(menu -> menu.getParentId() != 0);
    }


}
