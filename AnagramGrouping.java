import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnagramGrouping {
    public static void main(String[] args) {
        String[] input = {
            "eat", "tea", "tan", "ate", "nat", "bat"
        };

        Map<String, List<String>> groupedAnagrams = groupAnagrams(input);

        for (Map.Entry<String, List<String>> entry : groupedAnagrams.entrySet()) {
            System.out.println(entry.getValue());
        }
    }

    public static Map<String, List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> anagramMap = new HashMap<>();

        for (String str : strs) {
            char[] charArray = str.toCharArray();
            java.util.Arrays.sort(charArray);
            String sortedString = new String(charArray);

            anagramMap.putIfAbsent(sortedString, new ArrayList<>());
            anagramMap.get(sortedString).add(str);
        }

        return anagramMap;
    }
}
