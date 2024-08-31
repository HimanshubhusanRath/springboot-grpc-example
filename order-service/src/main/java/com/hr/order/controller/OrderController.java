package com.hr.order.controller;

import com.hr.order.grpcclient.PaymentServiceGrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private PaymentServiceGrpcClient paymentServiceGrpcClient;

    @GetMapping
    public String getOrder() {
        return paymentServiceGrpcClient.getPayment("Order-001");
    }
}
