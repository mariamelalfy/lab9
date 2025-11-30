package verifiers;

import models.*;

public class SequentialVerifier extends VerifierStrategy {
    
    public SequentialVerifier(int[][] board) {
        super(board);
    }
    
    @Override
    public ValidationResult verify() {
        // Validate all rows
        for (int i = 0; i < 9; i++) {
            ValidationWorker worker = new ValidationWorker(
                ValidationType.ROW, i, board, result
            );
            worker.run();
        }
        
        // Validate all columns
        for (int i = 0; i < 9; i++) {
            ValidationWorker worker = new ValidationWorker(
                ValidationType.COLUMN, i, board, result
            );
            worker.run();
        }
        
        // Validate all boxes
        for (int i = 0; i < 9; i++) {
            ValidationWorker worker = new ValidationWorker(
                ValidationType.BOX, i, board, result
            );
            worker.run();
        }
        
        return result;
    }
    
    @Override
    public String toString() {
        return "SequentialVerifier (Mode 0 - Single Thread)";
    }
}
