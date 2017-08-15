package com.zyp.bb;

import org.h2.server.web.WebServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.zyp.bb.domain.Customer;
import com.zyp.bb.message.amqp.Sender;
import com.zyp.bb.respository.CustomerRespository;

/**
 *
 * [Api Reference](http://localhost:8080/swagger-ui.html)
 *
 * [H2 Management Console](http://localhost:8080/h2-console/)
 *
 * @author skygreen
 *
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

    // @Inject
    @Autowired
    Sender sender;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        sender.send("Hello this is rabbit Messaging..!!!");
    }

    @Bean
    CommandLineRunner init(CustomerRespository customerRepository) {
        return (evt) -> {
            customerRepository.save(new Customer("Adam", "adam@boot.com"));
            customerRepository.save(new Customer("John", "john@boot.com"));
            customerRepository.save(new Customer("Smith", "smith@boot.com"));
            customerRepository.save(new Customer("Edgar", "edgar@boot.com"));
            customerRepository.save(new Customer("Martin", "martin@boot.com"));
            customerRepository.save(new Customer("Tom", "tom@boot.com"));
            customerRepository.save(new Customer("Sean", "sean@boot.com"));
        };
    }

    @Bean
    public ServletRegistrationBean h2servletRegistration() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new WebServlet());
        registration.addUrlMappings("/h2-console/*");
        return registration;
    }
}