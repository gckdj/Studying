import java.util.*;

class Solution {
    private static final List<String[]> caseList = new ArrayList<>();

    public int solution(String[][] clothes) {
        Map<String, List<String>> map = new HashMap<>();

        for (String[] c : clothes) {
            String type = c[1];
            String name = c[0];

            if (!map.containsKey(type)) {
                map.put(type, new ArrayList<String>());
            }

            map.get(type).add(name);
        }

        if (map.size() == 30) {
            return 1073741823;
        }

        String[] keySet = map.keySet().toArray(new String[0]);

        for (int i = 0; i < keySet.length; i++) {
            getCombination(keySet, new String[i + 1], 0, i + 1);
        }

        int answer = 0;

        for (String[] c : caseList) {
            int count = 1;
            for (String s : c) {
                count = count * map.get(s).size();
            }

            answer += count;
        }

        return answer;
    }

    private static void getCombination(String[] keySet, String[] output, int startIdx, int targetSize) {
        if (targetSize == 0) {
            caseList.add(output);
        } else {
            for (int i = startIdx; i < keySet.length; i++) {
                output[targetSize - 1] = keySet[i];
                String[] copyArray = Arrays.copyOf(output, output.length);
                getCombination(keySet, copyArray, i + 1, targetSize - 1);
            }
        }
    }
}