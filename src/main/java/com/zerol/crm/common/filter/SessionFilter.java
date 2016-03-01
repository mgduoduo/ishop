package com.zerol.crm.common.filter;

import com.zerol.crm.common.constant.CommonConstant;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by gaoqiang on 23/1/2015.
 */

/**
 * 登录过滤器
 */
public class SessionFilter extends OncePerRequestFilter {

    /*
     *
     * @see
     * org.springframework.web.filter.OncePerRequestFilter#doFilterInternal(
     * javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 不过滤的uri
        String[] notFilter = new String[] { "login", "login.ftl","logon", "logon.ftl", "login.jsp" };

//        if (CommonConstant.CONTEXT_NAME.equals(request.getContextPath().toLowerCase())) {
            // 请求的uri
            String uri = request.getRequestURI();

            // 是否过滤
            boolean doFilter = true;
            for (String s : notFilter) {
                if (uri.endsWith(s)) {
                    // 如果uri中包含不过滤的uri，则不进行过滤
                    doFilter = false;
                    break;
                }
            }
            if (doFilter) {
                // 执行过滤
                // 从session中获取登录者实体
                Object obj = request.getSession().getAttribute(CommonConstant.USER_SESSION_OBJECT);
                if (null == obj) {
                    // 如果session中不存在登录者实体，则弹出框提示重新登录
                    // 设置request和response的字符集，防止乱码
                    request.setCharacterEncoding("UTF-8");
                    response.setCharacterEncoding("UTF-8");
                    PrintWriter out = response.getWriter();
                    String loginPage = request.getServletPath()+request.getContextPath() + "/login.jsp";
                    StringBuilder builder = new StringBuilder();
                    builder.append("<script type=\"text/javascript\">");
                    builder.append("alert('网页过期，请重新登录！');");
                    builder.append("window.top.location.href='");
                    builder.append(loginPage);
                    builder.append("';");
                    builder.append("</script>");
                    out.print(builder.toString());
                } else {
                    // 如果session中存在登录者实体，则继续
                    filterChain.doFilter(request, response);
                    response.sendRedirect(request.getContextPath() + "/login.jsp");
                }
            } else {
                // 如果不执行过滤，则继续
                filterChain.doFilter(request, response);
            }
//        } else {
//            // 继续
//            filterChain.doFilter(request, response);
//        }
    }

}
