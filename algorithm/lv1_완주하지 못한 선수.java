import java.util.*;

class Solution {
    public String solution(String[] participant, String[] completion) {
        String answer = "";
        
        Map<String, Integer> result = new HashMap<>();
        for (String p : participant) {
            if (result.get(p) != null) {
                int count = result.get(p) + 1;
                result.put(p, count);
            } else {
                result.put(p, 1);
            }
        }
        
        for (String c : completion) {
            int count = result.get(c) - 1;
            result.put(c, count);
        }
        
        for (String key : result.keySet()) {
            if (result.get(key) > 0) {
                answer = key;
            }
        }
                        
        return answer;
    }
}