package com.designpatterns.subsystem;

import com.designpatterns.model.Customer;

public class NotificationService {
    public void sendOrderConfirmation(Customer customer, String orderId) {
        System.out.println("[NotificationService] Sending confirmation email to " + customer.getEmail() + " for Order #" + orderId);
    }

    public void sendCancellationNotice(Customer customer, String orderId) {
        System.out.println("[NotificationService] Sending cancellation notice to " + customer.getEmail() + " for Order #" + orderId);
    }
}