package verifiers;

import models.*;

public class ThreeThreadVerifier extends VerifierStrategy {
    
    public ThreeThreadVerifier(int[][] board) {
        super(board);
    }
    
    @Override
    public ValidationResult verify() {
        // Create 3 workers (threads) One checks all rows, one checks all columns, one checks all boxes
        RowWorker rowWorker = new RowWorker(board, result);
        ColumnWorker colWorker = new ColumnWorker(board, result);
        BoxWorker boxWorker = new BoxWorker(board, result);
        
        // Create 3 threads
        Thread t1 = new Thread(rowWorker);
        Thread t2 = new Thread(colWorker);
        Thread t3 = new Thread(boxWorker);
        
        // start all 3 threads at the same time
        t1.start();
        t2.start();
        t3.start();
        
        // wait for all 3 to finish
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        return result;
    }
}