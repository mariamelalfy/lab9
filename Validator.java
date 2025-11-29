package utils;

import models.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Validator {

    public static ValidationResult validate(int[][] board) {
        ValidationResult result = new ValidationResult();

        // Validate rows
        for (int row = 0; row < 9; row++) {
            Map<Integer, List<int[]>> map = new HashMap<>();
            for (int col = 0; col < 9; col++) {
                int val = board[row][col];
                map.putIfAbsent(val, new ArrayList<>());
                map.get(val).add(new int[]{row, col});
            }
            for (Map.Entry<Integer, List<int[]>> entry : map.entrySet()) {
                if (entry.getValue().size() > 1) {
                    result.addDuplicate(new Duplicate(ValidationType.ROW, row, entry.getKey(), entry.getValue()));
                }
            }
        }

        // Validate columns
        for (int col = 0; col < 9; col++) {
            Map<Integer, List<int[]>> map = new HashMap<>();
            for (int row = 0; row < 9; row++) {
                int val = board[row][col];
                map.putIfAbsent(val, new ArrayList<>());
                map.get(val).add(new int[]{row, col});
            }
            for (Map.Entry<Integer, List<int[]>> entry : map.entrySet()) {
                if (entry.getValue().size() > 1) {
                    result.addDuplicate(new Duplicate(ValidationType.COLUMN, col, entry.getKey(), entry.getValue()));
                }
            }
        }

        // Validate 3x3 boxes
        for (int box = 0; box < 9; box++) {
            int startRow = (box / 3) * 3;
            int startCol = (box % 3) * 3;
            Map<Integer, List<int[]>> map = new HashMap<>();

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    int row = startRow + i;
                    int col = startCol + j;
                    int val = board[row][col];
                    map.putIfAbsent(val, new ArrayList<>());
                    map.get(val).add(new int[]{row, col});
                }
            }

            for (Map.Entry<Integer, List<int[]>> entry : map.entrySet()) {
                if (entry.getValue().size() > 1) {
                    result.addDuplicate(new Duplicate(ValidationType.BOX, box, entry.getKey(), entry.getValue()));
                }
            }
        }

        return result;
    }
}
