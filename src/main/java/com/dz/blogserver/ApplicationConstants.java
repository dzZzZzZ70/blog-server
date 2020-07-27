package com.dz.blogserver;

import com.dz.blogserver.config.MyFunction;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ApplicationConstants {
    public enum Able{
        VALID("有效", "0"),
        UNVALID("无效", "1");

        private String name;
        private String index;

        Able(String name,String index) {
            this.name = name;
            this.index = index;
        }

        public String getName() {
            return name;
        }

        public String getIndex() {
            return index;
        }
    }

    public enum ResultFlag {
        SUCCESS("成功", "0"),FAILED("验证失败", "1"),ERROR("错误", "2");
        private String name;
        private String index;
        ResultFlag(String name, String index) {
            this.name = name;
            this.index = index;
        }

        public String getName() {
            return name;
        }

        public String getIndex() {
            return index;
        }
    }
}
