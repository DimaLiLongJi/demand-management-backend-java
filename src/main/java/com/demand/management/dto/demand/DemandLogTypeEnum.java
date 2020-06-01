package com.demand.management.dto.demand;

import com.baomidou.mybatisplus.annotation.EnumValue;

public enum DemandLogTypeEnum {
    CREATE("1"),
    UPDATE("2"),
    DELETE("3"),
    FINISH("4"), // 完成需求进度
    UNFINISH("5"), // 未完成需求进度
    UPDATEPROGRESSDATE("6"), // 更新进度排期
    ONUPPER("7"), // 未归档需求
    ONLOWER("8"); // 已归档需求

    @EnumValue
    private String type;
    DemandLogTypeEnum(String type) {
        this.type = type;
    };

    public String getType() {
        return this.type;
    }
}
