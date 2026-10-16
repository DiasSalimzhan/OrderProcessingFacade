package com.designpatterns.subsystem;

import com.designpatterns.model.Customer;

public class ShippingService {
    public String arrangeShipping(Customer customer) {
        System.out.println("[ShippingService] Scheduling delivery to address: " + customer.getAddress() + "...");
        String trackingId = "TRK-" + System.currentTimeMillis();
        System.out.println(" -> Shipping arranged. Tracking ID: " + trackingId);
        return trackingId; // Verification: Returns a valid tracking string identifier.
    }

    public void cancelShipping(String trackingId) {
        System.out.println("[ShippingService] Rollback: Cancelling shipment with tracking ID: " + trackingId);
    }
}