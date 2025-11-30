package verifiers;

import models.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ValidationWorker implements Runnable {

    private ValidationType type;
    private int index;             
    private int[][] board;
    private ValidationResult result;

    public ValidationWorker(ValidationType type, int index,
                            int[][] board, ValidationResult result) {
        this.type = type;
        this.index = index;
        this.board = board;
        this.result = result;
    }

    @Override
    public void run() {
        if (type == ValidationType.ROW) checkRow();
        else if (type == ValidationType.COLUMN) checkColumn();
        else checkBox();
    }

    private void checkRow() {
        int[] row = board[index];
        checkDuplicates(row, ValidationType.ROW);
    }

    private void checkColumn() {
        int[] col = new int[9];
        for (int i = 0; i < 9; i++) {
            col[i] = board[i][index];
        }
        checkDuplicates(col, ValidationType.COLUMN);
    }

    private void checkBox() {
        int[] box = new int[9];
        int sr = (index / 3) * 3;
        int sc = (index % 3) * 3;
        int pos = 0;

        for (int r = sr; r < sr + 3; r++) {
            for (int c = sc; c < sc + 3; c++) {
                box[pos++] = board[r][c];
            }
        }
        checkDuplicates(box, ValidationType.BOX);
    }

    private void checkDuplicates(int[] arr, ValidationType type) {
        Map<Integer, List<Integer>> map = DuplicateHelper.findDuplicates(arr);

        for (int value : map.keySet()) {
            List<Integer> positions = map.get(value);
            List<int[]> convertedPositions = convertPositions(positions, type);
            
            
            result.addDuplicate(new Duplicate(type, index, value, convertedPositions)); 
        }
    }
    
    private List<int[]> convertPositions(List<Integer> positions, ValidationType type) {
        List<int[]> result = new ArrayList<>();
        
        for (int pos : positions) {  
            int[] coordinate = new int[2];
            
            if (type == ValidationType.ROW) {
              
                coordinate[0] = index + 1;  
                coordinate[1] = pos;       
                
            } else if (type == ValidationType.COLUMN) {
               
                coordinate[0] = pos;       
                coordinate[1] = index + 1; 
                
            } else {  
                
                int boxStartRow = (index / 3) * 3;  
                int boxStartCol = (index % 3) * 3; 
                
               
                int offset = pos - 1;
                int rowOffset = offset / 3; 
                int colOffset = offset % 3; 
                
                coordinate[0] = boxStartRow + rowOffset + 1;  // 1 based row
                coordinate[1] = boxStartCol + colOffset + 1;  // 1 based col
            }
            
            result.add(coordinate);
        }
        
        return result;
    }
}
