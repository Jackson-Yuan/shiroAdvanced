package com.yyc.realm;

import com.yyc.dao.UserMapper;
import com.yyc.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

public class WebRealm extends AuthorizingRealm {
    private static final Logger logger = LogManager.getLogger(WebRealm.class);
    @Autowired
    private UserMapper userMapper;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("enter authorization");
        /**获取认证的用户名*/
        String userId = (String) principalCollection.getPrimaryPrincipal();
        /**根据用户名，从数据库获取其权限信息*/
        User user = userMapper.selectByPrimaryKey(userId);
        /**将权限信息放入Set集合，并将其封装成AuthorizationInfo的实例并当做返回值，SecurityManager会将其与规定好的权限进行比较*/
        Set<String> roles = new HashSet<>();
        roles.add(user.getAuthority());
        return new SimpleAuthorizationInfo(roles);
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info("enter authentication");
        /**根据login方法传入的凭证获取用户名*/
        String userId = (String) authenticationToken.getPrincipal();
        /**根据用户名从数据库中查找此用户的信息*/
        User user = userMapper.selectByPrimaryKey(userId);
        /**因密码都需要加密，这里利用用户名进行盐值加密提高加密程度*/
        ByteSource salt = ByteSource.Util.bytes(userId);
        /**将从数据库中获取的用户名，密码，盐值，与自定义Realm名称封装成AuthenticationInfo的实例并当做返回值，SecurityManager会将其与传入的凭证比对*/
        return new SimpleAuthenticationInfo(user.getId(), user.getPassword(), salt, getName());
    }

    /**
     * 清除缓存的方法
     */
    public void clearCache() {
        Subject subject = SecurityUtils.getSubject();
        super.clearCache(subject.getPrincipals());
    }
}
