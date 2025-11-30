package verifiers;

import models.*;

public class BoxWorker implements Runnable {
    private int[][] board;
    private ValidationResult result;
    
    public BoxWorker(int[][] board, ValidationResult result) {
        this.board = board;
        this.result = result;
    }
    
    @Override
    public void run() {
        // checks all 9 boxes
        for (int i = 0; i < 9; i++) {
            ValidationWorker worker = new ValidationWorker(
                ValidationType.BOX, i, board, result
            );
            worker.run();// you call run() directly, not in a new thread
        }
    }
}