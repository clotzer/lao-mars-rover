package com.clotzer.marsrover.ws;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LaoMarsRoverApplication.class)
@WebAppConfiguration
public abstract class AbstractTest {
	public static final Logger log = LoggerFactory.getLogger(AbstractTest.class);
}
