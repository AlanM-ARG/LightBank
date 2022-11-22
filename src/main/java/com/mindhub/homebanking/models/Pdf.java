package com.mindhub.homebanking.models;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.TransactionDTO;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Comparator;
import java.util.Set;

public class Pdf {
    Document document;

    Font titleSource = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16);
    Font paragraphSource = FontFactory.getFont(FontFactory.HELVETICA, 12);

    public void createDocument(HttpServletResponse response) throws IOException, DocumentException {
        document = new Document(PageSize.A4, 35, 30, 50, 50);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
    }

    public void addTitle(String text) throws DocumentException, IOException {
        PdfPTable table = new PdfPTable(1);
        PdfPCell cell = new PdfPCell(new Phrase(text, titleSource));
        cell.setColspan(5);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
//        Image image = Image.getInstance("F:/Modulo 3 - MindHub/homebanking/src/main/resources/static/web/assets/img/LB-final - copia.png");
//        document.add(image);
        document.add(table);

    }

    public void addParagraph(String text) throws DocumentException {
        Paragraph paragraph = new Paragraph();
        paragraph.add(new Phrase(text, paragraphSource));
        document.add(paragraph);
    }

    public void addLineJumps() throws DocumentException {
        Paragraph lineJumps = new Paragraph();
        lineJumps.add(new Phrase(Chunk.NEWLINE));
        lineJumps.add(new Phrase(Chunk.NEWLINE));
        document.add(lineJumps);
    }

    public void addTransactionsTable(Set<TransactionDTO> transactions) throws DocumentException {
        PdfPTable table = new PdfPTable(6);
        table.addCell("Id");
        table.addCell("Description");
        table.addCell("Amount");
        table.addCell("Type");
        table.addCell("Date");
        table.addCell("Balance Account");
        transactions.stream().sorted(Comparator.comparing(TransactionDTO::getId)).forEach(transaction -> {
            table.addCell(transaction.getId() + "");
            table.addCell(transaction.getDescription());
            table.addCell(transaction.getAmount() + "");
            table.addCell(transaction.getType() + "");
            table.addCell(transaction.getDate() + "");
            table.addCell(transaction.getAccountCurrentBalance() + "");
        });
        document.add(table);
    }

    public void addAccountTable(AccountDTO accountDTO) throws DocumentException {
        PdfPTable table = new PdfPTable(5);
        table.addCell("Id");
        table.addCell("Number");
        table.addCell("Balance");
        table.addCell("Type");
        table.addCell("Creation Date");

        table.addCell(accountDTO.getId() + "");
        table.addCell(accountDTO.getNumber());
        table.addCell(accountDTO.getBalance() + "");
        table.addCell(accountDTO.getAccountType()+ "");
        table.addCell(accountDTO.getCreationDate() + "");

        document.add(table);
    }

    public void closeDocument() {
        document.close();
    }
}
