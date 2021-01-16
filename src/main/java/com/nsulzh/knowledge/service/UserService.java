package com.nsulzh.knowledge.service;



import com.baomidou.mybatisplus.extension.service.IService;
import com.nsulzh.knowledge.entity.UserEntity;
import com.nsulzh.knowledge.util.PageUtils;
import com.nsulzh.knowledge.util.R;

import java.util.Map;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-12-30 20:41:46
 */
public interface UserService extends IService<UserEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 用户注册
     * @param userEntity
     * @return
     */
    R register(UserEntity userEntity);

    R login(UserEntity userEntity);
}

