// 2018 KAKAO BLIND RECRUITMENT [1차] 다트 게임
// https://school.programmers.co.kr/learn/courses/30/lessons/17682
import java.util.*;

class Solution {
    public int solution(String dartResult) {
        int answer = 0;
        int before = 0;
        int temp = 0;
        
        for (int i = 0; i < dartResult.length(); i++) {
            char c = dartResult.charAt(i);
            
            if (c == '*') {
                before *= 2;
                temp *= 2;
            } else if (c == '#') {
                temp *= -1;
            } else if (c == 'S') {
                temp = temp;
            } else if (c == 'D') {
                temp = (int) Math.pow(temp, 2);
            } else if (c == 'T') {
                temp = (int) Math.pow(temp, 3);
            } else {
                answer += before;
                before = temp;
                
                if (c == '1' && i + 1 < dartResult.length() && dartResult.charAt(i + 1) == '0') {
                    i++;
                    temp = 10;
                } else {
                    temp = Character.getNumericValue(c);
                }
            }
        }
        
        return answer + before + temp;
    }
}