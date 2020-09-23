package com.dz.blogserver.config.shiro;

import com.dz.blogserver.entity.management.ApplicationPermission;
import com.dz.blogserver.entity.management.ApplicationRole;
import com.dz.blogserver.entity.management.ApplicationUser;
import com.dz.blogserver.service.ManagementService;
import com.dz.blogserver.vo.management.ApplicationUserVo;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomerShiroRealm extends AuthorizingRealm {
    @Autowired
    private ManagementService managementService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        ApplicationUser userInfo  = (ApplicationUser)principalCollection.getPrimaryPrincipal();
        for(ApplicationRole role:userInfo.getApplicationRolesList()){
            authorizationInfo.addRole(role.getRoleName());
            for(ApplicationPermission p:role.getApplicationPermissionList()){
                authorizationInfo.addStringPermission(p.getPermission());
            }
        }
        return authorizationInfo;
//        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("CustomerShiroRealm.doGetAuthenticationInfo()");
        //获取用户的输入的账号.
        String username = (String)token.getPrincipal();
        System.out.println(token.getCredentials());
        if (username == null || "".equals(username)) {
            return null;
        }
        //通过username从数据库中查找 User对象，如果找到，没找到.
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        ApplicationUserVo userInfo = managementService.findByUserAccount(username);
        System.out.println("----->>userInfo="+userInfo);
        if(userInfo == null){
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                userInfo, //用户名
                userInfo.getPassword(), //密码
//                ByteSource.Util.bytes(userInfo.getCredentialsSalt()),//salt=username+salt
                getName()  //realm name
        );
        return authenticationInfo;
    }
}
