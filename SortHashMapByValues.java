import java.util.*;

public class SortHashMapByValues {
    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("Apple", 3);
        map.put("Banana", 1);
        map.put("Cherry", 2);
        map.put("Date", 5);
        map.put("Elderberry", 4);

        System.out.println("Original HashMap: " + map);

        LinkedHashMap<String, Integer> sortedMap = sortByValues(map);

        System.out.println("Sorted HashMap by Values: " + sortedMap);
    }

    public static LinkedHashMap<String, Integer> sortByValues(HashMap<String, Integer> map) {
        List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> entry1, Map.Entry<String, Integer> entry2) {
                return entry1.getValue().compareTo(entry2.getValue());
            }
        });

        LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }
}
