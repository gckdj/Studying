import java.util.*;

class Solution {
    public int[] solution(String[][] places) {
        
        int[] answer = new int[places.length];
        int count = 0;
        
        for (String[] place : places) {
            char[][] cp = new char[5][5];
            
            for (int i = 0; i < 5; i++) {
                cp[i] = place[i].toCharArray();
            }
            
            List<int[]> p_pos = new ArrayList<>();
            
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (cp[i][j] == 'P') {
                        p_pos.add(new int[] { i, j });
                    }
                }
            }
            
            boolean valid = true;

            for (int i = 0; i < p_pos.size(); i++) {
                for (int j = i + 1; j < p_pos.size(); j++) {
                    int y = Math.abs(p_pos.get(i)[0] - p_pos.get(j)[0]);
                    int x = Math.abs(p_pos.get(i)[1] - p_pos.get(j)[1]);
                    
                    if (x + y <= 2) {
                        if (!isBlock(p_pos.get(i), p_pos.get(j), cp)) {
                            valid = false;
                        }
                    }
                }
            }
            
            if (valid) {
                answer[count] = 1;
            } else {
                answer[count] = 0;
            }
            
            count++;
        }
        return answer;
    }
    
    private boolean isBlock(int[] a, int[] b, char[][] cp) {
        boolean valid = false;
        
        if (a[0] == b[0]) {
            // 수평
            if (a[1] > b[1]) {
                if (cp[b[0]][b[1] + 1] == 'X') {
                    valid = true;
                }
            } else {
                if (cp[a[0]][a[1] + 1] == 'X') {
                    valid = true;
                }
            }
        } else if (a[1] == b[1]) {
            // 수직
            if (a[0] > b[0]) {
                if (cp[b[0] + 1][b[1]] == 'X') {
                    valid = true;
                }
            } else {
                if (cp[a[0] + 1][b[1]] == 'X') {
                    valid = true;
                }
            }
        } else {
            int minX = 0;
            int minY = 0;
            
            if (a[0] > b[0]) {
                minY = b[0];
            } else {
                minY = a[0];
            }
            
            if (a[1] > b[1]) {
                minX = b[1]; 
            } else {
                minX = a[1];
            }
            
            if (cp[minY][minX] == 'O' || cp[minY + 1][minX] == 'O' ||
               cp[minY][minX + 1] == 'O' || cp[minY + 1][minX + 1] == 'O') {
                valid = false;
            } else {
                valid = true;
            }
        }
        
        if (valid == false) {
            System.out.println(Arrays.toString(a) + ", " + Arrays.toString(b));
        }
        
        return valid;
    }
}
