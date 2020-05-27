//package com.mashirro.xinfang_system.shiro;
//
//import com.mashirro.xinfang_system.entity.User;
//import com.mashirro.xinfang_system.service.UserService;
//import org.apache.shiro.authc.*;
//import org.apache.shiro.authz.AuthorizationInfo;
//import org.apache.shiro.authz.SimpleAuthorizationInfo;
//import org.apache.shiro.realm.AuthorizingRealm;
//import org.apache.shiro.subject.PrincipalCollection;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//
///**
// * 自定义realm,推荐继承AuthorizingRealm
// */
//public class MyRealm extends AuthorizingRealm {
//
//    private final static Logger log = LoggerFactory.getLogger(MyRealm.class);
//
//    @Autowired
//    private UserService userService;
//
//    @Override
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//        String username = (String) principals.getPrimaryPrincipal();
//        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
//        //authorizationInfo.setRoles(userService.findRoles(username));
//        //authorizationInfo.setStringPermissions(userService.findPermissions(username));
//        return authorizationInfo;
//    }
//
//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
//        String loginAccount = (String) token.getPrincipal();
//        User user = userService.findByUsername(loginAccount);
//        if (user == null) {
//            throw new UnknownAccountException();// 没找到帐号
//        }
//
//        if (1 == user.getIsLocked()) {
//            throw new LockedAccountException(); // 帐号锁定
//        }
//
//        // 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
//        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getLoginAccount(), // 用户帐号
//                user.getPassword(), // 密码
//                getName() // realm name
//        );
//        return authenticationInfo;
//    }
//
//}
