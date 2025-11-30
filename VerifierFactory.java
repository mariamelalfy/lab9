package factory;

import verifiers.VerifierStrategy;
import verifiers.SequentialVerifier;
import verifiers.ThreeThreadVerifier;
import verifiers.TwentySevenThreadVerifier;

public class VerifierFactory {
    // the one and only instance of this factory (Singleton pattern)
    private static VerifierFactory singleInstance = null;
    
    private VerifierFactory() {
    }
    
    public static VerifierFactory getInstance() {
        // if we haven't created the factory yet, create it now
        if (singleInstance == null) {
            // synchronized to make this thread-safe
            synchronized (VerifierFactory.class) {
                // double-check in case another thread just created it
                if (singleInstance == null) {
                    singleInstance = new VerifierFactory();
                }
            }
        }
        return singleInstance;
    }
    
    public VerifierStrategy createVerifier(int mode, int[][] board) {
        // validate board dimensions
        if (board == null || board.length != 9 || board[0].length != 9) {
            throw new IllegalArgumentException("Board must be 9x9");
        }
        
        // create verifier based on the mode
        switch (mode) {
            case 0:
                // mode 0: Sequential verification (single thread)
                return new SequentialVerifier(board);
                
            case 3:
                // mode 3: Three threads (one for rows, one for columns, one for boxes)
                return new ThreeThreadVerifier(board);
                
            case 27:
                // mode 27: Twenty-seven threads (one for each row/column/box)
                return new TwentySevenThreadVerifier(board);
                
            default:
                // invalid mode 
                throw new IllegalArgumentException(
                    "Invalid mode: " + mode + ". " +
                    "Valid modes are 0 (sequential), 3 (three threads), or 27 (twenty-seven threads)."
                );
        }
    }
    
    public static VerifierStrategy create(int mode, int[][] board) {
        return getInstance().createVerifier(mode, board);
    }
    
    public boolean isModeSupported(int mode) {
        switch (mode) {
            case 0:
            case 3:
            case 27:
                return true;
            default:
                return false;
        }
    }
    
    public static String getModeDescription(int mode) {
        switch (mode) {
            case 0:
                return "Mode 0: Sequential verification (one thread, checks step by step)";
            case 3:
                return "Mode 3: Three threads (one thread for rows, one for columns, one for boxes)";
            case 27:
                return "Mode 27: Twenty-seven threads (one thread for each individual row/column/box)";
            default:
                return "Unknown mode";
        }
    }
    
    public static int[] getSupportedModes() {
        return new int[]{0, 3, 27};
    }
}
