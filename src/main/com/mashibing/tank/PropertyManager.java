package com.mashibing.tank;

import java.io.IOException;
import java.util.Properties;

public class PropertyManager {
    static Properties props = new Properties();

    // static block is executed when PropertyManager class is loaded to memory
    static {
        try {
            props.load(PropertyManager.class.getClassLoader().getResourceAsStream("config"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object get(String key) {
        if(props == null)
            return null;
        return props.get(key);
    }

    public static int getInt(String key) {
        return Integer.parseInt((String) props.get(key));
    }

    public static String getString(String key) {
        return (String) props.get(key);
    }
}
