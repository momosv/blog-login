package cn.momosv.blog.login.component.impl;

import cn.momosv.blog.common.util.Constants;
import cn.momosv.blog.login.component.TokenManager;
import cn.momosv.blog.login.model.UserInfoPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpServletRequest;

@Component("authManager")
public class AuthManager {

    @Autowired
    private TokenManager tokenManager;

    /**
     * 获取请求体
     * @return
     */
    public HttpServletRequest getRequest(){
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 登录
     * @param userInfo
     * @return
     */
    public String signIn(UserInfoPO userInfo){
        return tokenManager.getToken(userInfo);
    }

    /**
     * 获取该访问用户信息
     * @return
     */
    public UserInfoPO getUserInfo() throws AuthException {
        HttpServletRequest request=getRequest();
        String token=request.getAttribute(Constants.USER_TOKEN).toString();
        UserInfoPO userInfo=tokenManager.getUserInfoByToken(token);
        if(userInfo==null){
            throw new AuthException("该用户已过期"+"|"+ HttpStatus.UNAUTHORIZED.value());
        }
        refreshUserInfo();
        return userInfo;
    }

    /**
     * 刷新该登录用户，延时
     */
    public void refreshUserInfo(){
        HttpServletRequest request=getRequest();
        String token=request.getAttribute(Constants.USER_TOKEN).toString();
        tokenManager.refreshUserToken(token);
    }

    /**
     * 注销该访问用户
     */
    public void loginOff(){
        HttpServletRequest request=getRequest();
        String token=request.getAttribute(Constants.USER_TOKEN).toString();
        tokenManager.loginOff(token);
    }
}
