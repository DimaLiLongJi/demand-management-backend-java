package com.demand.management.dto.demand;

import com.baomidou.mybatisplus.annotation.EnumValue;

public enum DemandLogPropertyEnum {
    progress("progress"),
    demand("demand"),
    name("name"),
    manDay("manDay"),
    detail("detail"),
    comment("comment"),
    url("url"),
    demandType("demandType"),
    demandStatus("demandStatus"),
    proposer("proposer"),
    broker("broker"),
    developer("developer"),
    devops("devops"),
    file("file"),
    expectDate("expectDate"),
    scheduleStartDate("scheduleStartDate"),
    scheduleEndDate("scheduleEndDate"),
    finishDate("finishDate"),
    deleteDate("deleteDate"),
    demandNode("demandNode"),
    isPending("isPending");

    @EnumValue
    private String property;
    DemandLogPropertyEnum(String property) {
        this.property = property;
    };

    public String getProperty() {
        return this.property;
    }
}
