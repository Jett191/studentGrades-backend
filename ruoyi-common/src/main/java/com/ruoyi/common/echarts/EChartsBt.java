package com.ruoyi.common.echarts;

import java.io.Serializable;

/**
 * EChart饼图实体类
 */
public class EChartsBt implements Serializable {
    private String name;
    private String value;

    public EChartsBt() {
    }

    public EChartsBt(String name, String value) {
        this.name = name;
        this.value = value;
    }

    /**
     * 获取
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * 设置
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取
     * @return value
     */
    public String getValue() {
        return value;
    }

    /**
     * 设置
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }

    public String toString() {
        return "EchartVo{name = " + name + ", value = " + value + "}";
    }
}
