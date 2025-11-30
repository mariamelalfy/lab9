package verifiers;

import models.*;

public class RowWorker implements Runnable {
    private int[][] board;
    private ValidationResult result;
    
    public RowWorker(int[][] board, ValidationResult result) {
        this.board = board;
        this.result = result;
    }
    
    @Override
    public void run() {
        // Check all 9 rows
        for (int i = 0; i < 9; i++) {
            ValidationWorker worker = new ValidationWorker(
                ValidationType.ROW, i, board, result
            );
            worker.run(); // you call run() directly, not in a new thread
        }
    }
}