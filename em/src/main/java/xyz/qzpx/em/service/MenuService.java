package xyz.qzpx.em.service;

import xyz.qzpx.em.dataObject.MenuDO;

import java.util.List;

public interface MenuService {
    List<MenuDO> getMenusByCurrentUser();
}
