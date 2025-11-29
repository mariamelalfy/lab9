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
            
            
            result.addDuplicate(new Duplicate(type, index, value, convertedPositions)); // index + 1 converts 0-based to 1-based for display
        }
    }
    
    private List<int[]> convertPositions(List<Integer> positions, ValidationType type) {
        List<int[]> result = new ArrayList<>();
        
        for (int pos : positions) {  // pos is 1 based (1-9) from DuplicateHelper
            int[] coordinate = new int[2];
            
            if (type == ValidationType.ROW) {
                // ROW: row number is index+1, column is pos
                coordinate[0] = index + 1;  // row (1 based)
                coordinate[1] = pos;        // col (already 1 based)
                
            } else if (type == ValidationType.COLUMN) {
                // COLUMN: row is pos, column number is index+1
                coordinate[0] = pos;        // row (already 1 based)
                coordinate[1] = index + 1;  // col (1 based)
                
            } else {  // BOX
                // to convert box index (0-8) and position (1-9) to [row, col]
                int boxStartRow = (index / 3) * 3;  // 0, 3, or 6
                int boxStartCol = (index % 3) * 3;  // 0, 3, or 6
                
                // pos is 1-9, convert to 0-8 for offset calculation
                int offset = pos - 1;
                int rowOffset = offset / 3;  // 0, 1, or 2
                int colOffset = offset % 3;  // 0, 1, or 2
                
                coordinate[0] = boxStartRow + rowOffset + 1;  // 1 based row
                coordinate[1] = boxStartCol + colOffset + 1;  // 1 based col
            }
            
            result.add(coordinate);
        }
        
        return result;
    }
}
