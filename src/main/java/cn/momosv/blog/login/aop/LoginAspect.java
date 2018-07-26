package cn.momosv.blog.login.aop;


import cn.momosv.blog.base.interfaces.AuthIgnore;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by wuwf on 17/4/27.
 * 日志切面
 */
@Aspect
@Component
public class LoginAspect {
    @Pointcut("execution(* cn.momosv.blog.login.controller.*.*(..))")
    public void webLogin(){}

    //环绕通知,环绕增强，相当于MethodInterceptor
    @Around("webLogin()")
    public Object arround(ProceedingJoinPoint pjp) {
        System.out.println("方法环绕start.....");

        //获取被注解的方法
        MethodInvocationProceedingJoinPoint mjp = (MethodInvocationProceedingJoinPoint) pjp;
        MethodSignature signature = (MethodSignature) mjp.getSignature();
        Method method = signature.getMethod();
        AuthIgnore annotation = method.getAnnotation(AuthIgnore.class);
        if(null == annotation){
            annotation = method.getDeclaringClass().getAnnotation(AuthIgnore.class);
        }
        if(annotation!=null){
            System.out.println("方法不用拦截");
        }
        try {
            Object o =  pjp.proceed();
            System.out.println("方法环绕proceed，结果是 :" + o);
            return o;
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

    @Before("webLogin()")
    public void deBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        System.out.println("URL : " + request.getRequestURL().toString());
        System.out.println("HTTP_METHOD : " + request.getMethod());
        System.out.println("IP : " + request.getRemoteAddr());
        System.out.println("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        System.out.println("ARGS : " + Arrays.toString(joinPoint.getArgs()));

    }



    //后置最终通知,final增强，不管是抛出异常或者正常退出都会执行
    @After("webLogin()")
    public void after(JoinPoint jp){
        System.out.println("方法最后执行.....");
    }

    @AfterReturning(returning = "ret", pointcut = "webLogin()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        System.out.println("方法的返回值 : " + ret);
    }

    //后置异常通知
    @AfterThrowing("webLogin()")
    public void throwss(JoinPoint jp){
        System.out.println("方法异常时执行.....");
    }
}
