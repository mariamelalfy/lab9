package utils;

import models.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Utility class for formatting validation results for display.
 */
public class OutputFormatter {
    
    /**
     * For valid boards: Returns "VALID"
     * For invalid boards: Returns "INVALID" followed by all duplicates
     * grouped by type (ROW, COLUMN, BOX) with separators.
     */
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
        
        // Format rows
        for (Duplicate dup : rows) {
            sb.append(formatDuplicate(dup)).append("\n");
        }
        
        // Add separator if there are rows and other types
        if (!rows.isEmpty() && (!columns.isEmpty() || !boxes.isEmpty())) {
            sb.append("------------------------------------------\n");
        }
        
        // Format columns
        for (Duplicate dup : columns) {
            sb.append(formatDuplicate(dup)).append("\n");
        }
        
        // Add separator if there are columns and boxes
        if (!columns.isEmpty() && !boxes.isEmpty()) {
            sb.append("------------------------------------------\n");
        }
        
        // Format boxes
        for (Duplicate dup : boxes) {
            sb.append(formatDuplicate(dup)).append("\n");
        }
        
        return sb.toString().trim();
    }
    
    /*
     * Formats a single duplicate entry for display.
     * Format: "ROW 1, #5, [0,2], [0,7]"
     */
    private static String formatDuplicate(Duplicate dup) {
        StringBuilder sb = new StringBuilder();
        
        // Add type and index (convert to 1-indexed for display)
        sb.append(dup.getType().toString()).append(" ");
        sb.append(dup.getIndex() + 1).append(", ");
        
        // Add value with # prefix
        sb.append("#").append(dup.getValue()).append(", ");
        
        // Add all positions in format [row,col]
        List<int[]> positions = dup.getPositions();
        for (int i = 0; i < positions.size(); i++) {
            int[] pos = positions.get(i);
            sb.append("[").append(pos[0]).append(",").append(pos[1]).append("]");
            if (i < positions.size() - 1) {
                sb.append(", ");
            }
        }
        
        return sb.toString();
    }
}
