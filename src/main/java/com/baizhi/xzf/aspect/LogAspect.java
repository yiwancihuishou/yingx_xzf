package com.baizhi.xzf.aspect;

import com.baizhi.xzf.annotation.AddLog;
import com.baizhi.xzf.dao.LogMapper;
import com.baizhi.xzf.entity.Admin;
import com.baizhi.xzf.entity.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.UUID;

@Aspect
@Configuration
public class LogAspect {
    @Resource
    HttpSession session;
    @Resource
    LogMapper logMapper;

    @Around("@annotation(com.baizhi.xzf.annotation.AddLog)")
    public Object addLog(ProceedingJoinPoint joinPoint){
        Log log = new Log();
        String message=null;
        //谁操作
        Admin admin = (Admin) session.getAttribute("admin");

        //操作 哪个方法
        //获取操作的方法名
        String methodName = joinPoint.getSignature().getName();

        //获取方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        //获取方法上的注解
        AddLog addLog = method.getAnnotation(AddLog.class);
        //在获取注解里面的内容
        String value = addLog.value();
        try {
            //放行
            Object proceed = joinPoint.proceed();
            message="success";
            log.setId(UUID.randomUUID().toString());
            //因为要测试 不能老是登录  所以这里操作员写死为admin   本来应该admin.getUserName获取的
            log.setAdminName("admin");
            log.setDate(new Date());
            log.setOperation(value+methodName);
            log.setStatus(message);
            logMapper.insert(log);

            return proceed;

        } catch (Throwable throwable) {
            throwable.printStackTrace();
            message="error";
            return null;
        }

    }

    //@Around("execution(* com.baizhi.xzf.serviceImpl.*.*(..)) && !execution(* com.baizhi.xzf.serviceImpl.*.query*(..))")
    /*public Object addLog1(ProceedingJoinPoint joinPoint){
        //谁操作
        Admin admin = (Admin) session.getAttribute("admin");
        //时间
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(date);

        //操作 哪个方法
        //获取操作的方法名
        String methodName = joinPoint.getSignature().getName();
        try {
            //放行
            Object proceed = joinPoint.proceed();
            String message="success";
            System.out.println("管理员："+admin+"---时间："+format+"---操作的方法："+methodName+"---状态："+message);
            return  proceed;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            String message="error";
            return  null;
        }

    }*/
}
