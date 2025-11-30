
import utils.CSVReader;
import utils.OutputFormatter;
import factory.VerifierFactory;
import verifiers.VerifierStrategy;
import models.ValidationResult;

public class Main {
    
    public static void main(String[] args) {
    // for testing
    if (args.length == 0) {
        args = new String[]{"board.csv", "0"};
        System.out.println("No arguments provided. Using defaults: board.csv, mode 0\n");
    }
    
    // check command line arguments
    if (args.length != 2) {
        printUsage();
        System.exit(1);
    }
    
    String filepath = args[0];
    String modeStr = args[1];
    
    try {
        // parse mode
        int mode = parseMode(modeStr);
        
        // read the board from CSV
        int[][] board = CSVReader.readBoard(filepath);
        
        // create appropriate verifier using Factory pattern
        VerifierStrategy verifier = VerifierFactory.create(mode, board);
        
        // verify the board
        ValidationResult result = verifier.verify();
        
        // format and print output
        String output = OutputFormatter.format(result);
        System.out.println(output);
        
    } catch (NumberFormatException e) {
        System.err.println("Error: Mode must be 0, 3, or 27");
        printUsage();
        System.exit(1);
    } catch (IllegalArgumentException e) {
        System.err.println("Error: " + e.getMessage());
        printUsage();
        System.exit(1);
    } catch (Exception e) {
        System.err.println("Error: " + e.getMessage());
        e.printStackTrace();
        System.exit(1);
    }
}
    
    private static int parseMode(String modeStr) {
        int mode = Integer.parseInt(modeStr);
        VerifierFactory factory = VerifierFactory.getInstance();
        if (!factory.isModeSupported(mode)) {
            throw new IllegalArgumentException("Mode must be 0, 3, or 27");
        }
        
        return mode;
    }
    
    private static void printUsage() {
        System.out.println("\nUsage: java -jar sudoku-verifier.jar <csv-filepath> <mode>");
        System.out.println("\nArguments:");
        System.out.println("  <csv-filepath>  Path to the CSV file containing 9x9 Sudoku board");
        System.out.println("  <mode>          Verification mode:");
        System.out.println("                    0  - Sequential (single thread)");
        System.out.println("                    3  - Three threads (one per type)");
        System.out.println("                    27 - Twenty-seven threads (one per row/col/box)");
        System.out.println("\nExample:");
        System.out.println("  java -jar sudoku-verifier.jar board.csv 0");
    }
}
