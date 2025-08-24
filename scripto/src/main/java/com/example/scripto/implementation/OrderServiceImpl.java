package com.example.scripto.implementation;

import com.example.scripto.entity.*;
import com.example.scripto.repository.*;
import com.example.scripto.response.buyer.order.OrderItemResponse;
import com.example.scripto.response.buyer.order.OrderResponse;
import com.example.scripto.service.IOrderService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired private OrderRepository orderRepo;
    @Autowired private OrderItemRepository orderItemRepo;
    @Autowired private BuyerCartRepo cartRepo;
    @Autowired private UserRepository userRepo;
    @Autowired private SellerBookListingRepo bookRepo;
    @Autowired private BuyerAddressRepo addressRepo;



    // Create order from cart (buyerEmail from SecurityContext)
    @Override
    @Transactional
    public ResponseEntity<String> placeOrder(Long addressId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String buyerEmail = authentication.getName();

        User buyer = userRepo.findByEmail(buyerEmail);
        if (buyer == null) {
            throw new RuntimeException("User not found");
        }

        // Check if selected address belongs to user
        BuyerAddress selectedAddress = addressRepo.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));
        if (!selectedAddress.getUser().equals(buyer)) {
            throw new RuntimeException("Invalid address selection");
        }

        List<CartItem> cartItems = cartRepo.findByBuyer(buyer);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = new Order();
        order.setBuyer(buyer);
        order.setAddress(selectedAddress); // Save selected address

        List<OrderItem> items = cartItems.stream().map(ci -> {
            OrderItem oi = new OrderItem();
            oi.setBook(ci.getBook());
            oi.setQuantity(ci.getQuantity());
            oi.setPrice(ci.getBook().getPrice());
            oi.setStatus(OrderItemStatus.PENDING);
            oi.setOrder(order);
            return oi;
        }).collect(Collectors.toList());

        double total = items.stream().mapToDouble(i -> i.getPrice() * i.getQuantity()).sum();
        order.setTotalAmount(total);
        order.setItems(items);

        Order saved = orderRepo.save(order);

        // Clear cart
        cartRepo.deleteAll(cartItems);

        return new ResponseEntity<>("Order placed successfully with ID: " + saved.getOrderId(), HttpStatus.OK);
    }




    // Seller updates status for a particular order item they own
    @Override
    @Transactional
    public ResponseEntity<String> updateOrderItemStatus(Long orderItemId, OrderItemStatus newStatus) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String sellerEmail = authentication.getName();

        OrderItem item = orderItemRepo.findById(orderItemId)
                .orElseThrow(() -> new RuntimeException("Order item not found"));

        // verify seller ownership
        User seller = item.getBook().getSeller();
        if (seller == null || !seller.getEmail().equalsIgnoreCase(sellerEmail)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not authorized to modify this item");
        }

        // business rules
        OrderItemStatus current = item.getStatus();

        // Disallow transitions from REJECTED/DONE etc.
        if (current == OrderItemStatus.REJECTED || current == OrderItemStatus.DELIVERED) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot change status from " + current);
        }

        // If seller confirms: just set CONFIRMED (we could reserve stock here if required)
        if (newStatus == OrderItemStatus.CONFIRMED) {
            // optional: check availability now, but we commit stock on SHIPPED per your earlier requirement
            item.setStatus(OrderItemStatus.CONFIRMED);
            orderItemRepo.save(item);
            // TODO: notify buyer
            return ResponseEntity.ok("Order item confirmed");
        }

        // If seller rejects:
        if (newStatus == OrderItemStatus.REJECTED) {
            item.setStatus(OrderItemStatus.REJECTED);
            orderItemRepo.save(item);
            // TODO: refund logic / notify buyer
            return ResponseEntity.ok("Order item rejected");
        }

        // If seller ships: check availability and reduce soldQuantity
        if (newStatus == OrderItemStatus.SHIPPED) {
            BookListing book = item.getBook();
            int available = book.getTotalQuantity() - book.getSoldQuantity();
            if (available < item.getQuantity()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Insufficient stock to ship. Available: " + available);
            }
            // update sold quantity
            book.setSoldQuantity(book.getSoldQuantity() + item.getQuantity());
            bookRepo.save(book);

            item.setStatus(OrderItemStatus.SHIPPED);
            orderItemRepo.save(item);

            // Optionally set order-level flags if all items shipped
            return ResponseEntity.ok("Order item marked as shipped and stock updated");
        }

        // Delivery handled similarly
        if (newStatus == OrderItemStatus.DELIVERED) {
            item.setStatus(OrderItemStatus.DELIVERED);
            orderItemRepo.save(item);
            return ResponseEntity.ok("Order item marked delivered");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unsupported status");
    }




    //Used to see all the order details this is the buyer functionality
    public ResponseEntity<List<OrderResponse>> getOrderDetails(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String buyerEmail = authentication.getName();

        if(buyerEmail == null){
            throw new RuntimeException("The user is not found");
        }

        List<Order> orders = orderRepo.findByBuyerEmail(buyerEmail);

        // Map entities to DTOs
        List<OrderResponse> collect = orders.stream().map(this::toDto).collect(Collectors.toList());

        return new ResponseEntity<>(collect, HttpStatus.OK);
    }


    private OrderResponse toDto(Order order) {
        List<OrderItemResponse> items = order.getItems().stream()
                .map(this::toItemDto)
                .collect(Collectors.toList());

        return new OrderResponse(
                order.getOrderId(),
                order.getOrderDate(),
                order.getTotalAmount(),
                items
        );
    }

    private OrderItemResponse toItemDto(OrderItem item) {
        return new OrderItemResponse(
                item.getId(),
                item.getBook().getBookId(),
                item.getBook().getBookName(),
                item.getPrice(),
                item.getQuantity(),
                item.getStatus()
        );
    }
}

