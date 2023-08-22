import java.util.*;

class Solution {
    public String solution(String s, String skip, int index) {
        char[] chars = s.toCharArray();
        String answer = "";

        for (char c : chars) {
            int count = 0;
            int cursor = 1;
            int convertedIndex = 0;
            String convertedString = "";

            while (count < index) {
                int idx = (int) c + cursor;
                // 소문자 z를 넘어갈 때 a로 돌아오게끔
                convertedIndex = ((idx - 97) % 26) + 97;
                convertedString = String.valueOf((char) convertedIndex);
                if (skip.contains(convertedString)) {
                    cursor++;
                    continue;
                }
                count++;
                cursor++;
            }

            answer += convertedString;
        }

        return answer;
    }
}