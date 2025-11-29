package verifiers;
import java.util.*;

public class DuplicateHelper {
    
    public static Map<Integer, List<Integer>> findDuplicates(int[] arr) {
        Map<Integer, List<Integer>> map = new HashMap<>();

        for (int i = 0; i < arr.length; i++) {
            map.putIfAbsent(arr[i], new ArrayList<>());
            map.get(arr[i]).add(i + 1); // position 1..9
        }

        map.entrySet().removeIf(e -> e.getValue().size() == 1);

        return map;
    }
    
    
}
