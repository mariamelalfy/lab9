package verifiers;

import models.*;

public class TwentySevenThreadVerifier extends VerifierStrategy {
    
    public TwentySevenThreadVerifier(int[][] board) {
        super(board);
    }

    @Override
    public ValidationResult verify() {
        Thread[] threads = new Thread[27];
        int t = 0;

        // 9 ROWS - i is 0-based (0-8)
        for (int i = 0; i < 9; i++) {
            threads[t] = new Thread(new ValidationWorker(
                    ValidationType.ROW, i, board, result));
            threads[t].start();
            t++;
        }

        // 9 COLUMNS - i is 0-based (0-8)
        for (int i = 0; i < 9; i++) {
            threads[t] = new Thread(new ValidationWorker(
                    ValidationType.COLUMN, i, board, result));
            threads[t].start();
            t++;
        }

        // 9 BOXES - i is 0-based (0-8)
        for (int i = 0; i < 9; i++) {
            threads[t] = new Thread(new ValidationWorker(
                    ValidationType.BOX, i, board, result));
            threads[t].start();
            t++;
        }

        // Wait for all threads
        for (int i = 0; i < 27; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}