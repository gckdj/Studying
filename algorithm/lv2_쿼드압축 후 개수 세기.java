import java.util.*;

class Solution {
    int[][] data = null;
    int zeroCnt = 0;
    int oneCnt = 0;
    
    public int[] solution(int[][] arr) {
        data = arr;
        
        Boolean isOneValue = true;
        int standard = data[0][0];
        for (int[] d : data) {
            for (int n : d) {
                if (n != standard) {
                    isOneValue = false;
                }
            }
        }
        
        if (isOneValue) {
            if (standard == 0) {
                zeroCnt += 1;
            } else {
                oneCnt += 1;
            }
        } else {
            quadCompress(0, 0, data.length / 2);

        }
        
        return new int[] { zeroCnt, oneCnt };
    }
    
    public void quadCompress(int r, int c, int range) {
        if (range >= 1) {
        
            int[] cols = { 0, range, 0,     range };
            int[] rows = { 0, 0,     range, range };
            
            
            for (int count = 0; count < 4; count++) {
                int row = r + rows[count];
                int col = c + cols[count]; 

                int standard = data[row][col];
                Boolean flag = true;

                for (int i = 0; i < range; i++) {
                    for (int j = 0; j < range; j++) {
                        if (standard != data[row + i][col + j]) {
                            flag = false;
                        }
                    }
                }
                
                if (!flag) {
                    if (range == 1) {
                        for (int i = 0; i < range; i++) {
                            for (int j = 0; j < range; j++) {
                                int value = data[row + i][col + j];
                                if (value == 0) {
                                    zeroCnt += 1;
                                } else {
                                    oneCnt += 1;
                                }
                            }
                        }
                    } else {
                        quadCompress(row, col, range / 2);
                    }
                } else {
                    if (standard == 0) {
                        zeroCnt += 1;
                    } else {
                        oneCnt += 1;
                    }
                }
            }
        }
    }
}