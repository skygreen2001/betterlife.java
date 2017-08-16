package com.zyp.bb;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.zyp.bb.domain.Greeting;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    @Value("${test.server.url}")
    private String serverUrl;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testVanillaService() {
        RestTemplate restTemplate = new RestTemplate();
        Greeting greet = restTemplate.getForObject(serverUrl, Greeting.class);
        Assert.assertEquals("Hello World Skygreen!", greet.getMessage());

    }
}
