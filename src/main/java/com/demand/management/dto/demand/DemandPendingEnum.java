package com.demand.management.dto.demand;

import com.baomidou.mybatisplus.annotation.EnumValue;

public enum DemandPendingEnum {
    notPending("1"), // 已审核
    isPending("2");  // 待审核

    @EnumValue
    private String type;
    DemandPendingEnum(String type) { this.type = type; };

    public String getType() {
        return this.type;
    }
}
