package verifiers;

import models.*;

public class ColumnWorker implements Runnable {
    private int[][] board;
    private ValidationResult result;
    
    public ColumnWorker(int[][] board, ValidationResult result) {
        this.board = board;
        this.result = result;
    }
    
    @Override
    public void run() {
        // check all 9 columns
        for (int i = 0; i < 9; i++) {
            ValidationWorker worker = new ValidationWorker(
                ValidationType.COLUMN, i, board, result
            );
            worker.run();// you call run() directly, not in a new thread
        }
    }
}