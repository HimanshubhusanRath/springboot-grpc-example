package com.hr.payment.service;

import com.hr.paymentsvc.PaymentRequest;
import com.hr.paymentsvc.PaymentResponse;
import com.hr.paymentsvc.PaymentServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class PaymentServiceImpl extends PaymentServiceGrpc.PaymentServiceImplBase {

    @Override
    public void getPayment(PaymentRequest request, StreamObserver<PaymentResponse> responseObserver) {
        final String message = "Payment is confirmed";
        PaymentResponse response = PaymentResponse.newBuilder().setMessage(message).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
