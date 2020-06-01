package com.mashirro.xinfang_system.shiro;

import com.mashirro.xinfang_system.entity.User;
import com.mashirro.xinfang_system.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 自定义realm,推荐继承AuthorizingRealm
 */
@Component
public class UserRealm extends AuthorizingRealm {

    private final static Logger logger = LoggerFactory.getLogger(UserRealm.class);

    @Autowired
    private UserService userService;

    /**
     * 授权
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //authorizationInfo.setRoles(userService.findRoles(username));
        //authorizationInfo.setStringPermissions(userService.findPermissions(username));
        return authorizationInfo;
    }

    /**
     * 身份验证
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String loginAccount = upToken.getUsername();
        String password = String.valueOf(upToken.getPassword());
        User user = userService.findUserByLoginAccount(loginAccount);
        if (user == null) {
            throw new UnknownAccountException();// 没找到帐号
        }
//        if (!user.getPassword().equals(password)) {
//            throw new IncorrectCredentialsException();
//        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getLoginAccount(), // 用户帐号
                user.getPassword(), // 密码
                getName() // realm name
        );
        return authenticationInfo;
    }

}
