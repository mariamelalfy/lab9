package verifiers;

import models.ValidationResult;

public abstract class VerifierStrategy {
    
    protected final int[][] board;
    protected final ValidationResult result;
    
    public VerifierStrategy(int[][] board) {
        if (board == null || board.length != 9 || board[0].length != 9) {
            throw new IllegalArgumentException("Board must be 9x9");
        }
        this.board = board;
        this.result = new ValidationResult();
    }
    
    public abstract ValidationResult verify();
    
    public ValidationResult getResult() {
        return result;
    }
}
