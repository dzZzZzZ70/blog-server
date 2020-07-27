package com.dz.blogserver.config.shiro;

import com.alibaba.fastjson.JSONObject;
import com.dz.blogserver.vo.result.ResultEntity;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author dz
 * @date 2020/7/8
 * @time 22:21
 */
public class ShiroFormAuthenticationFilter extends FormAuthenticationFilter {
    private static final Logger log = LoggerFactory.getLogger(ShiroFormAuthenticationFilter.class);

    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (this.isLoginRequest(request, response)) {
            if (this.isLoginSubmission(request, response)) {
                if (log.isTraceEnabled()) {
                    log.trace("Login submission detected.  Attempting to execute login.");
                }

                return this.executeLogin(request, response);
            } else {
                if (log.isTraceEnabled()) {
                    log.trace("Login page view.");
                }

                return true;
            }
        } else {
            // 处理未登录重定向
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse resp = (HttpServletResponse) response;
            if (req.getMethod().equals(RequestMethod.OPTIONS.name())) {
                resp.setStatus(HttpStatus.OK.value());
                return true;
            }
            if (log.isTraceEnabled()) {
                log.trace("Attempting to access a path which requires authentication.  Forwarding to the Authentication url [" + this.getLoginUrl() + "]");
            }

            resp.setHeader("Access-control-Allow-Origin", req.getHeader("Origin"));
            resp.setHeader("Access-Control-Allow-Credentials", "true");
            resp.setContentType("application/json; charset=utf-8");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter out = resp.getWriter();
            ResultEntity result = new ResultEntity();
            result.failed("outOfDate");
            out.println(JSONObject.toJSONString(result));
            out.flush();
            out.close();
//            this.saveRequestAndRedirectToLogin(request, response);
            return false;
        }
    }
}
