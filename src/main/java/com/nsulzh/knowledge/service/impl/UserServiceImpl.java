package com.nsulzh.knowledge.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nsulzh.knowledge.dao.UserDao;
import com.nsulzh.knowledge.entity.UserEntity;
import com.nsulzh.knowledge.service.UserService;
import com.nsulzh.knowledge.shiro.cache.RedisCacheManager;
import com.nsulzh.knowledge.util.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RedisCacheManager redisCacheManager;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
//        IPage<UserEntity> page = this.page(
//                new Query<UserEntity>().getPage(params),
//                new QueryWrapper<UserEntity>()
//        );
//
//        return new PageUtils(page);
        return null;
    }

    @Override
    public R register(UserEntity userEntity) {
        //处理业务调用dao
        //1.生成随机盐
        String salt = SaltUtils.getSalt(8);
        //2.将随机盐保存到数据
        userEntity.setSalt(salt);
        //3.明文密码进行md5 + salt + hash散列
        Md5Hash md5Hash = new Md5Hash(userEntity.getPassWord(), salt, 1024);
        userEntity.setPassWord(md5Hash.toHex());
        try {
            userDao.insert(userEntity);
        } catch (DuplicateKeyException duplicateKeyException) {
            return R.error("已存在相同用户名！");
        }
        return R.ok();
    }

    @Override
    public R login(UserEntity userEntity) {
        try {
            //获取主体对象
            Subject subject = SecurityUtils.getSubject();
            subject.login(new UsernamePasswordToken(userEntity.getUserName(), userEntity.getPassWord()));
            String token = TokenUtil.getToken(userEntity.getUserName(), "1");
            UserEntity user = userDao.selectOne(new LambdaQueryWrapper<UserEntity>().eq(UserEntity::getUserName, userEntity.getUserName()));
            user.setPassWord(null);
            user.setSalt(null);
            Map map=new HashMap();
            map.put("token",token);
            map.put("user",user);
            Cache<Object, Object> cache = redisCacheManager.getCache("WEB:USER");
            cache.put(token,user);
            Object o = cache.get(token);
            return R.setData(map);
        } catch (UnknownAccountException e) {
            return R.error("用户名不存在！");
        } catch (IncorrectCredentialsException e) {
            return R.error("密码错误！");
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("系统错误！");
        }
    }

}