package com.nsulzh.knowledge.dao;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nsulzh.knowledge.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-12-30 20:41:46
 */
@Mapper
public interface UserDao extends BaseMapper<UserEntity> {
	
}
