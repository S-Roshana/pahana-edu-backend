package com.example.Pahana.Edu.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "bills")
public class Bill {
    @Id
    private String id;
    private String customerName;
    private String customerContactNo;
    private String customerAddress;
    private String customerEmail;
    private List<BookItem> items;
    private double totalPrice;
    private LocalDateTime billDate;

    public static class BookItem {
        private String title;
        private String category;
        private int quantity;
        private double price;

        public BookItem() {}
        public BookItem(String title, String category, int quantity, double price) {
            this.title = title;
            this.category = category;
            this.quantity = quantity;
            this.price = price;
        }
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }
        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
        public double getPrice() { return price; }
        public void setPrice(double price) { this.price = price; }
    }

    public Bill() {}
    public Bill(String customerName, String customerContactNo, String customerAddress, String customerEmail, List<BookItem> items, double totalPrice, LocalDateTime billDate) {
        this.customerName = customerName;
        this.customerContactNo = customerContactNo;
        this.customerAddress = customerAddress;
        this.customerEmail = customerEmail;
        this.items = items;
        this.totalPrice = totalPrice;
        this.billDate = billDate;
    }
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getCustomerContactNo() { return customerContactNo; }
    public void setCustomerContactNo(String customerContactNo) { this.customerContactNo = customerContactNo; }
    public String getCustomerAddress() { return customerAddress; }
    public void setCustomerAddress(String customerAddress) { this.customerAddress = customerAddress; }
    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }
    public List<BookItem> getItems() { return items; }
    public void setItems(List<BookItem> items) { this.items = items; }
    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
    public LocalDateTime getBillDate() { return billDate; }
    public void setBillDate(LocalDateTime billDate) { this.billDate = billDate; }
} 