package com.villege.common.annotation;

import java.lang.annotation.*;

/**
 * 系统日志注解
 *
 * @author 赖国荣 31343080@qq.com
 * @Target 所有标注了指定注解的类
 * @Retention 注解说明, 这种类型的注解会被保留到那个阶段.
 * @Documented 注解表明这个注解应该被 javadoc工具记录.
 * <p>
 * Retention(保留)注解说明,这种类型的注解会被保留到那个阶段. 有三个值:
 * 1.RetentionPolicy.SOURCE —— 这种类型的Annotations只在源代码级别保留,编译时就会被忽略
 * 2.RetentionPolicy.CLASS —— 这种类型的Annotations编译时被保留,在class文件中存在,但JVM将会忽略
 * 3.RetentionPolicy.RUNTIME —— 这种类型的Annotations将被JVM保留,所以他们能在运行时被JVM或其他使用反射机制的代码所读取和使用.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

    String value() default "";
}
