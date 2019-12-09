package com.hd.test.elastic.common;

/**
 * 创建索引配置
 *
 * @author Sands
 */
public class ElasticIndexSetting {
//    /**
//     * 是否为下一个索引文件
//     */
//    private boolean next;
    /**
     * 索引名称
     */
    private String index;
    /**
     * 索引类型
     */
    private String type;
    /**
     * 字段映射类
     */
    private Class<?> mappingClass;
    /**
     * 是否使用时间索引创建，按照年月
     */
    private boolean useDateMonth;
    /**
     * 是否使用时间索引创建，按照年月日
     */
    private boolean useDateDay;

    protected ElasticIndexSetting(String index, Class<?> mappingClass, boolean useDateMonth) {
        this.index = index;
        this.mappingClass = mappingClass;
        this.useDateMonth = useDateMonth;
    }

    public String getIndex() {
        return index;
    }

    private void setIndex(String index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    private void setType(String type) {
        this.type = type;
    }

    public Class<?> getMappingClass() {
        return mappingClass;
    }

    private void setMappingClass(Class<?> mappingClass) {
        this.mappingClass = mappingClass;
    }

    public boolean isUseDateMonth() {
        return useDateMonth;
    }

    private void setUseDateMonth(boolean useDateMonth) {
        this.useDateMonth = useDateMonth;
    }

    public boolean isUseDateDay() {
        return useDateDay;
    }

    private void setUseDateDay(boolean useDateDay) {
        this.useDateDay = useDateDay;
    }

    @Override
    public String toString() {
        return "ElasticIndexSetting{" +
                "index='" + index + '\'' +
                ", type='" + type + '\'' +
                ", mappingClass=" + mappingClass +
                ", useDateMonth=" + useDateMonth +
                ", useDateDay=" + useDateDay +
                '}';
    }

    public static class Builder {
        private final ElasticIndexSetting setting;

        public Builder(String index, Class<?> mappingClass, boolean useDateMonth) {
            setting = new ElasticIndexSetting(index, mappingClass, useDateMonth);
        }

        public ElasticIndexSetting build() {
            ElasticIndexSetting setting1 = new ElasticIndexSetting(setting.getIndex(), setting.getMappingClass(), setting.isUseDateMonth());
            setting1.setType(setting.getType());
            setting1.setUseDateDay(setting.isUseDateDay());

            return setting1;
        }

        public Builder index(String index) {
            setting.setIndex(index);
            return this;
        }

        public Builder type(String type) {
            setting.setType(type);
            return this;
        }

        public Builder mappingClass(Class<?> mappingClass) {
            setting.setMappingClass(mappingClass);
            return this;
        }

        public Builder useDateMonth(boolean useDateMonth) {
            setting.setUseDateMonth(useDateMonth);
            return this;
        }

        public Builder useDateDay(boolean useDateDay) {
            setting.setUseDateDay(useDateDay);
            return this;
        }
    }

}
