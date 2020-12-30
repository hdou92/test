package com.hd.test.config;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hd.test.common.StringUtils;
import com.hd.test.db.dao.UserMapper;
import com.hd.test.db.entity.User;
import lombok.AllArgsConstructor;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * 拦截器 ， 拦截scc_order_info 表 scc_order_packer 表 记录的数据变化 如果变化则放入kafka 通知到es同步修改
 *
 * @author houdu
 * @date 2020/12/16
 */
@Intercepts({
        //type指定代理的是那个对象，method指定代理Executor中的那个方法,args指定Executor中的query方法都有哪些参数对象
        //由于Executor中有两个query，因此需要两个@Signature
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})//需要代理的对象和方法
})
@AllArgsConstructor
public class SqlLoggerInterceptor implements Interceptor {

    /**
     * 中台订单 常变字段  如果修改表中有如下字段  则发送kafka通知ES更新
     */
    private final static List<String> MONITOR_COLUMN_LIST = Collections.singletonList(
            "age"
    );

    /**
     * orderId 字符串
     */
    private static final String ORDER_ID_STRING = "id";

    /**
     * 拦截器处理方法
     *
     * @param invocation 调用对象
     * @return 放行
     * @throws Throwable 异常
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object proceed = invocation.proceed();
        try {
            if (proceed instanceof Integer) {
                int result = (Integer) proceed;
                if (result < 1) {
                    return proceed;
                }
            } else if (proceed instanceof Boolean) {
                boolean result = (Boolean) proceed;
                if (!result) {
                    return proceed;
                }
            }
            MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
            SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
            // 只处理更新操作
            if (sqlCommandType != SqlCommandType.UPDATE) {
                return proceed;
            }
            // sql执行的ID
            String sqlId = mappedStatement.getId();
            // 获取到mapper 全名  防止有重复名称的mapper
            String className = sqlId.substring(0, sqlId.lastIndexOf("."));
            // 不是 user
            if (StringUtils.isNotEqualsAndNotEmpty(className, UserMapper.class.getName())) {
                return proceed;
            }
            checkColumnIsSendKafka(invocation.getArgs()[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return proceed;
    }

    /**
     * 检查是否发送kafka
     *
     * @param obj 参数
     */
    private void checkColumnIsSendKafka(Object obj) {
        MapperMethod.ParamMap<?> paramMap = (MapperMethod.ParamMap<?>) obj;
        Set<String> set = paramMap.keySet();
        // 循环遍历是否有更新字段
        String exist = set.stream().filter(e -> conform(paramMap.get(e))).findAny().orElse(null);
        if (null == exist) {
            return;
        }
        String orderId = null;
        // 遍历找出订单ID
        for (String canSend : set) {
            Object o = paramMap.get(canSend);
            orderId = getOrderId(o, paramMap);
            if (null != orderId) {
                break;
            }
        }
        // 找到订单ID 发送kafka
        if (StringUtils.isNotEmpty(orderId)) {
            System.out.println("=============================================================");
            System.out.println("=============================================================");
            System.out.println("==============================" + orderId + "==============================");
            System.out.println("=============================================================");
            System.out.println("=============================================================");
        }
    }

    /**
     * 获取订单ID , 没有返回null
     *
     * @param o        参数key
     * @param paramMap 参数
     * @return orderId   or   null
     */
    private String getOrderId(Object o, MapperMethod.ParamMap<?> paramMap) {
        Object id = null;
        if (o instanceof User) {
            id = ((User) o).getId();
        } else if (o instanceof UpdateWrapper) {
            id = getOrderId(((UpdateWrapper<?>) o).getEntity(), paramMap);
        } else {
            if (paramMap.containsKey(ORDER_ID_STRING)) {
                id = paramMap.get(ORDER_ID_STRING);
            }
        }
        if (null != id) {
            return id.toString();
        }
        return null;
    }

    /**
     * 检查是否有合适字段
     *
     * @param obj 参数
     * @return 是否符合
     */
    private boolean conform(Object obj) {
        if (null == obj) {
            return false;
        }
        if (obj instanceof User) {
            User oi = (User) obj;
            return checkOrderInfo(oi);
        } else if (obj instanceof UpdateWrapper) {
            Object entity = ((UpdateWrapper<?>) obj).getEntity();
            // 防止递归 死循环
            if (!(entity instanceof UpdateWrapper)) {
                return conform(entity);
            }
        }
        return null != MONITOR_COLUMN_LIST.stream().filter(e -> StringUtils.isEquals(e, obj.toString())).findAny().orElse(null);
    }

    /**
     * 检查orderInfo对象  字段较多  采用反射匹配
     *
     * @param orderInfo 订单实体
     * @return 是否符合
     */
    private boolean checkOrderInfo(User orderInfo) {
        Class<?> aClass = orderInfo.getClass();
        //得到属性
        Field field;
        for (String e : MONITOR_COLUMN_LIST) {
            try {
                field = aClass.getDeclaredField(e);
                if (null == field) {
                    continue;
                }
                //打开私有访问
                field.setAccessible(true);
                //获取属性值
                String subjectType = field.get(orderInfo).toString();
                // 说明有更新字段有值  不管是条件还是修改值  都 返回 true
                if (StringUtils.isNotEmpty(subjectType)) {
                    return true;
                }
            } catch (Exception ignored) {
            }
        }
        return false;
    }

}
