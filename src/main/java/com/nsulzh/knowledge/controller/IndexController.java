package com.nsulzh.knowledge.controller;


import com.nsulzh.knowledge.entity.User;
import com.nsulzh.knowledge.entity.UserEntity;
import com.nsulzh.knowledge.service.UserService;
import com.nsulzh.knowledge.util.MyUtil;
import com.nsulzh.knowledge.util.TokenUtil;
import com.nsulzh.knowledge.util.VerifyCodeUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private UserService userService;

    @RequestMapping("/home")
    public String index(ModelMap map) {
        map.addAttribute("name", "haozz");
        return "home/home";
    }

    @RequestMapping("/login")
    public String login(String name, String passWord, String code, ModelMap modelMap,HttpServletRequest request) {
        //比较验证码
        if(MyUtil.isEmpty(name)&&MyUtil.isEmpty(passWord)&&MyUtil.isEmpty(code)){
            return "home/login";
        }
        try {
            if (code.equalsIgnoreCase(code)) {
                //获取主体对象
                Subject subject = SecurityUtils.getSubject();
                subject.login(new UsernamePasswordToken(name, passWord));
                String token = TokenUtil.getToken(name, "1");
                modelMap.addAttribute("token",token);
                String name1 = TokenUtil.getName(token);
                return "home/home";
            } else {
                modelMap.addAttribute("error","验证码错误");
            }
        } catch (UnknownAccountException e) {
            modelMap.addAttribute("error","用户不存在");
        } catch (IncorrectCredentialsException e) {
            modelMap.addAttribute("error","密码错误");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "home/login";
    }

    /**
     * 用户注册
     */
    @RequestMapping("register")
    public String register(String name,String passWord) {
        try {
            UserEntity userEntity=new UserEntity();
            userEntity.setName(name);
            userEntity.setPassWord(passWord);
            userService.register(userEntity);
            return "home/login";
        } catch (Exception e) {
            e.printStackTrace();
            return "home/register";
        }
    }

    @RequestMapping("/test")
    public String test(ModelMap map) {
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
        map.addAttribute("user", u);
        map.addAttribute("userList", userList);
        return "test/test";
    }

    @PostMapping("/postform")
    public String postform(User u) {
        System.out.println(u.getName());
        return "redirect:/test";
    }

    /**
     * 验证码方法
     */
    @RequestMapping("/getImage")
    public void getImage(HttpSession session, HttpServletResponse response) throws IOException {
        //生成验证码
        String code = VerifyCodeUtils.generateVerifyCode(4);
        //验证码放入session
        session.setAttribute("code", code);
        //验证码存入图片
        ServletOutputStream os = response.getOutputStream();
        response.setContentType("image/png");
        VerifyCodeUtils.outputImage(220, 60, os, code);
    }


}
