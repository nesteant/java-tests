package com.apps4j.springedu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component("akJLAKDJALD")
public class InjectionTest {

    @Autowired
    private InjectionTestInject injection1;

    private InjectionTestInject inj;


//    public InjectionTest() {
//        String x = "";
//    }

    public InjectionTest(InjectionTestInject inj) {
        this.inj = inj;
//        System.out.println(injection1);
        System.out.println(inj);
    }


    @PostConstruct
    public void ini() {
        System.out.println(injection1);
    }


}
