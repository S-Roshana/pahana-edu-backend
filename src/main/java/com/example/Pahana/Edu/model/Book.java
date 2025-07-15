package com.example.Pahana.Edu.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.HashMap;
import java.util.Map;

@Document(collection = "books")
public class Book {
    @Id
    private String id;
    private String title;
    private String author;
    private String description;
    private String imageUrl;
    private double price;
    private int quantity;
    private String category;

    // NEW: Store individual user ratings (userId -> rating)
    private Map<String, Double> userRatings = new HashMap<>();

    // Remove old rating fields - we'll calculate these dynamically
    // private double rating;
    // private double totalRating;
    // private int ratingCount;

    public Book() {
        this.userRatings = new HashMap<>();
    }

    public Book(String title, String author, String description, String imageUrl,
                double price, int quantity, String category) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.userRatings = new HashMap<>();
    }

    // Calculate average rating from user ratings
    public double getRating() {
        if (userRatings.isEmpty()) return 0.0;
        double sum = userRatings.values().stream().mapToDouble(Double::doubleValue).sum();
        return Math.round((sum / userRatings.size()) * 10.0) / 10.0;
    }

    // Get total number of ratings
    public int getRatingCount() {
        return userRatings.size();
    }

    // Check if user has rated this book
    public boolean hasUserRated(String userId) {
        return userRatings.containsKey(userId);
    }

    // Get user's rating for this book
    public Double getUserRating(String userId) {
        return userRatings.get(userId);
    }

    // Add or update user rating
    public void setUserRating(String userId, double rating) {
        userRatings.put(userId, rating);
    }

    // Remove user rating
    public void removeUserRating(String userId) {
        userRatings.remove(userId);
    }

    // Getters and setters
    public String getId() { return id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Map<String, Double> getUserRatings() { return userRatings; }
    public void setUserRatings(Map<String, Double> userRatings) { this.userRatings = userRatings; }
}