package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Duplicate {
    private ValidationType type;
    private int index;
    private int value;
    private List<int[]> positions;
    
    public Duplicate(ValidationType type, int index, int value, List<int[]> positions) {
        this.type = type;
        this.index = index;
        this.value = value;
        this.positions = positions;
    }
    
    public ValidationType getType() {
        return type;
    }
    
    public int getIndex() {
        return index;
    }
    
    public int getValue() {
        return value;
    }
    
    public List<int[]> getPositions() {
        return positions;
    }
    
    /**
     * CORRECT FORMAT:
     * ROW 1, #5, [3, 7]          <- just column numbers
     * COL 3, #2, [1, 5, 9]       <- just row numbers
     * BOX 5, #1, [1, 2, 3, ...]  <- positions within box (1-9)
     */
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(type).append(" ").append(index + 1).append(", #").append(value).append(", [");
        
        // Extract ONLY the relevant numbers
        List<Integer> numbers = new ArrayList<>();
        
        for (int[] pos : positions) {
            int row = pos[0];  // 1-based (1-9)
            int col = pos[1];  // 1-based (1-9)
            
            if (type == ValidationType.ROW) {
                numbers.add(col);
                
            } else if (type == ValidationType.COLUMN) {
                numbers.add(row);
                
            } else { 
                int boxStartRow = (index / 3) * 3 + 1;  
                int boxStartCol = (index % 3) * 3 + 1;  
                
                int relativeRow = row - boxStartRow;
                int relativeCol = col - boxStartCol;  
                
                int positionInBox = relativeRow * 3 + relativeCol + 1;
                numbers.add(positionInBox);
            }
        }
        
        // Sort and format 
        Collections.sort(numbers);
        for (int i = 0; i < numbers.size(); i++) {
            result.append(numbers.get(i));
            if (i < numbers.size() - 1) {
                result.append(", ");
            }
        }
        
        result.append("]");
        return result.toString();
    }
}
