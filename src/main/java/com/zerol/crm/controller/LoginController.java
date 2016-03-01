package com.zerol.crm.controller;

import com.zerol.crm.common.constant.CommonConstant;
import com.zerol.crm.common.util.DateUtil;
import com.zerol.crm.entry.common.User;
import com.zerol.crm.service.web.ProdService;
import com.zerol.crm.service.web.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);


    @Autowired
    ProdService prodService;
    @Autowired
    UserService userService;

    public void setProdService(ProdService prodService) {
        this.prodService = prodService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * @param request
     * @param response
     * @return 登陆
     */
    @RequestMapping(value="/logon.do", method = RequestMethod.POST)
    public ModelAndView logon(@RequestParam("username")  String username, @RequestParam("password")  String password, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();

        //String username = user.getUsername();//unused : request.getParameter("username");
        //String password = user.getPassword();//unused : request.getParameter("password");
        User loginUser = userService.findUserByUsernamePassword(username, password);
        if (loginUser == null) {
            request.setAttribute("errInd",CommonConstant.COMMON_BOOLEAN_Y);
            String loginUrl = "/login.jsp";
            return new ModelAndView("redirect:" + loginUrl);
        }

        HttpSession session = request.getSession(false);
        request.getSession().invalidate();//1
        session = request.getSession(true);
        loginUser.setLastLoginDate(DateUtil.getDate());
        session.setAttribute(CommonConstant.USER_SESSION_OBJECT, loginUser);

        return loginSuccess();
    }

    public ModelAndView loginSuccess() {
        String projectUrl = "/iShop/preSearch.do";
        return new ModelAndView("redirect:" + projectUrl);
    }

    @RequestMapping("/login.do")
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
        String loginUrl = "/login.jsp";
        return new ModelAndView("redirect:" + loginUrl);
    }

    @RequestMapping("/logout.do")
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(CommonConstant.USER_SESSION_OBJECT);
            session.invalidate();//1
        }
        String loginUrl = "/login.jsp";
        return new ModelAndView("redirect:" + loginUrl);
    }
}
