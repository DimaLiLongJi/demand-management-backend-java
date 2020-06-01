package com.demand.management.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Component
public class MybatisRelatedFieldsMetaHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        // 为 createDate 和 updateDate自动创建
        if (metaObject.hasGetter("createDate")) {
            this.setFieldValByName("createDate", new Date(), metaObject);
        }
        if (metaObject.hasGetter("updateDate")) {
            this.setFieldValByName("updateDate", new Date(), metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if (metaObject.hasGetter("updateDate")) {
            this.setFieldValByName("updateDate", new Date(), metaObject);
        }
    }
}
