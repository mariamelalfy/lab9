package verifiers;
import models.*;
public class ThreeThreadVerifier extends VerifierStrategy {
    
    public ThreeThreadVerifier(int[][] board) {
        super(board);
    }
    
    @Override
    public ValidationResult verify() {
        // Create 3 threads - each validates all 9 of its type
        Thread rowThread = new Thread(() -> {
            for (int i = 0; i < 9; i++) {
                new ValidationWorker(ValidationType.ROW, i, board, result).run();
            }
        });
        
        Thread colThread = new Thread(() -> {
            for (int i = 0; i < 9; i++) {
                new ValidationWorker(ValidationType.COLUMN, i, board, result).run();
            }
        });
        
        Thread boxThread = new Thread(() -> {
            for (int i = 0; i < 9; i++) {
                new ValidationWorker(ValidationType.BOX, i, board, result).run();
            }
        });
        
        // Start all 3 threads
        rowThread.start();
        colThread.start();
        boxThread.start();
        
        // Wait for all to finish
        try {
            rowThread.join();
            colThread.join();
            boxThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        return result;
    }
}
