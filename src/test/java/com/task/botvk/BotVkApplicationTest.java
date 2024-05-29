package com.task.botvk;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;


@SpringBootTest(classes = BotVkApplication.class)
@ContextConfiguration(classes = ConfigByApplication.class, loader = AnnotationConfigContextLoader.class)
public class BotVkApplicationTest {

    @Test
    public void contextLoads() {
    }

}