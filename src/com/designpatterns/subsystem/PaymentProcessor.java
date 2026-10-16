package com.designpatterns.subsystem;

public class PaymentProcessor {
    public boolean processPayment(String customerId, double amount) {
        System.out.println("[PaymentProcessor] Processing payment of $" + amount + " for customer " + customerId + "...");
        // Simulating a successful transaction
        System.out.println(" -> Payment authorized successfully.");
        return true; // Verification: Returns true upon successful gateway authorization.
    }

    public void refundPayment(String customerId, double amount) {
        System.out.println("[PaymentProcessor] Rollback: Issuing full refund of $" + amount + " to customer " + customerId);
    }
}