package com.nsulzh.knowledge.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-12-30 20:41:46
 */
@Data
@TableName("user")
public class UserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 唯一id
	 */
	@TableId
	private Long id;
	/**
	 * 账户
	 */
	private String userName;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 密码
	 */
	private String passWord;
	/**
	 * 盐
	 */
	private String salt;
	/**
	 * 电话
	 */
	private String phone;
	/**
	 * 简介
	 */
	private String brief;
	/**
	 * 头像
	 */
	private String header;
	/**
	 * 出生日期
	 */
	private Date birthTime;
	/**
	 * 插入时间
	 */
	private Date insertTime;
	/**
	 * 修改时间
	 */
	private Date updateTime;

}
