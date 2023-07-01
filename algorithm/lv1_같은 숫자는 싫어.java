import java.util.*;

public class Solution {
    public int[] solution(int [] arr) {
        List<Integer> list = new ArrayList<>();
        int check = -1;
        for (int n : arr) {
            if (check != n) {
                list.add(n);
            }
            check = n;
        }        
        return list.stream().mapToInt(i -> i).toArray();
    }
}