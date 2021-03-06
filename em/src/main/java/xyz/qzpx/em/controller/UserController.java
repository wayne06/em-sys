package xyz.qzpx.em.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;
import xyz.qzpx.em.dataObject.Selection;
import xyz.qzpx.em.dataObject.UserDO;
import xyz.qzpx.em.dataObject.UserVO;
import xyz.qzpx.em.service.UserService;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(@RequestBody UserDO requestUser, HttpSession session) {
        System.out.println("/login");
        String username = requestUser.getUsername();
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, requestUser.getPassword());
        usernamePasswordToken.setRememberMe(true);
        try {
            subject.login(usernamePasswordToken);
            return "Login success";
        } catch (AuthenticationException e) {
            return "Login failed";
        }
    }

    @PostMapping("/changePass")
    public String changePass(@RequestBody UserDO requestUser, HttpSession session) {
        String username = SecurityUtils.getSubject().getPrincipal().toString();
        UserDO userDO = userService.getByName(username);
        String passToBeChecked = new SimpleHash("md5", requestUser.getPassword(), userDO.getSalt(), 2).toString();
        if (!passToBeChecked.equals(userDO.getPassword())) {
            return "Incorrected Password";
        } else {
            String newPass = new SimpleHash("md5", requestUser.getName(), userDO.getSalt(), 2).toString();
            userDO.setPassword(newPass);
            userService.changePass(userDO);
            return "success";
        }
    }

    @GetMapping("/role")
    public String role() {
        String username = SecurityUtils.getSubject().getPrincipal().toString();
        String role = userService.getRoleByName(username);
        if (StringUtils.isEmpty(role)) {
            return "";
        } else {
            return role;
        }

    }

    @PostMapping("/register")
    public String register(@RequestBody UserDO userDO) {
        String username = userDO.getUsername();
        String password = userDO.getPassword();
        username = HtmlUtils.htmlEscape(username);
        userDO.setUsername(username);

        boolean exist = userService.isExist(username);
        if (exist) {
            return "Duplicated username";
        }

        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        String encodedPassword = new SimpleHash("md5", password, salt, 2).toString();
        userDO.setSalt(salt);
        userDO.setPassword(encodedPassword);
        userDO.setEnabled(false);
        userDO.setCreatedAt(new Date());
        userService.add(userDO);
        return "Register success";
    }

    @GetMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        System.out.println("logout success");
        return "Logout success";
    }

    @GetMapping("/profile")
    public UserDO profile() {
        String username = SecurityUtils.getSubject().getPrincipal().toString();
        UserDO userDO = userService.getByName(username);
        return userDO;
    }

    @GetMapping("/authentication")
    public String authentication() {
        return "Authenticated.";
    }

    @GetMapping("/one")
    public String one() {
        String result = "欢迎回来";
        try {
            Document document = Jsoup.connect("http://wufazhuce.com/").get();
            Elements elements = document.getElementsByClass("fp-one-cita");
            Element element = elements.get(0);
            result = element.text();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @GetMapping("/all")
    public List<UserVO> all() {
        return userService.getAll();
    }

    @PostMapping("/update")
    public void update(@RequestBody UserDO userDO) {
        userService.update(userDO);
    }

    @GetMapping("/schSelection")
    public List<Selection> getSchedulers() {
        return userService.getSchedulers();
    }

}
