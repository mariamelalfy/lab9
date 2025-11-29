package verifiers;

import models.ValidationResult;

public class SequentialVerifier extends VerifierStrategy {
    
    public SequentialVerifier(int[][] board) {
        super(board);
    }
    
    @Override
    public ValidationResult verify() {
        
        validateRows();
        validateColumns();
        validateBoxes();
        
        return result;
    }
    
    @Override
    public String toString() {
        return "SequentialVerifier (Mode 0 - Single Thread)";
    }
}
