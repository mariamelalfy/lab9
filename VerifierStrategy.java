package verifiers;

import models.Duplicate;
import models.ValidationResult;
import models.ValidationType;

import java.util.*;

public abstract class VerifierStrategy {
    
    protected final int[][] board;
    protected final ValidationResult result;
    
    public VerifierStrategy(int[][] board) {
        if (board == null || board.length != 9 || board[0].length != 9) {
            throw new IllegalArgumentException("Board must be 9x9");
        }
        this.board = board;
        this.result = new ValidationResult();
    }
    
    public abstract ValidationResult verify();
    
    protected void validateRows() {
        for (int row = 0; row < 9; row++) {
            // track all positions for each value
            Map<Integer, List<int[]>> valuePositions = new HashMap<>();
            
            // collect all positions
            for (int col = 0; col < 9; col++) {
                int val = board[row][col];
                valuePositions.putIfAbsent(val, new ArrayList<>());
                valuePositions.get(val).add(new int[]{row, col});
            }
            
            // add duplicates
            for (Map.Entry<Integer, List<int[]>> entry : valuePositions.entrySet()) {
                if (entry.getValue().size() > 1) {
                    result.addDuplicate(new Duplicate(
                        ValidationType.ROW,
                        row,
                        entry.getKey(),
                        entry.getValue()
                    ));
                }
            }
        }
    }
    
    protected void validateColumns() {
        for (int col = 0; col < 9; col++) {
            Map<Integer, List<int[]>> valuePositions = new HashMap<>();
            
            for (int row = 0; row < 9; row++) {
                int val = board[row][col];
                valuePositions.putIfAbsent(val, new ArrayList<>());
                valuePositions.get(val).add(new int[]{row, col});
            }
            
            for (Map.Entry<Integer, List<int[]>> entry : valuePositions.entrySet()) {
                if (entry.getValue().size() > 1) {
                    result.addDuplicate(new Duplicate(
                        ValidationType.COLUMN,
                        col,
                        entry.getKey(),
                        entry.getValue()
                    ));
                }
            }
        }
    }
    
    protected void validateBoxes() {
        for (int box = 0; box < 9; box++) {
            int startRow = (box / 3) * 3;
            int startCol = (box % 3) * 3;
            Map<Integer, List<int[]>> valuePositions = new HashMap<>();
            
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    int row = startRow + i;
                    int col = startCol + j;
                    int val = board[row][col];
                    valuePositions.putIfAbsent(val, new ArrayList<>());
                    valuePositions.get(val).add(new int[]{row, col});
                }
            }
            
            for (Map.Entry<Integer, List<int[]>> entry : valuePositions.entrySet()) {
                if (entry.getValue().size() > 1) {
                    result.addDuplicate(new Duplicate(
                        ValidationType.BOX,
                        box,
                        entry.getKey(),
                        entry.getValue()
                    ));
                }
            }
        }
    }
    
    public ValidationResult getResult() {
        return result;
    }
}
