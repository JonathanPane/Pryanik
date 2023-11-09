package com.example.pryanik;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class BeanContext {
    private static final Map<String, ObjectBeanContainer> context;
    private static final List<String> info_about_bean;

    static {
        context = new HashMap<>();
        info_about_bean = new LinkedList<>();
        context.put("bean_info", new ObjectBeanContainer(info_about_bean, true));
        try {
            Class.forName("com.example.pryanik.project.library.Logger");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void register_bean(String name, Object bean) {
        register_bean(name, bean, false);
    }

    public static void register_system_bean(String name, Object bean) {
        register_bean(name, bean, true);
    }

    private static void register_bean(String name, Object bean, boolean for_system) {
        if (contains_bean(name)) {
            throw new BeanDuplicateException(name);
        }
        if(!for_system)
            info_about_bean.add(String.format("%10s: %30s; %50s", "register", name, bean.getClass().getCanonicalName()));
        context.put(name, new ObjectBeanContainer(bean, for_system));
    }

    public static <T> T get_bean(String name) {
        if (!contains_bean(name)) {
            throw new BeanNotFoundException(name);
        }
        if(!context.get(name).for_system)
            info_about_bean.add(String.format("%10s: %30s; %50s", "get", name, context.get(name).value.getClass().getCanonicalName()));

        return (T) context.get(name).value;
    }

    public static void remove_bean(String name) {
        if (!contains_bean(name)) {
            throw new BeanNotFoundException(name);
        }
        if(!context.get(name).for_system)
            info_about_bean.add(String.format("%10s: %30s; %50s", "remove", name, context.get(name).value.getClass().getCanonicalName()));
        context.remove(name);
    }

    public static <T> T get_and_remove_bean(String name) {
        if(!contains_bean(name))
            throw new BeanNotFoundException(name);
        T res = get_bean(name);
        remove_bean(name);
        return res;
    }

    public static void set_value_in_bean(String name, Object new_value) {
        if(!contains_bean(name)) {
            throw new BeanNotFoundException(name);
        }
        if(!context.get(name).for_system)
            info_about_bean.add(String.format("%10s: %30s;", "changing value", name));
        boolean for_system = context.get(name).for_system;
        context.remove(name);
        context.put(name, new ObjectBeanContainer(new_value, for_system));
    }

    private static class ObjectBeanContainer{
        private final Object value;
        private final boolean for_system;

        public ObjectBeanContainer(Object value, boolean for_system) {
            this.value = value;
            this.for_system = for_system;
        }
    }

    public static boolean contains_bean(String name) {
        return context.containsKey(name);
    }

    private static class BeanNotFoundException extends RuntimeException {
        public BeanNotFoundException(String message) {
            super("Bean with name: " + message + " not found");
        }
    }

    private static class BeanDuplicateException extends RuntimeException {
        public BeanDuplicateException(String message) {
            super("Bean with name: " + message + " cannot be register, because bean with this name already registered");
        }
    }
}
