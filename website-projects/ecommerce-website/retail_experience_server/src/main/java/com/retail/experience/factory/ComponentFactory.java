package com.retail.experience.factory;

import com.retail.experience.model.Component;

import java.util.Map;

public interface ComponentFactory {

    Component makeComponent(Map<String, String> listItems);
}
