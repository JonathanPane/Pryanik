package com.example.pryanik;

import java.util.HashMap;
import java.util.Map;



public class BeanContext {
    private static Map<String, Object> context;
    static{
        context = new HashMap<>();
    }
   public static void register_bean(String name, Object bean){
        context.put(name, bean);
    }
   public static <T> T get_bean(String name){
        return (T) context.get(name);
    }

    public static void remove_bean(String name){
        context.remove(name);
    }
    public static <T> T get_and_remove_bean(String name){
        T res = get_bean(name);
        remove_bean(name);
        return res;
    }
    public static boolean contains_bean(String name){
        return context.containsKey(name);
    }
}
