import java.util.stream.*;

class Solution {
    public int[] solution(int[] answers) {
        int[] answer = {};
        
        // 패턴 미리 입력해주기
        int[] first = new int[] { 1, 2, 3, 4, 5 };
        int[] secnd = new int[] { 2, 1, 2, 3, 2, 4, 2, 5 };
        int[] third = new int[] { 3, 3, 1, 1, 2, 2, 4, 4, 5, 5 };
        
        int fCnt = 0;
        int sCnt = 0;
        int tCnt = 0;
                
        for (int i = 0; i < answers.length; i++) {
            // 각 배열의 길이를 나눈 나머지 값 == 인덱스
            if (first[i % first.length] == answers[i]) fCnt++;
            if (secnd[i % secnd.length] == answers[i]) sCnt++;
            if (third[i % third.length] == answers[i]) tCnt++;
        }
        
        int[] result = new int[] { fCnt, sCnt, tCnt };
        
        int max = 0;
        
        for (int i : result) {
            if (i > max) {
                max = i;
            }
        }
        
        final int valid = max;
        
        return IntStream.range(0, 3)
                .filter(i -> result[i] == valid)
                .map(i -> i + 1)
                .toArray();
    }
}