import java.util.*;

class Solution {
    public int solution(int[][] board, int[] moves) {
        int answer = 0;
        List<Integer> numbers = new ArrayList<>();
        
        for (int m : moves) {
            for (int i = 0; i < board.length; i++) {
                int drill = board[i][m - 1];
                
                if (drill != 0) {
                    board[i][m - 1] = 0;
                    numbers.add(drill);
                    
                    while (true) {
                        if (numbers.size() < 2) {
                            break;
                        } 
                        
                        int lastNum = numbers.get(numbers.size() - 1);
                        int lastPrevNum = numbers.get(numbers.size() - 2);
                        
                        if (lastNum == lastPrevNum) {
                            numbers.remove(numbers.size() - 1);
                            numbers.remove(numbers.size() - 1);
                            
                            answer += 2;
                        } else {
                            break;
                        }
                    }
                    
                    break;
                }
            }
        }
                
        return answer;
    }
}