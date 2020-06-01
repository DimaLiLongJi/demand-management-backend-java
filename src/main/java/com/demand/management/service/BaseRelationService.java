package com.demand.management.service;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface BaseRelationService<T> extends IService<T> {
    void updateByLeft(String left, List<String> ids);
    void removeLeft(String left);
    void addRight(String left, List<String> ids);
}
