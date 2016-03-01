package com.zerol.crm.common.interceptor;

import com.zerol.crm.common.constant.CommonConstant;
import com.zerol.crm.common.util.UUIDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoqiang on 30/1/2015.
 */
public class TokenHelper {
    private final static Logger LOGGER = LoggerFactory.getLogger(TokenHelper.class);

    static Map<String, String> springmvc_token_map = null;
    private final static String SPRINGMVC_TOKEN="SPRINGMVC.TOKEN";

    //生成一个唯一值的token
    @SuppressWarnings("unchecked")
    public synchronized static String generateGUID(HttpSession session) {
        String token = "";
        try {
            Object obj = session.getAttribute(SPRINGMVC_TOKEN);
            if (obj != null)
                springmvc_token_map = (Map<String, String>) session.getAttribute(SPRINGMVC_TOKEN);
            else
                springmvc_token_map = new HashMap<String, String>();

//            token = new BigInteger(165, new Random()).toString(36).toUpperCase();
            token = UUIDGenerator.getUUID();

            springmvc_token_map.put(CommonConstant.DEFAULT_TOKEN_NAME, token);
            springmvc_token_map.put(CommonConstant.DEFAULT_TOKEN_NAME + "." + token, token);
            session.setAttribute(SPRINGMVC_TOKEN, springmvc_token_map);

        } catch (IllegalStateException e) {
            LOGGER.error("generateGUID() mothod find bug,by token session...");
        }
        return token;
    }

    //验证表单token值和session中的token值是否一致
    @SuppressWarnings("unchecked")
    public static boolean validToken(HttpServletRequest request) {
        String inputToken = getInputToken(request);

        if (inputToken == null) {
            LOGGER.warn("token is not valid!inputToken is NULL");
            return false;
        }

        HttpSession session = request.getSession();
        Map<String, String> tokenMap = (Map<String, String>) session.getAttribute(SPRINGMVC_TOKEN);
        if (tokenMap == null || tokenMap.size() < 1) {
            LOGGER.warn("token is not valid!sessionToken is NULL");
            return false;
        }
        String sessionToken = tokenMap.get(CommonConstant.DEFAULT_TOKEN_NAME + "." + inputToken);
        if (!inputToken.equals(sessionToken)) {
            LOGGER.warn("token is not valid!inputToken='" + inputToken
                    + "',sessionToken = '" + sessionToken + "'");
            return false;
        }
        tokenMap.remove(CommonConstant.DEFAULT_TOKEN_NAME + "." + inputToken);
        session.setAttribute(SPRINGMVC_TOKEN, tokenMap);

        return true;
    }

    //获取表单中token值
    @SuppressWarnings("unchecked")
    public static String getInputToken(HttpServletRequest request) {
        Map params = request.getParameterMap();

        if (!params.containsKey(CommonConstant.DEFAULT_TOKEN_NAME)) {
            LOGGER.warn("Could not find token name in params.");
            return null;
        }

        String[] tokens = (String[]) params.get(CommonConstant.DEFAULT_TOKEN_NAME);

        if ((tokens == null) || (tokens.length < 1)) {
            LOGGER.warn("Got a null or empty token name.");
            return null;
        }

        return tokens[0];
    }
}
