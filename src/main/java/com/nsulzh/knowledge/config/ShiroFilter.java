package com.nsulzh.knowledge.config;


import com.alibaba.fastjson.JSONObject;
import com.nsulzh.knowledge.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ShiroFilter extends AuthenticationFilter {
    @Override
    protected void redirectToLogin(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException {
        // 返回json
        servletResponse.setContentType("application/json; charset=utf-8");
        Map<String, Object> res = new HashMap<String, Object>();
        res.put("code", 401);
        res.put("msg", "未登录或者登录已失效");
        String json = JSONObject.toJSONString(res);
        servletResponse.getWriter().write(json);

//
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        String ajax = request.getHeader("x-requested-with");
//        if (null == ajax) {
//            log.info("请求非法，跳转至等登录界面");
//            response.sendRedirect("/login");
//        } else {
//            response.setContentType("text/html;charset=utf-8");
//            response.getWriter().write("访问有问题");
//        }

    }
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse response1) throws Exception {
        this.saveRequestAndRedirectToLogin(servletRequest,response1);
        return false;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse response, Object mappedValue) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String token = request.getHeader(TokenUtil.tokenHeard);
        log.info(token);
        if (null == token || "".equals(token)) {
            log.info("token为空");
            return false;
        }
        //验证token的真实性
        try {
            TokenUtil.getTokenBody(token);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("token有问题");
            return false;
        }
        return true;
    }
}