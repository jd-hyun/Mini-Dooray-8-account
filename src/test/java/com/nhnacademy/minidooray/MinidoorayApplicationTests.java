package com.nhnacademy.minidooray;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
class MinidoorayApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void testMain() {
        MinidoorayApplication.main(new String[] {});

        assertNotNull(applicationContext, "The application context should have been loaded.");
    }

}
