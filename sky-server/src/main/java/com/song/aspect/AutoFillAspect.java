package com.song.aspect;

import com.song.annotation.AutoFill;
import com.song.constant.AutoFillConstant;
import com.song.context.BaseContext;
import com.song.enumeration.OperationType;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Component
@Aspect
public class AutoFillAspect {


    //切入点
    @Pointcut("execution(* com.song.mapper.*.*(..)) && @annotation(com.song.annotation.AutoFill)")
    public void autoFillPointCut() {}

    //执行时机
    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint) {
        //先获取注解中的操作类型
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        AutoFill autoFill = signature.getMethod().getDeclaredAnnotation(AutoFill.class);
        OperationType operationType = autoFill.value();

        //再获取到参数中的实体
        Object[] args = joinPoint.getArgs();
        if(args == null ||  args.length == 0){
            return;
        }
        Object entity = args[0];

        //获取到所需的值
        LocalDateTime now = LocalDateTime.now();
        Long user = BaseContext.getThreadLocal();

        //通过反射给实体类赋值

        if(operationType == OperationType.INSERT){
            try {
                Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setCreateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
                setCreateUser.invoke(entity,user);
                setUpdateUser.invoke(entity,user);
                setCreateTime.invoke(entity,now);
                setUpdateTime.invoke(entity,now);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else if(operationType == OperationType.UPDATE){
            try {
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
                setUpdateUser.invoke(entity,user);
                setUpdateTime.invoke(entity,now);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }


    }
}
