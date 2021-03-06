package com.sccl.data_source_change.aspectj.annotation;


import com.sccl.data_source_change.enumConst.DataSourceEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**自定义多数据源切换注解
 * Create by wangbin
 * 2019-11-18-15:25
 */

/**
 * 注解说明：
 * @author wangbin
 * @date 2019/11/18 15:36

源码样例：

 @Target(ElementType.METHOD)
 @Retention(RetentionPolicy.RUNTIME)
 @Documented
 @Inherited
 public @interface MthCache {
 String key();
 }

 @Target 注解

 功能：指明了修饰的这个注解的使用范围，即被描述的注解可以用在哪里。

 ElementType的取值包含以下几种：

 TYPE:类，接口或者枚举
 FIELD:域，包含枚举常量
 METHOD:方法
 PARAMETER:参数
 CONSTRUCTOR:构造方法
 LOCAL_VARIABLE:局部变量
 ANNOTATION_TYPE:注解类型
 PACKAGE:包
 =======================================================================================
 @Retention 注解

 功能：指明修饰的注解的生存周期，即会保留到哪个阶段。

 RetentionPolicy的取值包含以下三种：

 SOURCE：源码级别保留，编译后即丢弃。
 CLASS:编译级别保留，编译后的class文件中存在，在jvm运行时丢弃，这是默认值。
 RUNTIME： 运行级别保留，编译后的class文件中存在，在jvm运行时保留，可以被反射调用。

 ====================================================================================
 @Documented 注解

 功能：指明修饰的注解，可以被例如javadoc此类的工具文档化，只负责标记，没有成员取值。
 ========================================================================================
 @Inherited注解

 功能：允许子类继承父类中的注解。

 注意！：

 @interface意思是声明一个注解，方法名对应参数名，返回值类型对应参数类型。
 */
 @Target(ElementType.METHOD) //此注解使用于方法上
 @Retention(RetentionPolicy.RUNTIME) //此注解的生命周期为：运行时，在编译后的class文件中存在，在jvm运行时保留，可以被反射调用
public @interface DataSource {
    /**
     * 切换数据源值
     */
    DataSourceEnum value() default DataSourceEnum.MASTER;
}
