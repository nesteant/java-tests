package com.apps4j.springedu;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Configuration
@ComponentScan
public class PrintBeanDefinition {


    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(PrintBeanDefinition.class);
        String[] beanDefinitionNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanDefinitionNames);
        for (String beanDefinitionName : beanDefinitionNames) {
            AbstractBeanDefinition beanDefinition = (AbstractBeanDefinition) ctx.getBeanDefinition(beanDefinitionName);
            System.out.println(String.format("NAME: %-30s CLASS: %s", beanDefinitionName, beanDefinition.getBeanClassName()));
        }
    }


    @Bean(name = "BLA")
    public Object bean1() {
        return new Object();
    }

    @Bean
    public StaticClass anotherClass() {
        return new StaticClass();
    }

    @Bean
    public StaticClass anotherClass1() {
        return new StaticClass();
    }

    @Bean
    public InjectionTest injectionTest(InjectionTestInject injectionTestInject) {
        return new InjectionTest(injectionTestInject);
    }

    @Component
    static class StaticClass implements BeanNameAware {

        @Override
        public void setBeanName(String name) {
            System.out.println(name);
        }
    }
}

@Component
class SliceClass {
}