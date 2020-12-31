package com.mashirro.xinfang_framework.shiro.realm;


import com.mashirro.xinfang_framework.shiro.utils.PasswordUtil;
import com.mashirro.xinfang_framework.entity.SysUser;
import com.mashirro.xinfang_framework.service.SysUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
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
    private SysUserService sysUserService;

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
        //authorizationInfo.setRoles(sysUserService.findRoles(username));
        //authorizationInfo.setStringPermissions(sysUserService.findPermissions(username));
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
        SysUser sysUser = sysUserService.findUserByLoginAccount(upToken.getUsername());
        if (sysUser == null) {
            throw new UnknownAccountException();    //没找到帐号
        }
        if (sysUser.getIsLocked() == 1) {
            throw new LockedAccountException(); //账号被锁
        }
        //我们这里不需要进行凭证匹配,因为返回AuthenticationInfo实例后,会在AuthenticatingRealm的getAuthenticationInfo中进行凭证匹配this.assertCredentialsMatch(token, info);具体请参看源码
//        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(sysUser.getLoginAccount(), // 用户帐号
//                sysUser.getPassword(), // 密码
//                getName() // realm name
//        );

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(sysUser.getLoginAccount(),    //帐号
                sysUser.getPassword(),     //密码
                ByteSource.Util.bytes(sysUser.getSalt()),  //盐
                getName()); // realm name

        return authenticationInfo;
    }

    /**
     * 不配置的话默认使用SimpleCredentialsMatcher
     * 因为您要对用户的密码进行SHA-256散列，所以需要告诉Shiro使用适当的HashedCredentialsMatcher来匹配您的散列首选项
     *
     * @param credentialsMatcher
     */
    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName(PasswordUtil.ALGORITHM_NAME);
        //参看HashedCredentialsMatcher源码,因为我们使用了toHex(),这里可以不用配置,因为默认true
        matcher.setStoredCredentialsHexEncoded(true);
        matcher.setHashIterations(PasswordUtil.HASHITERATIONS);
        super.setCredentialsMatcher(matcher);
    }
}
