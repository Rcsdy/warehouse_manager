package com.pn.filter;

import com.alibaba.fastjson.JSON;
import com.pn.entity.Result;
import com.pn.utils.WarehouseConstants;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class LoginCheckFilter implements Filter {

    private StringRedisTemplate stringRedisTemplate;

    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate){
        this.stringRedisTemplate = stringRedisTemplate;
    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        List<String> userList = new ArrayList<>();
        userList.add("/login");
        userList.add("/captcha/captchaImage");
        userList.add("/logout");
        userList.add("/product/img-upload");

        String url = request.getServletPath();
        if(userList.contains(url) || url.contains("/img/upload")){
            filterChain.doFilter(request,response);
            return;
        }

        String token = request.getHeader(WarehouseConstants.HEADER_TOKEN_NAME);
        if(StringUtils.hasText(token) && stringRedisTemplate.hasKey(token)){
            filterChain.doFilter(request,response);
            return;
        }
        Result err = Result.err(Result.CODE_ERR_UNLOGINED, "未登录");
        String jsonString = JSON.toJSONString(err);
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(jsonString);
        writer.flush();
        writer.close();
    }
}
