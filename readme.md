# Project Setup:
* Below three applications/projects are created in this example:
  * grpc-common (Holds the common files)
  * payment-service (grpc server)
  * order-service (grpc-client)
# Steps:
### grpc-common
  * Define the service contract, request and response in protocol buffer file (.proto) as mentioned below.
    ```
      // Service Signature
      service PaymentService {
          rpc GetPayment(PaymentRequest) returns (PaymentResponse);
      }
    
      // Request
      message PaymentRequest {
       string name = 1;
      }
        
      // Response
      message PaymentResponse {
       string message = 1;
      }
      ```
  * Upon build, the java service class, request and response class are generated from this file.
  * These generated classes can be found in the `target -> generated-sources -> protobuf` directory.
    <img width="384" alt="Screenshot 2024-08-31 at 12 25 03 PM" src="https://github.com/user-attachments/assets/6e1112d2-6577-499f-bbfc-2d88a330febe">


### payment-service (GRPC server):
  * Define the implementation of the GRPC service by extending the above generated service like below:
    ```
    @GrpcService
    public class PaymentServiceImpl extends PaymentServiceGrpc.PaymentServiceImplBase {

    @Override
    public void getPayment(PaymentRequest request, StreamObserver<PaymentResponse> responseObserver) {
        final String message = "Payment is confirmed";
        ...
        // BUSINESS LOGIC GOES HERE
        ...
        PaymentResponse response = PaymentResponse.newBuilder().setMessage(message).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
      }
    }
    ```
  * This service is annotated by `@GrpcService`to mark this as a GRPC service.
  * The GRPC service port is defined in the `application.properties`:
    ```
    grpc.server.port=9090 
    ```

### order-service (GRPC client):
  * Use the GRPC client to access the GRPC service like below:
    ```
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
    ```
  * Here the service is defined as `payment-service` and `@GrpcClient` annotation is used to auto-wire the generated stub bean.
    Using this stub bean, we can access the GRPC service hosted on the GRPC server (payment service).
  * The GRPC server path is mentioned in the `application.properties` file as below:
    ```
    # Format : grpc.client.<service-name-defined-in-@GrpcClient>.*
    
    grpc.client.payment-service.address=static://localhost:9090
    grpc.client.payment-service.negotiation-type=plaintext
    ```





 
