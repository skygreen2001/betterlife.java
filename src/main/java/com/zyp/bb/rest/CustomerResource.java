package com.zyp.bb.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zyp.bb.domain.Customer;
import com.zyp.bb.respository.CustomerRespository;
import com.zyp.bb.service.CustomerService;

@RestController
public class CustomerResource{

    // @Inject
    @Autowired
    private CustomerService customerService;

    // @Inject
    @Autowired
    private CustomerRespository customerRespository;

    @RequestMapping("/customers")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return new ResponseEntity<>(customerRespository.findAll(), HttpStatus.OK);
    }

    @RequestMapping( path="/register", method = RequestMethod.POST)
    public Customer register(@RequestBody Customer customer) {
        return customerService.register(customer);
    }
}
