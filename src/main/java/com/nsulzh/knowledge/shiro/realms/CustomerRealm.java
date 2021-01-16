package com.nsulzh.knowledge.shiro.realms;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nsulzh.knowledge.dao.UserDao;
import com.nsulzh.knowledge.entity.UserEntity;
import com.nsulzh.knowledge.shiro.salt.MyByteSource;
import com.nsulzh.knowledge.util.ApplicationContextUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.util.ObjectUtils;



//自定义realm
public class CustomerRealm extends AuthorizingRealm {

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //获取身份信息
        String primaryPrincipal = (String) principals.getPrimaryPrincipal();
        System.out.println("调用授权验证: "+primaryPrincipal);
        //根据主身份信息获取角色 和 权限信息
        UserDao userDao = (UserDao) ApplicationContextUtils
                .getBean("userDap");
        LambdaQueryWrapper<UserEntity> lambdaQueryWrapper=new LambdaQueryWrapper<UserEntity>()
                .eq(UserEntity::getUserName,primaryPrincipal);
        UserEntity user = userDao.selectOne(lambdaQueryWrapper);
        //授权角色信息
//        if(!CollectionUtils.isEmpty(user.getRoles())){
//            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
//            user.getRoles().forEach(role->{
//                simpleAuthorizationInfo.addRole(role.getName());
//                //权限信息
//                List<Perms> perms = userService.findPermsByRoleId(role.getId());
//                if(!CollectionUtils.isEmpty(perms)){
//                    perms.forEach(perm->{
//                        simpleAuthorizationInfo.addStringPermission(perm.getName());
//                    });
//                }
//            });
//            return simpleAuthorizationInfo;
//        }
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //根据身份信息
        String principal = (String) token.getPrincipal();
        //在工厂中获取service对象
        UserDao userDao = (UserDao) ApplicationContextUtils.getBean("userDao");
        LambdaQueryWrapper<UserEntity> lambdaQueryWrapper=new LambdaQueryWrapper<UserEntity>()
                .eq(UserEntity::getUserName,principal);
        UserEntity user = userDao.selectOne(lambdaQueryWrapper);
        if(!ObjectUtils.isEmpty(user)){
            return new SimpleAuthenticationInfo(user.getUserName(),user.getPassWord(),
                    new MyByteSource(user.getSalt()),
                    this.getName());
        }
        return null;
    }

}
