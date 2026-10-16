package com.designpatterns.facade;

import com.designpatterns.model.Cart;
import com.designpatterns.model.Customer;
import com.designpatterns.subsystem.InventoryService;
import com.designpatterns.subsystem.NotificationService;
import com.designpatterns.subsystem.PaymentProcessor;
import com.designpatterns.subsystem.ShippingService;

public class OrderFacade {
    private final InventoryService inventoryService;
    private final PaymentProcessor paymentProcessor;
    private final ShippingService shippingService;
    private final NotificationService notificationService;

    public OrderFacade() {
        this.inventoryService = new InventoryService();
        this.paymentProcessor = new PaymentProcessor();
        this.shippingService = new ShippingService();
        this.notificationService = new NotificationService();
    }

    public String placeOrder(Cart cart, Customer customer) {
        System.out.println("=== Starting Order Placement Workflow ===");
        String orderId = "ORD-" + System.currentTimeMillis();
        double totalAmount = cart.calculateTotal();

        // Step 1: Reserve Inventory
        boolean stockReserved = inventoryService.reserveStock(cart);
        if (!stockReserved) {
            System.out.println("Error: Insufficient stock. Order failed.");
            return null;
        }

        // Step 2: Process Payment (with Rollback on failure)
        boolean paymentSuccess = paymentProcessor.processPayment(customer.getCustomerId(), totalAmount);
        if (!paymentSuccess) {
            System.out.println("Error: Payment declined. Initiating rollback...");
            inventoryService.releaseStock(cart);
            return null;
        }

        // Step 3: Arrange Shipping (with Rollback on failure)
        String trackingId = null;
        try {
            trackingId = shippingService.arrangeShipping(customer);
            if (trackingId == null) {
                throw new RuntimeException("Shipping assignment failed.");
            }
        } catch (Exception e) {
            System.out.println("Error: Shipping failed. Initiating full rollback...");
            paymentProcessor.refundPayment(customer.getCustomerId(), totalAmount);
            inventoryService.releaseStock(cart);
            return null;
        }

        // Step 4: Send Notification
        notificationService.sendOrderConfirmation(customer, orderId);

        System.out.println("=== Order #" + orderId + " Successfully Placed! ===\n");
        return orderId;
    }

    public void cancelOrder(String orderId, Cart cart, Customer customer) {
        System.out.println("=== Cancelling Order #" + orderId + " ===");
        shippingService.cancelShipping("TRK-SAMPLE");
        paymentProcessor.refundPayment(customer.getCustomerId(), cart.calculateTotal());
        inventoryService.releaseStock(cart);
        notificationService.sendCancellationNotice(customer, orderId);
        System.out.println("=== Order #" + orderId + " Successfully Cancelled! ===\n");
    }
}
