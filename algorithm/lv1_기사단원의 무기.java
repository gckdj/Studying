class Solution {
    public int solution(int number, int limit, int power) {
        int answer = 0;
        
        for (int i = 1; i < number + 1; i++) {
            int damage = 0;
            
            for (int j = 1; j * j < i + 1; j++) {
                if (j * j == i) {
                    damage += 1;
                } else if (i % j == 0) {
                    damage += 2;
                }
            }
            
            if (damage > limit) {
                damage = power;
            }
            
            answer += damage;
        }
        return answer;
    }
}