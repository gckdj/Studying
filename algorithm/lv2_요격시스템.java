import java.util.*;

class Solution {
    public int solution(int[][] targets) {
    	// 종료시점 기준 배열정렬
        Arrays.sort(targets, new Comparator<int[]>() {
           public int compare(int[] s1, int[] s2) {
               if (s1[1] == s2[1]) {
                   return s2[0] - s1[0];
               } else {
                   return s1[1] - s2[1];
               }
           }
        });
        
        int start = 0;
        int end = 0;
        int answer = 0;
        
        for (int i = 0; i < targets.length; i++) {
            int r1 = targets[i][0];
            int r2 = targets[i][1];
            
            // 첫 탄환을 기준탄환으로 지정
            if (i == 0) {
                start = r1;
                end = r2;
                answer++;
                continue;
            }
            
            // 판별탄환 시작지점이 기준탄환 종료지점보다 뒷 지점일 경우 갱신하고 요격갯수 +1
            if (end <= r1) {
                start = r1;
                end = r2;
                answer++;
            }
        }
        return answer;
    }
}