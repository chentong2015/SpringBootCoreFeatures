package com.retail.experience.io;

import com.retail.experience.factory.ComponentFactory;
import com.retail.experience.model.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class Parser {

    private final Loader loader;
    private final ComponentFactory componentFactory;

    public Parser(Loader loader, ComponentFactory factory) {
        this.loader = loader;
        this.componentFactory = factory;
    }

    public List<Component> parseComponents() {
        List<Component> components = new ArrayList<>();
        for (Map<String, String> items : loader.loadResourceData()) {
            Component component = componentFactory.makeComponent(items);
            if (component != null) {
                components.add(component);
            }
        }
        return components;
    }
}
