package com.yyc.controller;

import com.yyc.dao.UserMapper;
import com.yyc.realm.WebRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private WebRealm realm;

    @RequestMapping("/login")
    public String login(@RequestParam(required = false) String userName, @RequestParam(required = false) String password,
                        Model model) {
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);

        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated()) {
            try {
                subject.login(token);
            } catch (Exception e) {
                model.addAttribute("error", "账户或密码有误");
                return "login";
            }
        }
        return "index";
    }

    @RequestMapping("/clear")
    public String clearCache() {
        realm.clearCache();
        return "success";
    }
}
