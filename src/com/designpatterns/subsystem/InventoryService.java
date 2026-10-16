package com.designpatterns.subsystem;

import com.designpatterns.model.Cart;
import com.designpatterns.model.Item;

public class InventoryService {
    public boolean reserveStock(Cart cart) {
        System.out.println("[InventoryService] Checking and reserving stock for items...");
        for (Item item : cart.getItems()) {
            System.out.println(" -> Reserved " + item.getQuantity() + " unit(s) of " + item.getName());
        }
        return true; // Verification: Returns true if stock is successfully reserved.
    }

    public void releaseStock(Cart cart) {
        System.out.println("[InventoryService] Rollback: Releasing reserved stock...");
        for (Item item : cart.getItems()) {
            System.out.println(" -> Restocked " + item.getQuantity() + " unit(s) of " + item.getName());
        }
    }
}