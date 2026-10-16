====================================================================
SOFTWARE DESIGN PATTERNS (ShP-2216)
Assignment #4 Report: Facade Pattern
====================================================================

Student Name: Salimzhan Dias
Group: SE-2522
--------------------------------------------------------------------
1. INTRODUCTION
--------------------------------------------------------------------
Modern enterprise applications rely on complex subsystems where workflows span multiple services, such as inventory management, payment processing gateways, shipping logistics, and notification dispatchers. If a client application had to directly coordinate, instantiate, and manage exception rollbacks across all these decoupled services, the codebase would become tightly coupled, difficult to maintain, and prone to client-side errors.

To solve this, we implemented the Facade Structural Design Pattern. The chosen domain is an Online Order Processing System. The OrderFacade wraps four complex subsystem classes (InventoryService, PaymentProcessor, ShippingService, and NotificationService) behind a clean, simplified interface offering methods like placeOrder(cart, customer) and cancelOrder(...). This shields the presentation or client layer from internal subsystem complexity while preserving transactional consistency and error rollback.

--------------------------------------------------------------------
2. UML CLASS DIAGRAM
--------------------------------------------------------------------
[Client / Main]
│ (uses)
▼
[OrderFacade] ────────────────────────┐
│                              │
├─► [InventoryService]         │
├─► [PaymentProcessor]         │ (Manages Subsystems &
├─► [ShippingService]          │  Rollbacks internally)
└─► [NotificationService]      │
▼
[Model Classes]
(Cart, Customer, Item)

--------------------------------------------------------------------
3. CLEAN CODE PRINCIPLES & JUSTIFICATIONS
--------------------------------------------------------------------

Principle 1: Meaningful & Self-Explanatory Naming
- Justification: Class and method names clearly express their domain intent without requiring external documentation. For example, placeOrder() and reserveStock() instantly communicate their responsibilities.
- Code Excerpt:
  public String placeOrder(Cart cart, Customer customer) { ... }

Principle 2: Single Responsibility Principle (SRP)
- Justification: The Facade class is solely responsible for workflow orchestration and coordination. It does not implement low-level SQL queries, payment gateway encryption, or email socket connections; those reside entirely within their respective subsystem services.
- Code Excerpt:
  // InventoryService handles inventory state exclusively; OrderFacade only sequences calls.
  boolean stockReserved = inventoryService.reserveStock(cart);

Principle 3: Encapsulation & No Leaking of Subsystem Details
- Justification: The facade public interface exposes high-level domain objects (Cart, Customer) and strings, hiding low-level technical artifacts (such as raw transaction tokens, database connection handles, or API status codes) from the client.
- Code Excerpt:
  // Client only interacts with high-level facade methods:
  String orderId = orderFacade.placeOrder(cart, customer);

Principle 4: Small, Focused Subsystem Classes
- Justification: Each subsystem class has a single, well-defined domain responsibility (e.g., ShippingService handles tracking IDs and logistics, while PaymentProcessor handles authorizations).
- Code Excerpt:
  public class ShippingService {
  public String arrangeShipping(Customer customer) { ... }
  }

Principle 5: Robust Error Handling and Transaction Rollback
- Justification: Coordination across multiple services requires fault tolerance. If a downstream service (like payment or shipping) fails, the Facade automatically executes compensatory rollback actions across preceding services to maintain data integrity.
- Code Excerpt:
  if (!paymentSuccess) {
  inventoryService.releaseStock(cart);
  return null;
  }

--------------------------------------------------------------------
4. CONCLUSION
--------------------------------------------------------------------
- What the Facade Hides: The Facade hides the internal instantiation order, sequential orchestration logic, exception handling, and compensatory rollback routines required across multiple independent subsystem services.
- What Remains Accessible: In accordance with the true definition of the Facade pattern, the subsystem classes (InventoryService, PaymentProcessor, ShippingService, NotificationService) remain fully public and decoupled. Advanced clients retain the freedom to instantiate and interact with individual subsystem components directly when custom, low-level operations are required.

--------------------------------------------------------------------
5. GITHUB REPOSITORY LINK
--------------------------------------------------------------------
Repository URL: https://github.com/username/OrderProcessingFacade