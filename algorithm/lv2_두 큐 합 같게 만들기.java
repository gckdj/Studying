import java.util.*;

class Solution {
    public long solution(int[] queue1, int[] queue2) {
        Queue<Long> q1 = new LinkedList<>();
        Queue<Long> q2 = new LinkedList<>();
        
        long sum_q1 = 0;
        long sum_q2 = 0;
        
        for (int i : queue1) {
            sum_q1 += (long) i;
            q1.add((long) i);
        }
        
        for (int j : queue2) {
            sum_q2 += (long) j;
            q2.add((long) j);
        }
        
        long goal = (sum_q1 + sum_q2) / 2;        
        long count = 0;
        
        while (true) {
            if (sum_q1 == sum_q2 && sum_q1 == goal) {
                break;
            }
            
            count += 1;
            
            if (sum_q1 > sum_q2) {                
                long poll = q1.remove();
                sum_q1 -= poll;
                
                q2.add(poll);
                sum_q2 += poll;
            } else if (q1.size() == 0 || q2.size() == 0) {
                return -1;
            } else {                
                long poll = q2.remove();
                sum_q2 -= poll;
                
                q1.add(poll);
                sum_q1 += poll;
            }
                       
            if (count > q1.size() * 5 + q2.size() * 5) {
                return -1;
            }
        }
        
        return count;
    }
}