package com.example.Pahana.Edu.controller;

import com.example.Pahana.Edu.model.Bill;
import com.example.Pahana.Edu.repository.BillRepository;
import com.example.Pahana.Edu.service.BillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/billing")
@CrossOrigin
public class BillingController {

    @Autowired
    private BillingService billingService;
    @Autowired
    private BillRepository billRepository;
    
    @Value("${spring.mail.host}")
    private String mailHost;
    
    @Value("${spring.mail.username}")
    private String mailUsername;

    @PostMapping("/calculate")
    public ResponseEntity<Bill> calculateBill(@RequestBody Bill bill) {
        // Calculate total price
        double total = bill.getItems().stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
        bill.setTotalPrice(total);
        bill.setBillDate(LocalDateTime.now());
        return ResponseEntity.ok(bill);
    }

    @GetMapping
    public ResponseEntity<?> getAllBills() {
        return ResponseEntity.ok(billRepository.findAll());
    }

    @PostMapping("/pdf")
    public ResponseEntity<byte[]> generateBillPdf(@RequestBody Bill bill) {
        bill.setBillDate(LocalDateTime.now());
        bill.setTotalPrice(bill.getItems().stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum());
        byte[] pdfBytes = billingService.generateBillPdf(bill);
        billRepository.save(bill);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=bill.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }

    @PostMapping("/email")
    public ResponseEntity<String> sendBillEmail(@RequestBody Bill bill) {
        try {
            System.out.println("Using mail host: " + mailHost);
            System.out.println("Using mail username: " + mailUsername);
            
            bill.setBillDate(LocalDateTime.now());
            bill.setTotalPrice(bill.getItems().stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum());
            byte[] pdfBytes = billingService.generateBillPdf(bill);
            billingService.sendBillEmail(bill, pdfBytes);
            billRepository.save(bill);
            return ResponseEntity.ok("Bill sent to " + bill.getCustomerEmail());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to send email: " + e.getMessage());
        }
    }
} 