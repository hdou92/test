package com.hd.test.web;

import com.hd.test.common.StringUtils;
import com.hd.test.config.RedisUtil;
import com.hd.test.entity.AutoGenerationVO;
import com.hd.test.entity.RestResult;
import com.hd.test.entity.RobotMainPosition;
import com.hd.test.entity.RobotPositionMapping;
import com.hd.test.manager.TestManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 测试一键生成
 * eps 公司功能
 */
@CrossOrigin
@RestController
public class TestGenerateController {

    private static final Log LOGGER = LogFactory.getLog(TestGenerateController.class);

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private TestManager testManager;

    @RequestMapping("/test")
    public String test() {
        testManager.testLog();
        LOGGER.debug(redisUtil.get("test"));
        return "123";
    }

    @RequestMapping("/addMainPosition")
    public RestResult testMainPosition(AutoGenerationVO vo) {
        LOGGER.info("auto generation main position!" + vo.toString());
        return testManager.addAllMainPosition(vo);
    }

    @RequestMapping("/addMappingPosition")
    public RestResult testMappingPosition(AutoGenerationVO vo) {
        LOGGER.info("auto generation mapping position!" + vo.toString());
        return testManager.addAllPositionMapper(vo);
    }

    @RequestMapping("/getMain")
    public List<RobotMainPosition> testGetMain(String officeId) {
        LOGGER.info("get main position! 机构ID：" + officeId);
        if (StringUtils.isNotEmpty(officeId)) {
            return testManager.getMain(officeId);
        }
        return testManager.getMainAll();
    }

    @RequestMapping("/getMapping")
    public List<RobotPositionMapping> testGetMapping(String officeId) {
        LOGGER.info("get mapping position! 机构ID：" + officeId);
        if (StringUtils.isNotEmpty(officeId)) {
            return testManager.getMapping(officeId);
        }
        return testManager.getMappingAll();
    }

    @RequestMapping("/testRemove")
    public String testRemove() {
        LOGGER.info("delete all generation position!");
        if (testManager.removeAllData()) {
            return "success";
        }
        return "failed";
    }

    @RequestMapping("/addPosition")
    public String addPosition() {
        LOGGER.info("add position!");
        testManager.addPosition();
        return "success";
    }
}
