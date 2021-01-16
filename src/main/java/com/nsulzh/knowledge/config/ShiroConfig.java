package com.nsulzh.knowledge.config;


import com.nsulzh.knowledge.shiro.cache.RedisCacheManager;
import com.nsulzh.knowledge.shiro.realms.CustomerRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 用来整合shiro框架相关的配置类
 */
@Configuration
public class ShiroConfig {
    @Autowired
    private RedisCacheManager redisCacheManager;


    //1.创建shiroFilter  //负责拦截所有请求
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        //给filter设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);

        //配置系统受限资源
        //配置系统公共资源
        Map<String, String> map = new HashMap<String, String>();
        map.put("/login", "anon");//anon 设置为公共资源  放行资源放在下面
        map.put("/home", "anon");//anon 设置为公共资源  放行资源放在下面
        map.put("/register", "anon");//anon 设置为公共资源  放行资源放在下面
        map.put("/getImage", "anon");//anon 设置为公共资源  放行资源放在下面
        map.put("/bootstrap/**", "anon");//anon 设置为公共资源  放行资源放在下面
        map.put("/css/**", "anon");//anon 设置为公共资源  放行资源放在下面
        map.put("/js/**", "anon");//anon 设置为公共资源  放行资源放在下面
        map.put("/user/login", "anon");//anon 设置为公共资源  放行资源放在下面
        map.put("/user/register", "anon");//anon 设置为公共资源  放行资源放在下面

        map.put("/**", "authc");//authc 请求这个资源需要认证和授权

        //默认认证界面路径
//        shiroFilterFactoryBean.setLoginUrl("/home");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        Map map1 = new HashMap();
        map1.put("authc", new ShiroFilter());
        shiroFilterFactoryBean.setFilters(map1);
        return shiroFilterFactoryBean;
    }


    //2.创建安全管理器
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(Realm realm) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        //给安全管理器设置
        defaultWebSecurityManager.setRealm(realm);

        return defaultWebSecurityManager;
    }

    //3.创建自定义realm
    @Bean("realm")
    public Realm getRealm() {
        CustomerRealm customerRealm = new CustomerRealm();

        //修改凭证校验匹配器
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //设置加密算法为md5
        credentialsMatcher.setHashAlgorithmName("MD5");
        //设置散列次数
        credentialsMatcher.setHashIterations(1024);
        customerRealm.setCredentialsMatcher(credentialsMatcher);


        //开启缓存管理
        customerRealm.setCacheManager(redisCacheManager);
        customerRealm.setCachingEnabled(true);//开启全局缓存
        customerRealm.setAuthenticationCachingEnabled(true);//认证认证缓存
        customerRealm.setAuthenticationCacheName("WEB:AUTH-CACHE");
        customerRealm.setAuthorizationCachingEnabled(true);//开启授权缓存
        customerRealm.setAuthorizationCacheName("WEB:AUTH-CACHE");
        return customerRealm;
    }

}
