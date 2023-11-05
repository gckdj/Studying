import java.util.*;

class Solution {
    static Set<Integer> output = new HashSet<>();
    
    public int solution(String numbers) {
        int answer = 0;
        String[] nums = numbers.split("");
        
        for (int i = 1; i < numbers.length() + 1; i++) {
            perm(nums, new boolean[numbers.length()], 0, i, "");
        }
        
        for (Integer num : output) {
            // Set을 순회하며, 소수인 경우 답을 +1 해준다.
            if (isPrime(num)) {
                answer++;
            }
        }
        
        return answer;
    }
    
    // 주어진 카드로 만들 수 있는 모든 순열 추출한다.
    static void perm(String[] nums, boolean[] visited, int depth, int endPoint, String val) {
        if (depth == endPoint) {
            // HashSet에 저장함으로 숫자가 중복되지 않도록 한다.
            output.add(Integer.parseInt(val));
            return;
        }
        
        for (int i = 0; i < nums.length; i++) {
            if (!visited[i]) {
                visited[i] = true;
                perm(nums, visited, depth + 1, endPoint, val + nums[i]);
                visited[i] = false;
            }
        }
    }
    
    static boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        } 
        
        for (int i = 2; i <= (int) Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }
        
        return true;
    }
}