package com.wala.goal_service;

import com.wala.goal_service.GoalResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
public class ExcelExportService {

    public byte[] exportGoalsToExcel(List<GoalResponseDTO> goals) {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Objectifs");

            // Style pour les en-têtes
            CellStyle headerStyle = createHeaderStyle(workbook);

            // Créer l'en-tête
            createHeaderRow(sheet, headerStyle);

            // Remplir les données
            fillDataRows(sheet, goals, workbook);

            // Ajuster la largeur des colonnes
            autoSizeColumns(sheet);

            workbook.write(outputStream);
            return outputStream.toByteArray();

        } catch (Exception e) {
            log.error("Erreur lors de la génération du Excel", e);
            throw new RuntimeException("Erreur lors de l'export Excel", e);
        }
    }

    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }

    private void createHeaderRow(Sheet sheet, CellStyle headerStyle) {
        Row headerRow = sheet.createRow(0);
        String[] headers = {"ID", "Titre", "Description", "Date Début", "Date Fin", "Progression %", "Statut", "Priorité", "Propriétaire", "Créé le"};

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }
    }

    private void fillDataRows(Sheet sheet, List<GoalResponseDTO> goals, Workbook workbook) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        CellStyle normalStyle = workbook.createCellStyle();
        normalStyle.setBorderBottom(BorderStyle.THIN);
        normalStyle.setBorderTop(BorderStyle.THIN);
        normalStyle.setBorderRight(BorderStyle.THIN);
        normalStyle.setBorderLeft(BorderStyle.THIN);

        int rowNum = 1;
        for (GoalResponseDTO goal : goals) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(goal.getId());
            row.createCell(1).setCellValue(goal.getTitle());
            row.createCell(2).setCellValue(goal.getDescription() != null ? goal.getDescription() : "");
            row.createCell(3).setCellValue(goal.getStartDate().format(formatter));
            row.createCell(4).setCellValue(goal.getEndDate() != null ? goal.getEndDate().format(formatter) : "");
            row.createCell(5).setCellValue(goal.getProgress());
            row.createCell(6).setCellValue(goal.getStatus());
            row.createCell(7).setCellValue(goal.getPriority() != null ? goal.getPriority() : "");
            row.createCell(8).setCellValue(goal.getOwnerId());
            row.createCell(9).setCellValue(goal.getCreatedAt().format(formatter));

            // Appliquer le style à toutes les cellules
            for (int i = 0; i < 10; i++) {
                row.getCell(i).setCellStyle(normalStyle);
            }
        }
    }

    private void autoSizeColumns(Sheet sheet) {
        for (int i = 0; i < 10; i++) {
            sheet.autoSizeColumn(i);
        }
    }
}