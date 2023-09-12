import java.util.*;

class Solution {
    public String solution(String X, String Y) {
        // StringBuilder Or StringBuffer로 속도를 끌어올리자.
        StringBuilder sb = new StringBuilder();

        Map<Integer, Integer> x_map = getNumbersCount(X);
        Map<Integer, Integer> y_map = getNumbersCount(Y);

        for (int i = 9; i > -1; i--) {
            int x_val = x_map.getOrDefault(i, 0);
            int y_val = y_map.getOrDefault(i, 0);
            int count = 0;

            if (x_val != 0 && y_val != 0 && x_val > y_val) {
                count = y_val;
            } else if (x_val != 0 && y_val != 0 && x_val <= y_val) {
                count = x_val;
            } else {
                count = 0;
            }

            for (int j = 0; j < count; j++) {
                sb.append(String.valueOf(i));
            }
        }

        String answer = sb.toString();

        if (answer.isEmpty()) {
            answer = "-1";
        } else if (answer.charAt(0) ==  '0') {
            answer = "0";
        }

        return answer;
    }

    private static Map<Integer, Integer> getNumbersCount(String s) {
        Map<Integer, Integer> result = new HashMap<>();

        for (int i = 0; i < 10; i++) {
            int count = s.length() - s.replace(String.valueOf(i), "").length();
            result.put(i, count);
        }

        return result;
    }
}