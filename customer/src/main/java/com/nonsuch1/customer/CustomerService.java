package com.nonsuch1.customer;

import com.nonsuch1.clients.fraud.FraudCheckResponse;
import com.nonsuch1.clients.fraud.FraudClient;
import com.nonsuch1.clients.notification.NotificationClient;
import com.nonsuch1.clients.notification.NotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final FraudClient fraudClient;
    private final NotificationClient notificationClient;

    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();
        // TODO: check if email is valid
        // TODO: check if email not taken
        customerRepository.saveAndFlush(customer);

        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());
        if (fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("fraudster");
        }
        // TODO: make it async, i.e. add to queue
        notificationClient.sendNotification(
                new NotificationRequest(
                        customer.getId(), customer.getEmail(), "Hi there!")
        );
        // TODO: send notification
    }
}
