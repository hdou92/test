package com.hd.test.manager;

import com.hd.test.common.CollectionUtils;
import com.hd.test.common.OfficeBuildingUtils;
import com.hd.test.common.StringUtils;
import com.hd.test.consts.TestConsts;
import com.hd.test.entity.*;
import com.hd.test.service.RobotMainPositionService;
import com.hd.test.service.RobotPositionMappingService;
import com.hd.test.service.RobotPositionService;
import com.hd.test.web.TestController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class TestManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestManager.class);

    @Autowired
    private RobotPositionService robotPositionService;

    @Autowired
    private RobotMainPositionService robotMainPositionService;

    @Autowired
    private RobotPositionMappingService robotPositionMappingService;

//    /**
//     * 存在配置
//     *
//     * @param office     机构
//     * @param robotModel 机器人类型
//     * @param guId       位置guid
//     * @return true
//     */
//    private boolean isExist(Office office, String robotModel, String guId) {
//        RobotMainPosition mainPosition = new RobotMainPosition();
//        mainPosition.setOffice(office);
//        mainPosition.setRobotModel(robotModel);
//        mainPosition.setPositionId(guId);
//        return robotMainPositionService.getByOfficeIdAndPositionId(mainPosition) != null;
//    }
//
//    /**
//     * 一键添加机构下所有位置主表
//     * 首先根据机构查询所有的位置，然后将指定 机器人型号 下的所有位置添加表中，并且把其他机器人型号的，位置名称与选定型号不一样的位置也自动添加到表中。
//     *
//     * @param vo vo.officeId 机构   需要一键生成的机构
//     *           vo.buildingId 楼宇   为准的机器人类型所对应其他类型的楼宇
//     *           vo.robotModel 机器人类型      已该机器人类型为准生成配置
//     *           vo.name 0 = 名称; 1 = 全称
//     */
//    public RestResult<Integer> addAllMainPosition(AutoGenerationVO vo) {
//        RestResult result = new RestResult();
//        // 使用名称 或者全称 1 为全称。 防止没有传参则默认使用 0 也就是名称
//        boolean isFullName = StringUtils.isEquals(vo.getName(), "1");
//        // 是否传楼宇ID  如果没传则不考虑楼宇
//        boolean isBuilding = StringUtils.isEmpty(vo.getBuildingId());
//        Integer count = 0;
//        // 获取所有位置
//        List<TestPosition> positions = robotPositionService.getAllListByOfficeId(vo.getOfficeId());
//        if (CollectionUtils.isEmpty(positions)) {
//            result.setData(count);
//            return result;
//        }
//        // 获取所有参数机器人类型的位置
//        List<TestPosition> collectByRobotModel = positions.stream().filter(a -> StringUtils.isEquals(a.getRobotModel(), vo.getRobotModel())).collect(Collectors.toList());
//        RobotMainPosition rmp = null;
//        if (CollectionUtils.isNotEmpty(collectByRobotModel)) {
//            // 添加所有参数类型位置总表
//            for (TestPosition p : collectByRobotModel) {
//                if (isExist(p.getOffice(), p.getRobotModel(), p.getGuId())) {
//                    continue;
//                }
//                rmp = new RobotMainPosition();
//                rmp.setRobotModel(p.getRobotModel());
//                rmp.setOffice(p.getOffice());
//                rmp.setType(p.getType());
//                rmp.setPositionId(p.getGuId());
//                rmp.setFloor(p.getFloor());
//                rmp.setFullName(p.getFullName());
//                rmp.setName(p.getName());
//                count++;
//                robotMainPositionService.save(rmp);
//            }
//        }
//        // 将 list 转换为 map 方便验证位置全名是否已经生成配置
//        Map<String, TestPosition> maps = CollectionUtils.toMap(collectByRobotModel,
//                a -> isFullName ? a.getFullName() : a.getName(), a -> a, new HashMap<>());
//        // 遍历所有位置
//        for (TestPosition p : positions) {
//            // 如果机器人类型和参数类型一样 则跳过  因为参数类型的位置在上面已经添加了
//            if (StringUtils.isEquals(vo.getRobotModel(), p.getRobotModel())) {
//                continue;
//            }
//            // 如果包含全称或名称则说明已经生成过配置 且 楼宇 = 传参的楼宇 说明该位置对应有总表配置了 则跳过
//            if (maps.containsKey(isFullName ? p.getFullName() : p.getName()) && (isBuilding || StringUtils.isEquals(OfficeBuildingUtils.buildingToHalt(vo.getBuildingId()), OfficeBuildingUtils.buildingToHalt(p.getBuilding().getId())))) {
//                continue;
//            }
//            // 如果存在数据
//            if (isExist(p.getOffice(), p.getRobotModel(), p.getGuId())) {
//                continue;
//            }
//            rmp = new RobotMainPosition();
//            rmp.setRobotModel(p.getRobotModel());
//            rmp.setOffice(p.getOffice());
//            rmp.setType(p.getType());
//            rmp.setPositionId(p.getGuId());
//            rmp.setFloor(p.getFloor());
//            rmp.setFullName(p.getFullName());
//            rmp.setName(p.getName());
//            count++;
//            robotMainPositionService.save(rmp);
//        }
//        result.setData(count);
//        return result;
//    }
//
//    /**
//     * 一键添加机构下所有位置映射
//     * 即首先根据机构查询所有的机器人位置，然后筛选出所有 基准的机器人型号 位置，再根据名称查询出与其他机器人型号的名称一样的位置，对于有名称一样的位置，就插入到位置映射表中。
//     *
//     * @param vo vo.officeId     机构
//     *           vo.robotModel   基准的机器人类型
//     * @return RestResult   添加数量
//     */
//    public RestResult<Integer> addAllPositionMapper(AutoGenerationVO vo) {
//        RestResult result = new RestResult();
//        Integer count = 0;
//        // 使用名称 或者全称 1 为全称。 防止没有传参则默认使用 0 也就是名称
//        boolean isFullName = StringUtils.isEquals(vo.getName(), "1");
//        // 是否传楼宇ID  如果没传则不考虑楼宇
//        boolean isBuilding = StringUtils.isEmpty(vo.getBuildingId());
//        // 获取所有位置
//        List<TestPosition> positions = robotPositionService.getAllListByOfficeId(vo.getOfficeId());
//        if (CollectionUtils.isEmpty(positions)) {
//            result.setData(count);
//            return result;
//        }
//        // 获取所有参数机器人类型的位置
//        List<TestPosition> collectByRobotModel = positions.stream().filter(a -> StringUtils.isEquals(a.getRobotModel(), vo.getRobotModel())).collect(Collectors.toList());
//        if (CollectionUtils.isEmpty(collectByRobotModel)) {
//            result.setData(count);
//            return result;
//        }
//        // 获取所有非参数机器人类型的位置
//        List<TestPosition> collect = positions.stream().filter(a -> StringUtils.isNotEqualsAndNotEmpty(a.getRobotModel(), vo.getRobotModel())).collect(Collectors.toList());
//        if (CollectionUtils.isEmpty(collect)) {
//            result.setData(count);
//            return result;
//        }
//        Map<String, TestPosition> maps = CollectionUtils.toMap(collect,
//                a -> isFullName ? a.getFullName() : a.getName(), a -> a, new HashMap<>());
//
//        RobotPositionMapping rpm = null;
//        for (TestPosition p : collectByRobotModel) {
//            String name = isFullName ? p.getFullName() : p.getName();
//            // 不包含说明其他类型没有同名的位置
//            if (!maps.containsKey(name)) {
//                continue;
//            }
//            // 防止不同楼宇名称一样所以效验楼宇
//            if (StringUtils.isNotEqualsAndNotEmpty(OfficeBuildingUtils.buildingToHalt(maps.get(name).getBuilding().getId()), OfficeBuildingUtils.buildingToHalt(vo.getBuildingId()))) {
//                continue;
//            }
//            TestPosition robotPosition = maps.get(p.getFullName());
//            rpm = new RobotPositionMapping();
//            rpm.setOffice(p.getOffice());
//            rpm.setDatumRobotPositionId(p.getGuId());
//            rpm.setMappingRobotPositionId(robotPosition.getGuId());
//            rpm.setMappingRobotModel(robotPosition.getRobotModel());
//            rpm.setDatumRobotModel(p.getRobotModel());
//            RobotPositionMapping entity = robotPositionMappingService.getByOfficeIdAndPosition(rpm);
//            if (entity == null) {
//                count++;
//                robotPositionMappingService.save(rpm);
//            }
//        }
//        result.setData(count);
//        return result;
//    }

    /**
     * 一键添加机构下所有位置主表
     * 首先根据机构查询所有的位置，然后将指定 机器人型号 下的所有位置添加表中，并且把其他机器人型号的，位置名称与选定型号不一样的位置也自动添加到表中。
     *
     * @param vo vo.officeId 机构   需要一键生成的机构
     *           vo.buildingId 楼宇   为准类型楼宇  没什么用
     *           vo.robotModel 机器人类型      已该机器人类型为准生成配置
     *           vo.name 0 = 名称; 1 = 全称
     */
    public RestResult<Integer> addAllMainPosition(@RequestBody AutoGenerationVO vo) {
        LOGGER.debug("add main position params:" + vo.toString());
        RestResult result = new RestResult();
        // 使用名称 或者全称 1 为全称。 防止没有传参则默认使用 0 也就是名称
        boolean isFullName = StringUtils.isEquals(vo.getName(), "1");
        Integer count = 0;
        // 获取所有位置
        List<TestPosition> positions = robotPositionService.getAllListByOfficeId(vo.getOfficeId());
        LOGGER.debug("add main positions length : " + positions.size());
        if (CollectionUtils.isEmpty(positions)) {
            result.setData(count);
            return result;
        }
        // 获取所有参数机器人类型的位置
        List<TestPosition> collectByRobotModel = positions.stream().filter(a -> StringUtils.isEquals(a.getRobotModel(), vo.getRobotModel())).collect(Collectors.toList());
        List<TestPosition> collectByRobotModel1 = transitionList(collectByRobotModel, isFullName, true);
        // 将 list 转换为 map 方便验证位置全名是否已经生成配置
        Map<String, TestPosition> maps = CollectionUtils.toMap(collectByRobotModel1,
                a -> isFullName ? a.getFullName() : a.getName(), a -> a, new HashMap<>());
        // 遍历所有位置
        for (TestPosition p : positions) {
            // 如果机器人类型和参数类型一样 则跳过  因为参数类型的位置在上面已经添加了
            if (StringUtils.isEquals(vo.getRobotModel(), p.getRobotModel())) {
                continue;
            }
            // 获取key
            String name = isFullName ? p.getFullName() : p.getName();
            // 如果包含全称或名称 且 楼宇相等 说明该位置对应有总表配置了 则跳过
            if (maps.containsKey(name) || maps.containsKey(getNameString(name, OfficeBuildingUtils.getBuildingId(p.getBuilding())))) {
                TestPosition rp = maps.get(name);
                if (rp == null) {
                    rp = maps.get(getNameString(name, OfficeBuildingUtils.getBuildingId(p.getBuilding())));
                }
                // 楼宇不一样则可以生成配置
                if (StringUtils.isEquals(OfficeBuildingUtils.buildingToHalt(OfficeBuildingUtils.getBuildingId(rp.getBuilding())), OfficeBuildingUtils.buildingToHalt(OfficeBuildingUtils.getBuildingId(p.getBuilding())))) {
                    continue;
                }
            }
            collectByRobotModel.add(p);
        }
        RobotMainPosition rmp = null;
        if (CollectionUtils.isNotEmpty(collectByRobotModel)) {
            collectByRobotModel = transitionList(collectByRobotModel, isFullName, true);
            // 添加所有参数类型位置总表
            for (TestPosition p : collectByRobotModel) {
                if (isExist(p.getOffice(), p.getRobotModel(), p.getGuId())) {
                    continue;
                }
                rmp = new RobotMainPosition();
                rmp.setRobotModel(p.getRobotModel());
                rmp.setBuilding(OfficeBuildingUtils.newBuilding(p.getBuilding()));
                rmp.setOffice(p.getOffice());
                rmp.setType(p.getType());
                rmp.setPositionId(p.getGuId());
                rmp.setFloor(p.getFloor());
                rmp.setFullName(transitionString(p.getFullName(), OfficeBuildingUtils.getBuildingIdNotNull(p.getBuilding()))); // 如果是根据全名匹配 , 且名称有一致 则将名称 改回来
                rmp.setName(p.getName()); // 如果是根据名称匹配 , 且名称有一致 则无所谓
                count++;
                robotMainPositionService.save(rmp);
            }
        }
        result.setData(count);
        return result;
    }

    /**
     * 去除拼接的楼宇
     *
     * @param value
     * @param buildingId
     * @return
     */
    private String transitionString(String value, String buildingId) {
        return value.replace("(" + OfficeBuildingUtils.buildingToHalt(buildingId) + ")", "");
    }

    /**
     * 拼接楼宇使名称唯一
     *
     * @param value
     * @param buildingId
     * @return
     */
    private String getNameString(String value, String buildingId) {
        return value + "(" + OfficeBuildingUtils.buildingToHalt(buildingId) + ")";
    }

    /**
     * 将集合中重复的 name 值带上楼宇ID
     *
     * @param list           要转换的集合
     * @param isFullName     匹配条件 名称/全称
     * @param isMainPosition 位置总表的位置名称不能一致 , 位置映射可以一致
     * @return 转换厚的集合
     */
    private List<TestPosition> transitionList(List<TestPosition> list, boolean isFullName, boolean isMainPosition) {
        Map<String, TestPosition> map = new HashMap<>();
        for (TestPosition p : list) {
            for (TestPosition i : list) {
                if (!map.containsKey(i.getId()) && StringUtils.isEquals(p.getName(), i.getName()) && StringUtils.isNotEqualsAndNotEmpty(p.getId(), i.getId())) {
                    map.put(p.getId(), p);
                    map.put(i.getId(), i);
                }
            }
        }
        for (TestPosition p : list) {
            if (map.containsKey(p.getId())) {
                // 以全名为匹配条件 如果有全名一样的位置 则将全名改为 全名 + 楼宇
                String buildingId = OfficeBuildingUtils.getBuildingIdNotNull(p.getBuilding());
                if (isFullName) {
                    p.setFullName(getNameString(p.getFullName(), buildingId));
                    // 如果是位置总表 则将名称修改为 名称 + 楼宇 方便分辨位置
                    if (isMainPosition) {
                        p.setName(getNameString(p.getName(), buildingId));
                    }
                } else {
                    p.setName(getNameString(p.getName(), buildingId));
                }
            }
        }
        return list;
    }

    /**
     * 存在配置
     *
     * @param office     机构
     * @param robotModel 机器人类型
     * @param guId       位置guid
     * @return true
     */
    private boolean isExist(Office office, String robotModel, String guId) {
        RobotMainPosition mainPosition = new RobotMainPosition();
        mainPosition.setOffice(office);
        mainPosition.setRobotModel(robotModel);
        mainPosition.setPositionId(guId);
        return robotMainPositionService.getByOfficeIdAndPositionId(mainPosition) != null;
    }

    /**
     * 一键添加机构下所有位置映射
     * 即首先根据机构查询所有的机器人位置，然后筛选出所有 基准的机器人型号 位置，再根据名称查询出与其他机器人型号的名称一样的位置，对于有名称一样的位置，就插入到位置映射表中。
     *
     * @param vo vo.officeId     机构
     *           vo.robotModel   基准的机器人类型
     * @return RestResult   添加数量
     */
    public RestResult<Integer> addAllPositionMapper(@RequestBody AutoGenerationVO vo) {
        RestResult result = new RestResult();
        Integer count = 0;
        // 使用名称 或者全称 1 为全称。 防止没有传参则默认使用 0 也就是名称
        boolean isFullName = StringUtils.isEquals(vo.getName(), "1");
        // 获取所有位置
        List<TestPosition> positions = robotPositionService.getAllListByOfficeId(vo.getOfficeId());
        if (CollectionUtils.isEmpty(positions)) {
            result.setData(count);
            return result;
        }
        // 获取所有参数机器人类型的位置
        List<TestPosition> collectByRobotModel = positions.stream().filter(a -> StringUtils.isEquals(a.getRobotModel(), vo.getRobotModel())
                && StringUtils.isEquals(OfficeBuildingUtils.buildingToHalt(OfficeBuildingUtils.getBuildingId(a.getBuilding())), OfficeBuildingUtils.buildingToHalt(vo.getBuildingId()))).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(collectByRobotModel)) {
            result.setData(count);
            return result;
        }
        // 获取所有非参数机器人类型的位置
        List<TestPosition> collect = positions.stream().filter(a -> StringUtils.isNotEqualsAndNotEmpty(a.getRobotModel(), vo.getRobotModel())
                && StringUtils.isEquals(OfficeBuildingUtils.buildingToHalt(OfficeBuildingUtils.getBuildingId(a.getBuilding())), OfficeBuildingUtils.buildingToHalt(vo.getBuildingId()))).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(collect)) {
            result.setData(count);
            return result;
        }
        Map<String, TestPosition> maps = CollectionUtils.toMap(transitionList(collect, isFullName, false),
                a -> isFullName ? a.getFullName() : a.getName(), a -> a, new HashMap<>());

        RobotPositionMapping rpm = null;
        for (TestPosition p : collectByRobotModel) {
            String name = isFullName ? p.getFullName() : p.getName();
            // 不包含说明其他类型没有同名的位置
            if (!maps.containsKey(name) && !maps.containsKey(getNameString(name, OfficeBuildingUtils.getBuildingIdNotNull(p.getBuilding())))) {
                continue;
            }
            TestPosition rp = maps.get(name);
            if (rp == null) {
                rp = maps.get(getNameString(name, OfficeBuildingUtils.getBuildingIdNotNull(p.getBuilding())));
            }
            rpm = new RobotPositionMapping();
            rpm.setOffice(p.getOffice());
            rpm.setDatumRobotPositionId(rp.getGuId());
            rpm.setMappingRobotPositionId(p.getGuId());
            rpm.setMappingRobotModel(rp.getRobotModel());
            rpm.setDatumRobotModel(p.getRobotModel());
            RobotPositionMapping entity = robotPositionMappingService.getByOfficeIdAndPosition(rpm);
            if (entity == null) {
                count++;
                robotPositionMappingService.save(rpm);
            }
        }
        result.setData(count);
        return result;
    }

    public boolean removeAllData() {
        return robotMainPositionService.removeAll() && robotPositionMappingService.removeAll();
    }

    public List<RobotMainPosition> getMainAll() {
        return robotMainPositionService.getAll(TestConsts.MAIN_CLASS);
    }

    public List<RobotPositionMapping> getMappingAll() {
        return robotPositionMappingService.getAll(TestConsts.MAPPING_CLASS);
    }

    public List<RobotMainPosition> getMain(String officeId) {
        return getMainAll().stream().filter(a -> StringUtils.isEquals(officeId, a.getOffice().getId())).collect(Collectors.toList());
    }

    public List<RobotPositionMapping> getMapping(String officeId) {
        return getMappingAll().stream().filter(a -> StringUtils.isEquals(officeId, a.getOffice().getId())).collect(Collectors.toList());
    }

    public void addPosition() {
        if (CollectionUtils.isEmpty(robotPositionService.getData())) {
            robotPositionService.addPosition();
        }
    }

    private static final Log log = LogFactory.getLog(TestController.class);
    private static final Logger log1 = LoggerFactory.getLogger(TestController.class);

    public void testLog() {
        // 测试 logging 日志

        log.debug("这是org.apache.commons.logging一条debug日志！");
        log.info("这是org.apache.commons.logging一条info日志！");
        log.warn("这是org.apache.commons.logging一条warn日志！");
        log.error("这是org.apache.commons.logging一条error日志！");
        //测试 slf4j 日志

        log1.debug("这是org.slf4j一条debug日志！");
        log1.info("这是org.slf4j一条info日志！");
        log1.warn("这是org.slf4j一条warn日志！");
        log1.error("这是org.slf4j一条error日志！");
//        List<TestPosition> list = getList();
//        Map<String, TestPosition> map = new HashMap<>();
//        for (TestPosition p : list) {
//            for (TestPosition i : list) {
//                if (!map.containsKey(i.getId()) && StringUtils.isEquals(p.getName(), i.getName()) && StringUtils.isNotEqualsAndNotEmpty(p.getId(), i.getId())) {
//                    map.put(p.getId(), p);
//                    map.put(i.getId(), i);
//                }
//            }
//        }
//        for (TestPosition p : list) {
//            if (map.containsKey(p.getId())) {
//                p.setName(p.getName() + "_" + p.getFloor());
//            }
//            System.out.println(p.getName());
//        }
    }

    private static List<TestPosition> getList() {
        List<TestPosition> list = new ArrayList<>();
        TestPosition p = new TestPosition();
        p.setName("哈哈");
        p.setFloor("A");
        TestPosition p1 = new TestPosition();
        p1.setName("哈哈");
        p1.setFloor("B");
        TestPosition p2 = new TestPosition();
        p2.setName("哈哈");
        p2.setFloor("C");
        TestPosition p3 = new TestPosition();
        p3.setName("嘎嘎");
        p3.setFloor("A");
        TestPosition p4 = new TestPosition();
        p4.setName("呵呵");
        p4.setFloor("A");
        TestPosition p5 = new TestPosition();
        p5.setName("呵呵");
        p5.setFloor("B");
        TestPosition p6 = new TestPosition();
        p6.setName("嘻嘻");
        p6.setFloor("A");
        list.add(p);
        list.add(p1);
        list.add(p2);
        list.add(p3);
        list.add(p4);
        list.add(p5);
        list.add(p6);
        return list;
    }

}
