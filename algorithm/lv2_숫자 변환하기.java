import java.util.*;

class Solution {
    public int solution(int x, int y, int n) {
    	// set으로 선언해서 같은 값들이 중복생성 되는 것 방지 -> 경우의 수 낮추기
        HashSet<Integer> set = new HashSet<>();
        
        // 초기값 대입
        set.add(x + n);
        set.add(x * 2);
        set.add(x * 3);
        
        int count = 1;
        
        // x와 y가 같은 경우는 count = 0
        if (x == y) {
            return 0;
        }
        
        while (set.size() > 0) {            
            if (set.contains(y)) {
                return count;
            }
            
            HashSet<Integer> newSet = new HashSet<>();
            
            for (int i : set) {
                if (i + n <= y) {
                    newSet.add(i + n);
                }

                if (i * 2 <= y) {
                    newSet.add(i * 2);
                }

                if (i * 3 <= y) {
                    newSet.add(i * 3);
                }
            }
            
            // 기존 set은 버리고 새로운 set 값으로 대체
            set = newSet;
            count++;
        }
        
        return -1;
    }
}