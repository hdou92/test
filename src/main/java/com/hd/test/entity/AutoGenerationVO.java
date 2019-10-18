package com.hd.test.entity;

/**
 * 一键生成VO 类
 */
public class AutoGenerationVO {

    /**
     * 机构
     */
    private String officeId;

    /**
     * 楼宇
     * 通过楼宇匹配生成 由于多楼宇可能会有名称一样的位置 所以需要填写楼宇参数 填写之后多楼宇情况下 已该楼宇为准
     */
    private String buildingId;

    /**
     * 名称 0
     * 全称 1
     * 通过名称 或全称 匹配生成位置数据
     */
    private String name;

    /**
     * 机器人类型
     * 已该机器人类型为准生成配置
     */
    private String robotModel;

    public AutoGenerationVO() {
    }

    public AutoGenerationVO(String officeId, String buildingId, String name, String robotModel) {
        this.officeId = officeId;
        this.buildingId = buildingId;
        this.name = name;
        this.robotModel = robotModel;
    }

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    public String getRobotModel() {
        return robotModel;
    }

    public void setRobotModel(String robotModel) {
        this.robotModel = robotModel;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AutoGenerationVO{" +
                "officeId='" + officeId + '\'' +
                ", buildingId='" + buildingId + '\'' +
                ", name='" + name + '\'' +
                ", robotModel='" + robotModel + '\'' +
                '}';
    }
}
