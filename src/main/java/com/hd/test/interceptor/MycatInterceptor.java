package com.hd.test.interceptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.Properties;

@Component
/**
 * 自定义拦截器
 * 拦截StatementHandler类中参数类型为Statement的 prepare 方法
 */
@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})
//        ,@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
})
public class MycatInterceptor implements Interceptor {

    private static final Log LOGGER = LogFactory.getLog(MycatInterceptor.class);

    private static final String SCHEMA_SCRIPT_START = "/*!mycat:schema = ";

    private static final String SCHEMA_SCRIPT_END = " */";

    /**
     * 属性赋值,没什么乱用（这里）
     */
    private Properties properties = new Properties();


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        BoundSql boundSql = statementHandler.getBoundSql();
        // 拦截sql 适用于type为Executor
//        Object[] args = invocation.getArgs();
//        MappedStatement statement = (MappedStatement) args[0];
//        Object parameterObject = args[1];
//        BoundSql boundSql = statement.getBoundSql(parameterObject);
        String sql = boundSql.getSql();
        LOGGER.info("原生SQL为：{" + sql + "}");
//        String newSql = SCHEMA_SCRIPT_START + sql + SCHEMA_SCRIPT_END;
//        LOGGER.info("新的SQL为：{" + newSql + "}");
        //通过反射修改sql语句
//        Field field = boundSql.getClass().getDeclaredField("sql");
//        field.setAccessible(true);
//        field.set(boundSql, newSql);
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        this.properties = properties;
        LOGGER.info("属性为：{" + this.properties.toString() + "}");
    }
}
