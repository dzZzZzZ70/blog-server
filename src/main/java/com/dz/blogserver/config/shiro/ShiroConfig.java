package com.dz.blogserver.config.shiro;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //拦截器.
        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 配置不会被拦截的链接 顺序判断
        filterChainDefinitionMap.put("/", "anon");
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");//img
        filterChainDefinitionMap.put("/css/**", "anon");//css
        filterChainDefinitionMap.put("/js/**", "anon");//js
        filterChainDefinitionMap.put("/fonts/**", "anon"); // font
        filterChainDefinitionMap.put("/favicon.ico", "anon"); // ohter
        filterChainDefinitionMap.put("/sayHello", "anon");
        filterChainDefinitionMap.put("/controller/setting/findSettingBlogType", "anon");
        filterChainDefinitionMap.put("/controller/blog/findBlogByBlogType", "anon");
        filterChainDefinitionMap.put("/controller/blog/findBlog", "anon");
        filterChainDefinitionMap.put("/controller/normal", "anon");

        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/logout", "logout");
        //<!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
        //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
        filterChainDefinitionMap.put("/**", "authc");
        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/index");

        LinkedHashMap<String, Filter> filtsMap=new LinkedHashMap<String, Filter>();
        filtsMap.put("authc",new ShiroFormAuthenticationFilter() );
        shiroFilterFactoryBean.setFilters(filtsMap);
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        //未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public CustomerShiroRealm customerShiroRealm(){
        return new CustomerShiroRealm();
    }


    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
        securityManager.setRealm(customerShiroRealm());
        return securityManager;
    }
}
