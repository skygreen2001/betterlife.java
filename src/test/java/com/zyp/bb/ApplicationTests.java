package com.zyp.bb;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.zyp.bb.domain.Greeting;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void testVanillaService() {
        RestTemplate restTemplate = new RestTemplate();
        Greeting greet = restTemplate.getForObject("http://localhost:8080", Greeting.class);
        Assert.assertEquals("Hello World Skygreen!", greet.getMessage());

    }
}
