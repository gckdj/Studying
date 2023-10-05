import java.util.*;

class Solution {
    public int[] solution(int rows, int columns, int[][] queries) {
        int[] answer = new int[queries.length];
    
        int[][] field = new int[rows][columns];
        int count = 1;
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                field[i][j] = count;
                count++;
            }
        }
        
        int k = 0;
        
        for (int[] arr : queries) {
            int temp = 0;
            
            int[] p1 = { arr[0] - 1, arr[1] - 1 };
            int[] p2 = { arr[0] - 1, arr[3] - 1 };
            int[] p3 = { arr[2] - 1, arr[3] - 1 };
            int[] p4 = { arr[2] - 1, arr[1] - 1 };
            
            int lastVal = field[p1[0] + 1][p1[1]];
            int minVal = lastVal;

            for (int i = p1[1]; i < p2[1]; i++) {
                temp = field[p1[0]][i];
                field[p1[0]][i] = lastVal;
                lastVal = temp;
                minVal = Math.min(minVal, temp);
            }
            
            for (int i = p2[0]; i < p3[0]; i++) {
                temp = field[i][p2[1]];
                field[i][p2[1]] = lastVal;
                lastVal = temp;
                minVal = Math.min(minVal, temp);
            }
            
            for (int i = p3[1]; p4[1] < i; i--) {
                temp = field[p3[0]][i];
                field[p3[0]][i] = lastVal;
                lastVal = temp;
                minVal = Math.min(minVal, temp);
            }
            
            for (int i = p4[0]; p1[0] < i; i--) {
                temp = field[i][p4[1]];
                field[i][p4[1]] = lastVal;
                lastVal = temp;
                minVal = Math.min(minVal, temp);
            }
            
            answer[k] = minVal;
            k++;
        }
        
        return answer;
    }
}