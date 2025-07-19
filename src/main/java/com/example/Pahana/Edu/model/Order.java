package com.example.Pahana.Edu.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "orders")
public class Order {

    @Id
    private String id;

    private String bookId;
    private String bookTitle;
    private String customerName;
    private String customerAddress;
    private String customerContact;
    private String customerEmail;
    private int quantity;
    private double totalPrice;
    private LocalDateTime orderDate;
    private String paymentMethod;


    public Order() {
    }

    public Order(String bookId, String bookTitle, String customerName, String customerAddress, String customerContact, String customerEmail, int quantity, double totalPrice, LocalDateTime orderDate, String paymentMethod) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerContact = customerContact;
        this.customerEmail = customerEmail;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.paymentMethod = paymentMethod;
    }

    // Getters and setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

}
