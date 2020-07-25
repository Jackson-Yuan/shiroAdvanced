package com.yyc.manager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

public class CustomWebSessionManager extends DefaultWebSessionManager {
    private static final String HEAD_TOKEN_NAME = "weChatId";
    private static final Logger logger = LogManager.getLogger(CustomWebSessionManager.class);

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        String weChatId = WebUtils.toHttp(request).getHeader(HEAD_TOKEN_NAME);
        if (StringUtils.isEmpty(weChatId)) {
            logger.info("enter defaultWebSessionManager");
            return super.getSessionId(request, response);
        } else {
            logger.info("enter customWebSessionManager");
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, "weChat Request");
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, weChatId);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return weChatId;
        }
    }
}
