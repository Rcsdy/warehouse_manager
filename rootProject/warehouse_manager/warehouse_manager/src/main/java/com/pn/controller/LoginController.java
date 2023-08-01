package com.pn.controller;

import com.google.code.kaptcha.Producer;
import com.pn.entity.*;
import com.pn.service.AuthService;
import com.pn.service.UserService;
import com.pn.utils.DigestUtil;
import com.pn.utils.TokenUtils;
import com.pn.utils.WarehouseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

//@RequestMapping("/captcha")
@RestController
public class LoginController {

    @Resource(name = "captchaProducer")
    private Producer producer;

    @Autowired
    private StringRedisTemplate redisTemplate;

//    @GetMapping("/captchaImage")
    @RequestMapping("/captcha/captchaImage")
    public void captchaImage(HttpServletResponse response) {

        ServletOutputStream out = null;
        try {
            String text = producer.createText();
            BufferedImage image = producer.createImage(text);
            redisTemplate.opsForValue().set(text, "", 60 * 10, TimeUnit.SECONDS);
            response.setContentType("image/jpeg");
            out = response.getOutputStream();
            ImageIO.write(image, "jpg", out);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Autowired
    private UserService userService;
    @Autowired
    private TokenUtils tokenUtils;

    @RequestMapping("/login")
    public Result login(@RequestBody LoginUser loginUser){

        String verificationCode = loginUser.getVerificationCode();
        if(!redisTemplate.hasKey(verificationCode)) return Result.err(Result.CODE_ERR_BUSINESS,"验证码错误");

        User user = userService.queryUserByCode(loginUser.getUserCode());
        if(user != null){
            if(user.getUserState().equals(WarehouseConstants.USER_STATE_PASS)){
                String userPwd = DigestUtil.hmacSign(loginUser.getUserPwd());
                if(user.getUserPwd().equals(userPwd)){
                    CurrentUser currentUser = new CurrentUser(user.getUserId(),user.getUserCode(),user.getUserName());
                    String token = tokenUtils.loginSign(currentUser, userPwd);
                    return Result.ok("登录成功",token);
                }else{
                    return Result.err(Result.CODE_ERR_BUSINESS,"密码错误");
                }
            }else{
                return Result.err(Result.CODE_ERR_BUSINESS,"账号未审核");
            }
        }else{
            return Result.err(Result.CODE_ERR_BUSINESS,"账号不存在");
        }
    }


    @RequestMapping("/curr-user")
    public Result currentUser(@RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token){
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);

        return Result.ok(currentUser);
    }

    @Autowired
    private AuthService authService;

    @RequestMapping("/user/auth-list")
    public Result loadAuthTree(@RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token){
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        int userId = currentUser.getUserId();
        List<Auth> authTreeList = authService.authTreeByUid(userId);

        return Result.ok(authTreeList);
    }

    @RequestMapping("/logout")
    public Result logout(@RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token){
        redisTemplate.delete(token);
        return Result.ok("推出成功");
    }

}
