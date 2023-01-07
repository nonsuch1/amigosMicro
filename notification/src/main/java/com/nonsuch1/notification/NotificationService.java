package com.nonsuch1.notification;

import com.nonsuch1.clients.notification.NotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public void sendNotification(NotificationRequest request) {
        notificationRepository.save(Notification.builder()
                .toCustomerId(request.toCustomerId())
                .toCustomerEmail(request.toCustomerName())
                .sender("nonsuch1")
                .message(request.message())
                .sentAt(LocalDateTime.now())
                .build());
    }
}
