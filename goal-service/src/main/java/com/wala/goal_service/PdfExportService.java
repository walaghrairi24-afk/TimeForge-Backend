package com.wala.goal_service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.wala.goal_service.GoalResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
public class PdfExportService {

    private static final Font TITLE_FONT = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
    private static final Font HEADER_FONT = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
    private static final Font NORMAL_FONT = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);

    public byte[] exportGoalsToPdf(List<GoalResponseDTO> goals) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Document document = new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(document, outputStream);

            document.open();

            // Titre
            Paragraph title = new Paragraph("Rapport des Objectifs", TITLE_FONT);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            // Tableau
            PdfPTable table = createTable();

            // En-têtes
            addTableHeader(table);

            // Données
            addTableData(table, goals);

            document.add(table);
            document.close();

            return outputStream.toByteArray();
        } catch (Exception e) {
            log.error("Erreur lors de la génération du PDF", e);
            throw new RuntimeException("Erreur lors de l'export PDF", e);
        }
    }

    private PdfPTable createTable() throws DocumentException {
        PdfPTable table = new PdfPTable(10);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        float[] columnWidths = {1f, 2f, 3f, 1.5f, 1.5f, 1f, 1.5f, 1.5f, 1.5f, 1.5f};
        table.setWidths(columnWidths);

        return table;
    }

    private void addTableHeader(PdfPTable table) {
        String[] headers = {"ID", "Titre", "Description", "Date Début", "Date Fin", "Progression", "Statut", "Priorité", "Propriétaire", "Créé le"};

        for (String header : headers) {
            PdfPCell cell = new PdfPCell();
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setBorderWidth(1);
            cell.setPhrase(new Phrase(header, HEADER_FONT));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }
    }

    private void addTableData(PdfPTable table, List<GoalResponseDTO> goals) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (GoalResponseDTO goal : goals) {
            table.addCell(createCell(String.valueOf(goal.getId())));
            table.addCell(createCell(goal.getTitle()));
            table.addCell(createCell(goal.getDescription() != null ? goal.getDescription() : ""));
            table.addCell(createCell(goal.getStartDate().format(formatter)));
            table.addCell(createCell(goal.getEndDate() != null ? goal.getEndDate().format(formatter) : ""));
            table.addCell(createCell(goal.getProgress() + "%"));
            table.addCell(createCell(goal.getStatus()));
            table.addCell(createCell(goal.getPriority() != null ? goal.getPriority() : ""));
            table.addCell(createCell(String.valueOf(goal.getOwnerId())));
            table.addCell(createCell(goal.getCreatedAt().format(formatter)));
        }
    }

    private PdfPCell createCell(String content) {
        PdfPCell cell = new PdfPCell(new Phrase(content, NORMAL_FONT));
        cell.setBorderWidth(1);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        return cell;
    }
}