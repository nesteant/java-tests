package com.apps4j.javatests.interviewquestions;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class AutowireTest {

    @Autowired
    private TestBean testBean;

    private Config directAccess = new Config();

    @Test
    public void assertString() {
        Assert.assertEquals(directAccess.autowiredString(), testBean.autowiredString);
    }

    @Test
    public void assertInt() {
        Assert.assertEquals(directAccess.autowiredInt(), testBean.autowiredInt);
    }

    @Test
    public void assertBoolean() {
        Assert.assertEquals(directAccess.autowiredBoolean(), testBean.autowiredBoolean);
    }

    @Test
    public void assertByte() {
        Assert.assertEquals(directAccess.autowiredByte(), testBean.autowiredByte);
    }

    @Test
    public void assertByteArray() {
        Assert.assertArrayEquals(directAccess.namedAutowiredByteArray(), (byte[]) testBean.namedAutowiredByteArray);
    }

    @Configuration
    @ComponentScan
    public static class Config {
        @Bean
        public String autowiredString() {
            return "Got string";
        }

        @Bean
        public int autowiredInt() {
            return 17;
        }

        @Bean
        public boolean autowiredBoolean() {
            return true;
        }

        @Bean
        public byte autowiredByte() {
            return 17;
        }

        @Bean
        public byte[] namedAutowiredByteArray() {
            return new byte[]{0, 1, 2, 3};
        }
    }

    @Component
    public static class TestBean {
        @Autowired
        private String autowiredString;
        @Autowired
        private int autowiredInt;

        @Autowired
        private boolean autowiredBoolean;

        @Autowired
        private byte autowiredByte;

        @Autowired
        private Object namedAutowiredByteArray;
    }
}
