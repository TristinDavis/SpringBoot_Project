package com.cxp.springboot2quartz.config.dynamic;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;

/**
 * @author : cheng
 * @date : 2020-03-11 17:22
 */
@Aspect
//保证该AOP在@Transactional之前执行
@Order(-1)
@Component
public class DynamicDataSourceAspect {

    @Around("@annotation(com.cxp.springboot2quartz.config.dynamic.DS)")
    public Object switchDS(ProceedingJoinPoint point) throws Throwable {
        Class<?> className = point.getTarget().getClass();
        String dataSource = DynamicDataSourceEnum.MASTER.name();

        if (className.isAnnotationPresent(DS.class)) {
            DS ds = className.getAnnotation(DS.class);
            dataSource = ds.value();
        } else {
            // 得到访问的方法对象
            String methodName = point.getSignature().getName();
            Class[] argClass = ((MethodSignature) point.getSignature()).getParameterTypes();
            Method method = className.getMethod(methodName, argClass);
            // 判断是否存在@DS注解
            if (method.isAnnotationPresent(DS.class)) {
                DS annotation = method.getAnnotation(DS.class);
                // 取出注解中的数据源名
                dataSource = annotation.value();
            }
        }

        // 切换数据源
        DataSourceContextHolder.setDB(dataSource);

        try {
            return point.proceed();
        } finally {
            DataSourceContextHolder.clearDB();
        }
    }
}
