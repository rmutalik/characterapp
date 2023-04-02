package com.example.characterapp;

import static android.content.ContentValues.TAG;

import android.content.Context;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelUtils {

    Logger logger = new Logger();
    private static Sheet sheet;
    FileInputStream fileInputStream = null;
    List<List<List<String>>> cellValues = new ArrayList<>();
    List<List<String>> nameValues = new ArrayList<>();
    ArrayList<String> nameValuesRow = new ArrayList<>();
    List<List<String>> otherValues = new ArrayList<>();
    List<String> otherValuesRow = new ArrayList<>();
    Cell cell;


    public List<List<List<String>>> readExcelData(Context context, String fileName) {
        File file = new File(context.getExternalFilesDir(null), fileName);
        readWorksheet(file);
        addData();

        // Returns list of concatenated first and last names, as well as the other data
        cellValues.add(nameValues);
        cellValues.add(otherValues);
        return cellValues;
    }

    private void readWorksheet(File file) {
        Workbook workbook;
        final int SHEET_INDEX = 0;
        try {
            fileInputStream = new FileInputStream(file);
            workbook = new XSSFWorkbook(fileInputStream);
            sheet = workbook.getSheetAt(SHEET_INDEX);
        } catch (IOException e) {
            logger.e(TAG, "Error Reading Exception: ", e);
        } catch (Exception e) {
            logger.e(TAG, "Failed to read file due to Exception: ", e);
        } finally {
            // Close filestream to avoid resource leaks
            try {
                if (null != fileInputStream) {
                    fileInputStream.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void addData() {
        StringBuilder cellVal = new StringBuilder();
        for (Row row : sheet) {
            // Iterate through all the cells in a row (Excluding header row)
            if (row.getRowNum() > 0) {
                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {
                    cell = cellIterator.next();

                    // Concatenate First Name with Last Name
                    if (cell.getColumnIndex() <= 1) {
                        cellVal.append(cell.getStringCellValue());
                        cellVal.append(" ");
                    } else {
                        otherValuesRow.add(cell.getStringCellValue());
                    }
                }
                nameValuesRow.add(cellVal.toString().trim());

                // Deep copy each value to prevent previous values from getting overwritten
                nameValues.add(new ArrayList<>(nameValuesRow));
                otherValues.add(new ArrayList<>(otherValuesRow));

                // Reset values for next record iteration
                cellVal.setLength(0);
                nameValuesRow.clear();
                otherValuesRow.clear();
            }
        }
    }

}