package com.nsulzh.sso.controller;


import com.nsulzh.sso.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class IndexController {
    @RequestMapping("/index")
    public String index(ModelMap map){
        map.addAttribute("name","haozz");
        return "index/index";
    }
    @RequestMapping("/test")
    public String test(ModelMap map){
        User u = new User();
        u.setName("haozz");
        u.setAge(24);
        u.setPassWord("qwerty");
        u.setBirthDay(new Date());
        u.setDesc("<font color='green'><b>talk is cheap, show me the code</b></font>");

        User u1 = new User();
        u1.setName("nico robin");
        u1.setAge(35);
        u1.setPassWord("qwerty");
        u1.setBirthDay(new Date());
        u1.setDesc("<font color='green'><b>talk is cheap, show me the code</b></font>");

        User u2 = new User();
        u2.setName("nami");
        u2.setAge(27);
        u2.setPassWord("qwerty");
        u2.setBirthDay(new Date());
        u2.setDesc("<font color='green'><b>talk is cheap, show me the code</b></font>");

        List<User> userList = new ArrayList<>();
        userList.add(u);
        userList.add(u1);
        userList.add(u2);
        map.addAttribute("user",u);
        map.addAttribute("userList",userList);
        return "test/test";
    }

    @PostMapping("/postform")
    public String postform(User u){
        System.out.println(u.getName());
        return "redirect:/test";
    }
}
