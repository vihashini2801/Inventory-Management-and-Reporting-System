package com.enterprise.inventory.service;

import com.enterprise.inventory.entity.Product;
import com.enterprise.inventory.entity.StockLog;
import com.enterprise.inventory.repository.ProductRepository;
import com.enterprise.inventory.repository.StockLogRepository;
import com.opencsv.CSVWriter;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ProductRepository productRepository;
    private final StockLogRepository stockLogRepository;

    // ===============================
    // CSV EXPORT PRODUCTS
    // ===============================
    public void exportProductsToCSV(HttpServletResponse response) throws Exception {

        List<Product> products = productRepository.findAll();

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=products_report.csv");

        CSVWriter writer = new CSVWriter(response.getWriter());

        writer.writeNext(new String[]{"ID", "Name", "Category", "Quantity", "Price"});

        for (Product p : products) {
            writer.writeNext(new String[]{
                    p.getId().toString(),
                    p.getName(),
                    p.getCategory(),
                    String.valueOf(p.getQuantity()),
                    String.valueOf(p.getPrice())
            });
        }

        writer.close();
    }

    // ===============================
    // CSV EXPORT STOCK LOGS
    // ===============================
    public void exportStockLogsToCSV(HttpServletResponse response) throws Exception {

        List<StockLog> logs = stockLogRepository.findAll();

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=stock_logs_report.csv");

        CSVWriter writer = new CSVWriter(response.getWriter());

        writer.writeNext(new String[]{"Product ID", "Change Type", "Quantity", "Date"});

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for (StockLog log : logs) {
            writer.writeNext(new String[]{
                    log.getProductId().toString(),
                    log.getChangeType(),
                    String.valueOf(log.getQuantityChanged()),
                    log.getChangeDate().format(formatter)
            });
        }

        writer.close();
    }

    // ===============================
    // PDF EXPORT PRODUCTS
    // ===============================
    public void exportProductsToPDF(HttpServletResponse response) throws Exception {

        List<Product> products = productRepository.findAll();

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=products_report.pdf");

        Document document = new Document(PageSize.A4);

        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);

        Paragraph title = new Paragraph("Inventory Products Report", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);

        document.add(title);

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);

        table.addCell("ID");
        table.addCell("Name");
        table.addCell("Category");
        table.addCell("Quantity");
        table.addCell("Price");

        for (Product p : products) {

            table.addCell(String.valueOf(p.getId()));
            table.addCell(p.getName());
            table.addCell(p.getCategory());
            table.addCell(String.valueOf(p.getQuantity()));
            table.addCell(String.valueOf(p.getPrice()));

        }

        document.add(table);

        document.close();
    }

    // ===============================
    // PDF EXPORT STOCK LOGS
    // ===============================
    public void exportLogsToPDF(HttpServletResponse response) throws Exception {

        List<StockLog> logs = stockLogRepository.findAll();

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=stock_logs_report.pdf");

        Document document = new Document(PageSize.A4);

        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);

        Paragraph title = new Paragraph("Stock Logs Report", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);

        document.add(title);

        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);

        table.addCell("Product ID");
        table.addCell("Change Type");
        table.addCell("Quantity");
        table.addCell("Date");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for (StockLog log : logs) {

            table.addCell(String.valueOf(log.getProductId()));
            table.addCell(log.getChangeType());
            table.addCell(String.valueOf(log.getQuantityChanged()));
            table.addCell(log.getChangeDate().format(formatter));

        }

        document.add(table);

        document.close();
    }

}