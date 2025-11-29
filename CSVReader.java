
package utils;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;

public class CSVReader {
    /**
     * Reads a 9x9 Sudoku board from a CSV file.
     * Each line should contain 9 comma-separated integers (1-9).
     */
    public static int[][] readBoard(String filepath) throws IOException {
        // Validate file exists first
        if (!isValidFile(filepath)) {
            throw new IOException("File does not exist or is not readable: " + filepath);
        }
        
        int[][] board = new int[9][9];
        
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {   //read whole line
            String line;
            int row = 0;
            
            while ((line = br.readLine()) != null && row < 9) {
                // Skip empty lines
                if (line.trim().isEmpty()) {
                    continue;
                }
                
                // Split by comma and remove whitespace
                String[] values = line.trim().split(",");
                
                // Validate that we have exactly 9 values
                if (values.length != 9) {
                    throw new IllegalArgumentException(
                        "Invalid CSV format: Row " + (row + 1) + 
                        " has " + values.length + " values, expected 9"
                    );
                }
                
                // Parse each value
                for (int col = 0; col < 9; col++) {
                    try {
                        board[row][col] = Integer.parseInt(values[col].trim());
                        
                        // Validate range (1-9)
                        if (board[row][col] < 1 || board[row][col] > 9) {
                            throw new IllegalArgumentException(
                                "Invalid value at row " + (row + 1) + 
                                ", col " + (col + 1) + ": " + board[row][col] +
                                ". Values must be between 1 and 9."
                            );
                        }
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException(
                            "Invalid number format at row " + (row + 1) + 
                            ", col " + (col + 1) + ": '" + values[col] + "'"
                        );
                    }
                }
                row++;
            }
            
            // Validate that we read exactly 9 rows
            if (row != 9) {
                throw new IllegalArgumentException(
                    "Invalid CSV format: Expected 9 rows, found " + row
                );
            }
        }
        
        return board;
    }
    
    public static boolean isValidFile(String filepath) {
        if (filepath == null || filepath.trim().isEmpty()) {
            return false;
        }
        
        File file = new File(filepath);
        return file.exists() && file.canRead() && file.isFile();
    }
}
