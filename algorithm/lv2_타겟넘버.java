class Solution {
    public static int answer = 0;

    public int solution(int[] numbers, int target) {
        recur(numbers, 0, 0, target);
        return answer;
    }

    // DFS -> 재귀
    public void recur(int[] numbers, int count, int r, int target) {
        // count: 탐색깊이 체크
        if (numbers.length > count) {
            int r_1 = r + numbers[count];
            int r_2 = r - numbers[count];
            count++;
            // 탐색이 완료되지 않았기 때문에 재귀실행
            recur(numbers, count, r_1, target);
            recur(numbers, count, r_2, target);
        } else {
            // 탐색이 완료되면 타겟과 비교하고 전역변수 += 1
            if (r == target) {
                answer += 1;
            }
        }
    }
}
