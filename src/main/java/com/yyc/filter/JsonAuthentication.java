package com.yyc.filter;

import com.alibaba.fastjson.JSON;
import com.yyc.entity.Result;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.PathMatchingFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class JsonAuthentication extends PathMatchingFilter {
    private static final Logger logger = LogManager.getLogger(JsonAuthority.class);

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        logger.info("enter jsonAuthc");
        /**认证成功*/
        if (SecurityUtils.getSubject().isAuthenticated()) return true;
        else {
            /**认证失败以JSON形式返回*/
            Result<String> res = new Result<>("凭证已过期", false);
            HttpServletResponse resp = (HttpServletResponse) response;
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json;charset=UTF-8");
            resp.getWriter().write(JSON.toJSONString(res));
            return false;
        }
    }
}
