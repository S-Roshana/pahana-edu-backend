package com.example.Pahana.Edu.service;

import com.example.Pahana.Edu.model.Bill;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import jakarta.mail.internet.MimeMessage;


@Service
public class BillingService {
    @Autowired
    private JavaMailSender mailSender;

    public byte[] generateBillPdf(Bill bill) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Document document = new Document(PageSize.A4, 40, 40, 60, 40);
            PdfWriter.getInstance(document, baos);
            document.open();

            // Define modern fonts and colors
            Font headerFont = new Font(Font.HELVETICA, 24, Font.BOLD, new Color(41, 128, 185));
            Font titleFont = new Font(Font.HELVETICA, 16, Font.BOLD, new Color(52, 73, 94));
            Font subtitleFont = new Font(Font.HELVETICA, 12, Font.BOLD, new Color(44, 62, 80));
            Font normalFont = new Font(Font.HELVETICA, 10, Font.NORMAL, new Color(52, 73, 94));
            Font tableHeaderFont = new Font(Font.HELVETICA, 11, Font.BOLD, Color.WHITE);
            Font tableFont = new Font(Font.HELVETICA, 10, Font.NORMAL, new Color(44, 62, 80));
            Font totalFont = new Font(Font.HELVETICA, 14, Font.BOLD, new Color(231, 76, 60));

            // Header with gradient-like effect
            PdfPTable headerTable = new PdfPTable(2);
            headerTable.setWidthPercentage(100);
            headerTable.setWidths(new int[]{2, 1});
            
            PdfPCell headerCell = new PdfPCell(new Phrase("PAHANA EDU BOOKSHOP", headerFont));
            headerCell.setBackgroundColor(new Color(41, 128, 185));
            headerCell.setPadding(15);
            headerCell.setBorder(0);
            headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            headerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            
            PdfPCell billCell = new PdfPCell(new Phrase("INVOICE", titleFont));
            billCell.setBackgroundColor(new Color(52, 152, 219));
            billCell.setPadding(15);
            billCell.setBorder(0);
            billCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            billCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            
            headerTable.addCell(headerCell);
            headerTable.addCell(billCell);
            document.add(headerTable);
            document.add(new Paragraph(" "));

            // Bill details section
            PdfPTable detailsTable = new PdfPTable(2);
            detailsTable.setWidthPercentage(100);
            detailsTable.setWidths(new int[]{1, 1});
            detailsTable.setSpacingBefore(20);
            detailsTable.setSpacingAfter(20);

            // Left column - Bill info
            PdfPCell billInfoCell = new PdfPCell();
            billInfoCell.setBorder(0);
            billInfoCell.setPadding(10);
            
            Paragraph billDate = new Paragraph("Bill Date: " + bill.getBillDate().format(DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm")), subtitleFont);
            billDate.setSpacingAfter(5);
            billInfoCell.addElement(billDate);
            
            Paragraph billNumber = new Paragraph("Bill #: " + System.currentTimeMillis(), subtitleFont);
            billInfoCell.addElement(billNumber);
            
            detailsTable.addCell(billInfoCell);

            // Right column - Customer info
            PdfPCell customerInfoCell = new PdfPCell();
            customerInfoCell.setBorder(0);
            customerInfoCell.setPadding(10);
            
            Paragraph customerTitle = new Paragraph("CUSTOMER DETAILS", subtitleFont);
            customerTitle.setSpacingAfter(8);
            customerInfoCell.addElement(customerTitle);
            
            customerInfoCell.addElement(new Paragraph("Name: " + bill.getCustomerName(), normalFont));
            customerInfoCell.addElement(new Paragraph("Contact: " + bill.getCustomerContactNo(), normalFont));
            customerInfoCell.addElement(new Paragraph("Email: " + bill.getCustomerEmail(), normalFont));
            customerInfoCell.addElement(new Paragraph("Address: " + bill.getCustomerAddress(), normalFont));
            
            detailsTable.addCell(customerInfoCell);
            document.add(detailsTable);

            // Items table with modern styling
            PdfPTable itemsTable = new PdfPTable(5);
            itemsTable.setWidthPercentage(100);
            itemsTable.setWidths(new int[]{3, 2, 1, 2, 2});
            itemsTable.setSpacingBefore(20);
            itemsTable.setSpacingAfter(20);

            // Table header
            String[] headers = {"Book Title", "Category", "Qty", "Unit Price", "Total"};
            for (String header : headers) {
                PdfPCell tableHeaderCell = new PdfPCell(new Phrase(header, tableHeaderFont));
                tableHeaderCell.setBackgroundColor(new Color(52, 73, 94));
                tableHeaderCell.setPadding(8);
                tableHeaderCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                tableHeaderCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                itemsTable.addCell(tableHeaderCell);
            }

            // Table rows
            for (Bill.BookItem item : bill.getItems()) {
                double itemTotal = item.getPrice() * item.getQuantity();
                
                PdfPCell titleCell = new PdfPCell(new Phrase(item.getTitle(), tableFont));
                titleCell.setPadding(8);
                titleCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                itemsTable.addCell(titleCell);
                
                PdfPCell categoryCell = new PdfPCell(new Phrase(item.getCategory(), tableFont));
                categoryCell.setPadding(8);
                categoryCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                categoryCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                itemsTable.addCell(categoryCell);
                
                PdfPCell qtyCell = new PdfPCell(new Phrase(String.valueOf(item.getQuantity()), tableFont));
                qtyCell.setPadding(8);
                qtyCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                qtyCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                itemsTable.addCell(qtyCell);
                
                PdfPCell priceCell = new PdfPCell(new Phrase("Rs. " + String.format("%.2f", item.getPrice()), tableFont));
                priceCell.setPadding(8);
                priceCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                priceCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                itemsTable.addCell(priceCell);
                
                PdfPCell totalCell = new PdfPCell(new Phrase("Rs. " + String.format("%.2f", itemTotal), tableFont));
                totalCell.setPadding(8);
                totalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                totalCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                itemsTable.addCell(totalCell);
            }

            document.add(itemsTable);

            // Total section
            PdfPTable totalTable = new PdfPTable(2);
            totalTable.setWidthPercentage(50);
            totalTable.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totalTable.setWidths(new int[]{1, 1});

            PdfPCell totalLabelCell = new PdfPCell(new Phrase("TOTAL AMOUNT:", totalFont));
            totalLabelCell.setBorder(0);
            totalLabelCell.setPadding(10);
            totalLabelCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totalLabelCell.setBackgroundColor(new Color(236, 240, 241));

            PdfPCell totalValueCell = new PdfPCell(new Phrase("Rs. " + String.format("%.2f", bill.getTotalPrice()), totalFont));
            totalValueCell.setBorder(0);
            totalValueCell.setPadding(10);
            totalValueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totalValueCell.setBackgroundColor(new Color(231, 76, 60));

            totalTable.addCell(totalLabelCell);
            totalTable.addCell(totalValueCell);
            document.add(totalTable);

            // Footer
            document.add(new Paragraph(" "));
            PdfPTable footerTable = new PdfPTable(1);
            footerTable.setWidthPercentage(100);
            footerTable.setSpacingBefore(30);
            
            PdfPCell footerCell = new PdfPCell(new Phrase("Thank you for your purchase! Visit us again.", normalFont));
            footerCell.setBorder(0);
            footerCell.setPadding(15);
            footerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            footerCell.setBackgroundColor(new Color(236, 240, 241));
            
            footerTable.addCell(footerCell);
            document.add(footerTable);

            document.close();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate PDF: " + e.getMessage(), e);
        }
    }

    public void sendBillEmail(Bill bill, byte[] pdfBytes) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(bill.getCustomerEmail());
            helper.setSubject("Your Bill from Pahana Edu Bookshop");
            helper.setText("Dear " + bill.getCustomerName() + ",\n\nPlease find your bill attached.\n\nThank you for your purchase!\n\nPahana Edu Bookshop");
            helper.addAttachment("bill.pdf", new ByteArrayResource(pdfBytes));
            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send email: " + e.getMessage(), e);
        }
    }
} 