package cn.momosv.blog.login.interceptor;

import cn.momosv.blog.base.interfaces.AuthIgnore;
import cn.momosv.blog.base.redis.util.RedisUtils;
import cn.momosv.blog.base.mybatis.exception.MyException;
import cn.momosv.blog.common.util.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component("authorizationInterceptor")
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

     final static String[] ALLOW_PATH={"/webjars"};

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("接收到请求:"+request.getServletPath());
        for (String s : ALLOW_PATH) {
            if(request.getServletPath().contains(s)){
            System.out.println("不需拦截:"+request.getServletPath());
                return true;
            }
        }

        AuthIgnore annotation;
        if(handler instanceof HandlerMethod) {
            annotation = ((HandlerMethod) handler).getMethodAnnotation(AuthIgnore.class);
        }else{
            return true;
        }

        //如果有@AuthIgnore注解，则不验证token
        if(annotation != null){
            return true;
        }

        //获取用户凭证
        String token = request.getHeader(Constants.USER_TOKEN);
        if(StringUtils.isEmpty(token)){
            token = request.getParameter(Constants.USER_TOKEN);
        }
        if(StringUtils.isEmpty(token)){
            Object obj = request.getSession().getAttribute(Constants.USER_TOKEN);
            if(null!=obj){
                token=obj.toString();
            }
        }

        //token凭证为空
        if(StringUtils.isEmpty(token)){
            throw new AuthException(Constants.USER_TOKEN + "不能为空"+"|"+ HttpStatus.UNAUTHORIZED.value());
        }
        if(!RedisUtils.hasKey(token)){
            throw new MyException("登录已经过期");
        }
       // RedisUtils.expire(token,60*30);

        return true;
    }
}
