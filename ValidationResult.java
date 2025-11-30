package models;

import java.util.ArrayList;
import java.util.List;

public class ValidationResult {
    // List to store all mistakes
    private List<Duplicate> duplicates;
    
    public ValidationResult() {
        this.duplicates = new ArrayList<>();
    }
    
    public synchronized void addDuplicate(Duplicate duplicate) {
        duplicates.add(duplicate);
    }
    
    public boolean isValid() {
        return duplicates.isEmpty();
    }
    
    public List<Duplicate> getDuplicates() {
        return duplicates;
    }
    
    public String toString() {
        if (isValid()) {
            return "VALID";
        }
        
        String result = "INVALID\n";
        
        List<Duplicate> rows = new ArrayList<>();
        List<Duplicate> columns = new ArrayList<>();
        List<Duplicate> boxes = new ArrayList<>();
        
        for (Duplicate dup : duplicates) {
            if (dup.getType() == ValidationType.ROW) {
                rows.add(dup);
            } else if (dup.getType() == ValidationType.COLUMN) {
                columns.add(dup);
            } else {
                boxes.add(dup);
            }
        }
        
        for (Duplicate dup : rows) {
            result += dup.toString() + "\n";
        }
        
        if (rows.size() > 0 && (columns.size() > 0 || boxes.size() > 0)) {
            result += "------------------------------------------\n";
        }
        
        for (Duplicate dup : columns) {
            result += dup.toString() + "\n";
        }
        
        if (columns.size() > 0 && boxes.size() > 0) {
            result += "------------------------------------------\n";
        }
        
        // Print boxes
        for (Duplicate dup : boxes) {
            result += dup.toString() + "\n";
        }
        
        return result;
    }

}
