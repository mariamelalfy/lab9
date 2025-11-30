package utils;

import verifiers.ValidationResult;
import models.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class OutputFormatter {
    
    public static String format(ValidationResult result) {
        if (result == null) {
            return "ERROR: No validation result provided";
        }
        
        if (result.isValid()) {
            return "VALID";
        }
        
        StringBuilder sb = new StringBuilder("INVALID\n");
        
        // Separate duplicates by type
        List<Duplicate> rows = new ArrayList<>();
        List<Duplicate> columns = new ArrayList<>();
        List<Duplicate> boxes = new ArrayList<>();
        
        for (Duplicate dup : result.getDuplicates()) {
            switch (dup.getType()) {
                case ROW:
                    rows.add(dup);
                    break;
                case COLUMN:
                    columns.add(dup);
                    break;
                case BOX:
                    boxes.add(dup);
                    break;
            }
        }
        
        // Sort each group by index first, then by value
        Comparator<Duplicate> comparator = Comparator
            .comparingInt(Duplicate::getIndex)
            .thenComparingInt(Duplicate::getValue);
        
        rows.sort(comparator);
        columns.sort(comparator);
        boxes.sort(comparator);
        
        // Format rows - JUST USE toString()!
        for (Duplicate dup : rows) {
            sb.append(dup.toString()).append("\n");
        }
        
        // Add separator if there are rows and other types
        if (!rows.isEmpty() && (!columns.isEmpty() || !boxes.isEmpty())) {
            sb.append("------------------------------------------\n");
        }
        
        // Format columns - JUST USE toString()!
        for (Duplicate dup : columns) {
            sb.append(dup.toString()).append("\n");
        }
        
        // Add separator if there are columns and boxes
        if (!columns.isEmpty() && !boxes.isEmpty()) {
            sb.append("------------------------------------------\n");
        }
        
        for (Duplicate dup : boxes) {
            sb.append(dup.toString()).append("\n");
        }
        
        return sb.toString().trim();
    }
}
