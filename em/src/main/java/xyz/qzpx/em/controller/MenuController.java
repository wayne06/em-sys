package xyz.qzpx.em.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.qzpx.em.dataObject.MenuDO;
import xyz.qzpx.em.service.MenuService;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/byCurrentUser")
    public List<MenuDO> menus() {
        Subject subject = SecurityUtils.getSubject();
        String username = SecurityUtils.getSubject().getPrincipal().toString();
        return menuService.getMenusByCurrentUser();
    }

}
