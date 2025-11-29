package models;

import java.util.ArrayList;
import java.util.List;

public class Duplicate {
    private ValidationType type;  // ROW, COLUMN, or BOX
    private int index;            // Which row/column/box (0-8)
    private int value;            // The duplicate number (1-9)
    private List<int[]> positions; // Where it appears
    
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
    
    public String toString() {
        String result = type + " " + (index + 1) + ", #" + value + ", ";
        
        for (int i = 0; i < positions.size(); i++) {
            int[] pos = positions.get(i);
            result += "[" + pos[0] + "," + pos[1] + "]";
            if (i < positions.size() - 1) {
                result += ", ";
            }
        }
        
        return result;
    }
}