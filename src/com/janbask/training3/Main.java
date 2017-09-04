package com.janbask.training3;

//spring imports
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        BasicSpringSample obj = (BasicSpringSample) context.getBean("basicSpringExample");
        obj.getMessage();
    }
}
