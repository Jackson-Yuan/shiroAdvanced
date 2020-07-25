package com.yyc.filter;

import com.alibaba.fastjson.JSON;
import com.yyc.entity.Result;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class JsonAuthority extends AccessControlFilter {
    private static final Logger logger = LogManager.getLogger(JsonAuthority.class);

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        logger.info("enter jsonRole isAccessAllowed");
        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated()) return false;
        String[] roles = (String[]) o;
        if (roles == null) return true;
        for (String role : roles) {
            if (subject.hasRole(role)) return true;
        }
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        logger.info("enter jsonRole onAccessDenied");
        Result<String> res = new Result<>("凭证已过期或没有相应权限", false);
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=UTF-8");
        resp.getWriter().write(JSON.toJSONString(res));
        return false;
    }
}
