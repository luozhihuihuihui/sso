package com.nsulzh.knowledge.controller;

import java.util.Arrays;
import java.util.Map;


import com.nsulzh.knowledge.entity.UserEntity;
import com.nsulzh.knowledge.service.UserService;
import com.nsulzh.knowledge.util.PageUtils;
import com.nsulzh.knowledge.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-12-30 20:41:46
 */
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 用户注册
     */
    @PostMapping("/register")
    @ResponseBody
    public R register(@RequestBody UserEntity userEntity) {
        R r = userService.register(userEntity);
        return r;
    }

    /**
     * 登录
     * @param request
     * @return
     */
    @PostMapping("/login")
    public R login(@RequestBody UserEntity userEntity, HttpServletRequest request) {
        return userService.login(userEntity);
    }
    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = userService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        UserEntity user = userService.getById(id);

        return R.ok().put("user", user);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody UserEntity user) {
        userService.save(user);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody UserEntity user) {
        userService.updateById(user);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        userService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
