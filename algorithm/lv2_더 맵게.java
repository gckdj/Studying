import java.util.*;
import java.util.stream.*;

class Solution {
    public int solution(int[] scoville, int K) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();

        for (int s : scoville) {
            pq.add(s);
        }

        int count = 0;

        while (true) {
            // 모든 음식의 스코빌 지수를 K 이상으로 만들 수 없는 경우
            if (pq.size() == 1 && pq.peek() < K) {
                return -1;
                // 모든 음식의 스코빌 지수가 K 이상이 된 경우
            } else if (pq.peek() >= K) {
                return count;
                // 모든 음식의 스코빌 지수가 K 이상이 아니라서 처리가 필요한 경우
            } else if (pq.size() > 1) {
                int a = pq.poll();
                int b = pq.poll();
                int c = a + (b * 2);
                pq.add(c);
                count++;
            }
        }
    }
    
    /*public int solution(int[] scoville, int K) {
        int answer = 0;
        Arrays.sort(scoville);
        List<Integer> list = new ArrayList<>();
        
        for (int i = 0; i < scoville.length; i++) {
            list.add(scoville[i]);
        }
        
        boolean flag = false;
        int count = 0;
        
        while (!flag) {
            int first = list.get(0);
            int second = list.get(1);
            int res_sco = first + (second * 2);
            
            list.remove(0);
            list.remove(0);
            
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) > res_sco) {
                    list.add(i, res_sco);
                    break;
                }
            }
            
            count++;
            
            if (list.get(0) >= K) {
                flag = true;
            } else if (list.size() == 1 && list.get(0) < K) {
                flag = true;
                return -1;
            }
        }
        
        return count;
    }*/
}