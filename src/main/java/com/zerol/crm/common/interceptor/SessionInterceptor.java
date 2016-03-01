package com.zerol.crm.common.interceptor;

/**
 * Created by gaoqiang on 30/1/2015.
 */

import com.zerol.crm.common.constant.CommonConstant;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class SessionInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        if(!uri.endsWith(".do")){
            return true;
        }

        // 不过滤的uri
        String[] nonFilter = new String[]{"login.do", "logon.do", "logout.do", "login.jsp"};

        // 是否过滤
        boolean doFilter = true;
        for (String s : nonFilter) {
            if (uri.endsWith(s)) {
                // 如果uri中包含不过滤的uri，则不进行过滤
                doFilter = false;
                break;
            }
        }

        if (doFilter) {
            Object obj = request.getSession().getAttribute(CommonConstant.USER_SESSION_OBJECT);
            if (obj == null) {
                response.sendRedirect(request.getContextPath()+"/login.jsp");
                return false;
            }
        }
        return true;
    }
}
