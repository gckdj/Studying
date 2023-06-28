import java.util.*;

class Solution {
    public int[] solution(int[] array, int[][] commands) {
        int[] answer = new int[commands.length];

        for (int i = 0; i < commands.length; i++) {
            int from = commands[i][0] - 1;
            int to = commands[i][1];
            int select = commands[i][2] - 1;

            int[] temp = new int[to - from];
            int count = 0;

            // 범위 내 값들만 임시배열에 추가
            for (int j = from; j < to; j++) {
                temp[count] = array[j];
                count++;
            }

            // 정렬 후 임시배열에서 선택된 값을 결과에 반영
            Arrays.sort(temp);
            answer[i] = temp[select];
        }

        return answer;
    }
}
