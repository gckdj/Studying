import java.util.*;

// https://school.programmers.co.kr/learn/courses/30/lessons/68645
class Solution {
    public int[] solution(int n) {
        int[][] arr = new int[n][n];
        int nu = 1;
        int d = 0;
        int w = 0;
        int mn = 0;
        int turnPoint = n;
        int turnCount = 1;
        String[] modes = { "down", "right", "up" };
        int endPoint = 0;
        
        for (int q = 1; q < n + 1; q++) {
            endPoint += q;
        }
        
        while (true) {            
            if (modes[mn].equals("down")) {
                arr[d][w] = nu;
                d += 1;
            } else if (modes[mn].equals("right")) {
                arr[d][w] = nu;
                w += 1;
            } else if (modes[mn].equals("up")) {
                arr[d][w] = nu;
                w -= 1;
                d -= 1;
            }
            
            nu += 1;
            
            if (nu == turnPoint) {
                turnPoint += n - turnCount;
                turnCount += 1;
                mn += 1;
                
                if (mn == 3) {
                    mn = 0;
                }
            }
            
            if (nu == endPoint + 1) {
                break;
            }
        }
        
        int[] answer = new int[endPoint];
        int answerCount = 0;

        for (int[] row : arr) {
            for (int i : row) {
                if (i != 0) {
                    answer[answerCount] = i;
                    answerCount += 1;
                }
            }
        }
                
        return answer;
    }
}
