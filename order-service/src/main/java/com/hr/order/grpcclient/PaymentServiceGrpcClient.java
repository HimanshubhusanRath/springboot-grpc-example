package com.hr.order.grpcclient;

import com.hr.paymentsvc.PaymentRequest;
import com.hr.paymentsvc.PaymentResponse;
import com.hr.paymentsvc.PaymentServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceGrpcClient {

    @GrpcClient("payment-service")
    private PaymentServiceGrpc.PaymentServiceBlockingStub paymentServiceBlockingStub;

    public String getPayment(String name) {
        final PaymentRequest request = PaymentRequest.newBuilder().setName(name).build();
        final PaymentResponse response = paymentServiceBlockingStub.getPayment(request);
        return response.getMessage();
    }
}
