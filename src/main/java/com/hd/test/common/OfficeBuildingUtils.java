package com.hd.test.common;

import com.hd.test.entity.OfficeBuilding;

public class OfficeBuildingUtils {

    private static final String DECOLLATOR = "__";

    /**
     * 机器人的默认楼宇
     */
    public static final String DEFAULT_BUILDING_HALT = "A";

    /**
     * 是否不可以拼接
     *
     * @param officeId
     * @param building
     * @return 不能拼接 true
     */
    public static boolean isEmptyOfficeBuildingId(String officeId, String building) {
        return StringUtils.isEmpty(officeId) || StringUtils.isEmpty(building);
    }

    /**
     * 获取机构楼宇ID 防止有配有绑定楼宇信息的数据 产生 null 异常
     *
     * @param building 楼宇
     * @return
     */
    public static String getBuildingId(OfficeBuilding building) {
        return building == null ? null : building.getId();
    }

    /**
     * 获取机构楼宇ID 没有绑定楼宇给无 产生 null 异常
     *
     * @param building 楼宇
     * @return
     */
    public static String getBuildingIdNotNull(OfficeBuilding building) {
        return building == null ? "无" : building.getId();
    }

    /**
     * 最近有点神经 就想着异常 搞个没啥用的方法玩玩
     * @param building
     * @return
     */
    public static OfficeBuilding newBuilding(OfficeBuilding building) {
        return building == null ? new OfficeBuilding() : building;
    }

    /**
     * 获取机构楼宇ID
     *
     * @param officeId 机构id
     * @param halt     机器人传上来的楼宇id
     * @return
     */
    public static String getOfficeBuildingId(String officeId, String halt) {
        if (org.springframework.util.StringUtils.isEmpty(officeId)) {
            return null;
        }
        if (org.springframework.util.StringUtils.isEmpty(halt)) {
            //halt值为空时，返回默认值
            return officeId + DECOLLATOR + DEFAULT_BUILDING_HALT;
        } else {
            return officeId + DECOLLATOR + halt;
        }

    }

    /**
     * 是否为 机构+buildingId
     */
    public static boolean isOfficeBuildingId(String building) {
        return !isNotOfficeBuildingId(building);
    }

    /**
     * 是否不为 机构+buildingId
     */
    public static boolean isNotOfficeBuildingId(String building) {
        return StringUtils.isEmpty(building) || building.indexOf(DECOLLATOR) < 0 || building.length() < 32;
    }

    /**
     * buildingId to halt（机器人使用的楼宇id）
     */
    public static String buildingToHalt(String buildingId) {
        if (StringUtils.isEmpty(buildingId)) {
            //没有楼宇信息是，默认返回"A";
            return DEFAULT_BUILDING_HALT;
        }
        if (isOfficeBuildingId(buildingId)) {
            return StringUtils.endSplitTwo(buildingId, DECOLLATOR).getVal2();
        } else {
            return DEFAULT_BUILDING_HALT;
        }
    }

}
