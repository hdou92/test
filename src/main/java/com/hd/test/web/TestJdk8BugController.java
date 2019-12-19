package com.hd.test.web;

import com.hd.test.common.CollectionUtils;
import com.hd.test.common.ObjectUtils;
import com.hd.test.entity.RestResult;
import com.hd.test.manager.Jdk8TestManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Api(description = "* JDK8测试", value = "测试服务")
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestController
public class TestJdk8BugController {

    private static final Log LOGGER = LogFactory.getLog(Jdk8TestManager.class);

    @Autowired
    private Jdk8TestManager jdk8TestManager;

    @RequestMapping(value = "testJdk8", method = RequestMethod.GET)
    @ApiOperation(value = "* 测试JDK8 BUG")
    public void test() {
        LOGGER.debug("开启一个测试线程！");
        jdk8TestManager.testJdkBug();
    }

    @RequestMapping(value = "stopThread", method = RequestMethod.GET)
    @ApiOperation(value = "* 关闭一个线程")
    public Boolean stopThread() {
        LOGGER.debug("关闭线程！");
        Set<Thread> threads = Thread.getAllStackTraces().keySet();
        List<Thread> list = new ArrayList<>();
        threads.stream().forEach(e -> {
            // 排除堆栈线程
            if (e.getName().contains("Thread")) {
                if (e.isAlive()) {
                    list.add(e);
                }
            }
        });
        // 获取0位置的线程  并将其中断
        if (CollectionUtils.isNotEmpty(list)) {
            Thread thread = list.get(0);
            thread.interrupt();// 中断
            LOGGER.debug("已停止线程:" + thread.getName());
            return thread.interrupted();
        }
        return false;
    }

    @RequestMapping(value = "getThreadCount", method = RequestMethod.GET)
    @ApiOperation(value = "* 获取线程数量")
    public Integer getThreadCount() {
        LOGGER.debug("获取线程数量！");
        Set<Thread> threads = Thread.getAllStackTraces().keySet();
        List<Thread> list = new ArrayList<>();
        threads.stream().forEach(e -> {
            // 排除堆栈线程
            if (e.getName().contains("Thread")) {
                LOGGER.debug("name : " + e.getName() + " , status : " + e.getState().toString());
                if (ObjectUtils.isEquals(e.getState(), Thread.State.RUNNABLE)) {
                    list.add(e);
                }
            }
        });
        return CollectionUtils.isEmpty(list) ? 0 : list.size();
    }

    @RequestMapping(value = "getOS", method = RequestMethod.GET)
    @ApiOperation(value = "* 获取OS")
    public RestResult getOS() {
        LOGGER.debug("获取系统信息！");
        String osName = System.getProperty("os.name");
        RestResult result = new RestResult();
        result.setData(osName);
        return result;
    }
}
