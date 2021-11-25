package com.controller;

import com.entity.User;
import com.service.UserService;
import com.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/user/login")
    public Map<String, Object> login(User user) {
        Map<String, Object> result = new HashMap<>();
        log.info("用户名: [{}]", user.getName());
        log.info("密码: [{}]", user.getPassword());
        try {
            User userDB = userService.login(user);
            //用来存放payload
            Map<String, String> map = new HashMap<>();
            map.put("id", userDB.getId());
            map.put("username", userDB.getName());
            String token = JWTUtils.getToken(map);
            result.put("state", true);
            result.put("msg", "登录成功!!!");
            //成功返回token信息
            result.put("token", token);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("state", "false");
            result.put("msg", e.getMessage());
        }
        return result;
    }

    @GetMapping("/get")
    public String get() {
        return "ok";
    }
}
