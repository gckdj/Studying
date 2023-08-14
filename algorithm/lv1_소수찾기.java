import java.util.*;

class Solution {
    public int solution(int n) {
        int answer = 0;
        boolean[] primes = new boolean[n + 1];
        Arrays.fill(primes, true);

        for (int i = 0; i < n + 1; i++) {
            if (i == 0 || i == 1) {
                primes[i] = false;
                continue;
            }

            // 각 수의 배수들은 소수여부 false로 체크, 2의 배수 or 3의 배수 or 5의 배수 or 7의 배수..
            for (int j = i * 2; j < n + 1; j += i) {
                primes[j] = false;
            }
        }

        for (boolean p : primes) {
            if (p) {
                answer++;
            }
        }

        // System.out.println(Arrays.toString(primes));

        return answer;
    }
}