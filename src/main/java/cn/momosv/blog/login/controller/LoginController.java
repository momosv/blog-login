package cn.momosv.blog.login.controller;



import cn.momosv.blog.base.interfaces.AuthIgnore;
import cn.momosv.blog.base.mybatis.model.base.Msg;
import cn.momosv.blog.common.util.Constants;
import cn.momosv.blog.login.baseController.BaseController;

import cn.momosv.blog.login.model.UserInfoPO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpServletRequest;

@RestController
public class LoginController extends BaseController {



    @AuthIgnore
    @RequestMapping("/login")
    public Object login(UserInfoPO user,String id, HttpServletRequest servletRequest){
        String token = authManager.signIn(user);
        servletRequest.getSession().setAttribute(Constants.USER_TOKEN,token);
        return Msg.success("success").add(Constants.USER_TOKEN,token);
    }

    @RequestMapping("/lo")
    public Object lo() throws AuthException {
        UserInfoPO user = getUser();
        return Msg.success("success").add("user",user);
    }

}
