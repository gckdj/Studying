import java.util.*;

class Solution {
    public int solution(int[] nums) {
        int answer = 0;
        // 최대 조합 수: 배열사이즈의 반
        int r_max = nums.length / 2;

        Set<Integer> set = new HashSet<>();
        // 중복없이 모든 값 추가
        for (int num : nums) {
            set.add(num);
        }

        // 최대 조합 수보다 중복제거된 리스트 사이즈가 작다면 그것이 최대 경우의 수
        if (r_max >= set.size()) {
            answer = set.size();
        } else {
            answer = r_max;
        }

        return answer;
    }
}