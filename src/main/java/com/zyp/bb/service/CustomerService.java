package com.zyp.bb.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.zyp.bb.config.AppConfig;
import com.zyp.bb.domain.Customer;
import com.zyp.bb.domain.Greeting;
import com.zyp.bb.message.amqp.Sender;
import com.zyp.bb.respository.CustomerRespository;

@Component
@Lazy
public class CustomerService {
    private final Logger logger = LoggerFactory.getLogger(CustomerService.class);
    // @Inject
    @Autowired
    CustomerRespository customerRespository;
    // @Inject
    @Autowired
    Sender sender;

    private Map<String, String> userMessages = new HashMap<String, String>();

    @Autowired
    private SimpMessagingTemplate template;

    /**
     * [spring websocket 基于编码的方式手动进行推送]
     * (http://www.voidcn.com/blog/yingxiake/article/p-5789769.html)
     *
     * @throws Exception
     */
    @Scheduled(fixedRate = 8000)
    @Lazy(false)
    public void greetingGood() throws Exception {
        // public Greeting greetingGood() throws Exception {
        // Thread.sleep(1000); // simulated delay
        double d = Math.random();
        int i = (int) (d * 1000000);
        logger.debug("Hello, Every One " + i + "!");
        template.convertAndSend("/topic/greetings", new Greeting("Hello, Every One " + i + "!"));
        sender.send("Hello this is rabbit Messaging" + i + " for Betterlife!!!");

        WebApplicationContext webApplicationContext = AppConfig.getCurrentWebApplicationContext();
        if (webApplicationContext != null) {
            ServletContext sc = webApplicationContext.getServletContext();

            @SuppressWarnings("unchecked")
            Map<String, String> users = sc.getAttribute("users") == null ? null
                    : (Map<String, String>) sc.getAttribute("users");// 获取放在servletContext中的用户信息

            // 获取放在servletContext中的用户信息
            if (users != null) {
                Set<String> set = users.keySet();
                Iterator<String> iterator = set.iterator();
                String msg;
                while (iterator.hasNext()) { // 遍历根据用户进行推送
                    String key = iterator.next();
                    d = Math.random();
                    i = (int) (d * 1000000);
                    msg = "empty";
                    if (userMessages.keySet().contains(key)) {
                        msg = userMessages.get(key);
                    }
                    template.convertAndSendToUser(key, "/queue/come", new Greeting("Hello, " + msg + i + "!"));
                }
            }
        }
    }

    public void setName(String userID, String msg) {
        userMessages.put(userID, msg);
    }

    public Customer register(Customer customer) {
        Optional<Customer> existingCustomer = customerRespository.findByName(customer.getName());
        if (existingCustomer.isPresent()) {
            throw new RuntimeException("is already exists");
        } else {
            customerRespository.save(customer);
            sender.send(customer.getEmail());
        }
        return customer;
    }
}