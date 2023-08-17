import java.util.*;
    
class Solution {
    private long[] getCrsPositions(int[] a, int[] b) {
        double A = a[0], B = a[1], E = a[2], C = b[0], D = b[1], F = b[2];
        double x = 0;
        
        if (A * D - B * C == 0) {
            return null;
        }
        
        if (((B * F) - (E * D)) != 0 && ((A * D) - (B * C)) != 0) {
            x = ((B * F) - (E * D)) / ((A * D) - (B * C));
        }
        
        double y = 0;
        
        if (((E * C) - (A * F)) != 0 && ((A * D) - (B * C)) != 0) {
            y = ((E * C) - (A * F)) / ((A * D) - (B * C));
        }
        
        if (x % 1 != 0 || y % 1 != 0) {
            return null;
        }
        
        return new long[]{(long) x, (long) y};
    }
    
    private int[] getMaxPosition(List<long[]> positions) {
        long x = Long.MIN_VALUE;
        long y = Long.MIN_VALUE;
        
        for (long[] p : positions) {
            if (x < p[0]) x = p[0];
            if (y < p[1]) y = p[1];
        }
        
        return new int[]{(int) x, (int) y};
    }
    
    private int[] getMinPosition(List<long[]> positions) {
        long x = Long.MAX_VALUE;
        long y = Long.MAX_VALUE;
        
        for (long[] p : positions) {
            if (x > p[0]) x = p[0];
            if (y > p[1]) y = p[1];
        }
        
        return new int[]{(int) x, (int) y};
    }
    
    public String[] solution(int[][] line) {
        List<long[]> positions = new ArrayList<>();
        
        for (int i = 0; i < line.length; i++) {
            for (int j = i + 1; j < line.length; j++) {
                long[] cp = getCrsPositions(line[i], line[j]);
                
                if (cp != null) {
                    positions.add(cp);
                }
            }
        }
        
        int[] maximum = getMaxPosition(positions);
        int[] minimum = getMinPosition(positions);
        
        int width = maximum[0] - minimum[0] + 1;
        int height = maximum[1] - minimum[1] + 1;
                
        char[][] arr = new char[height][width];
        
        for (char[] row : arr) {
            Arrays.fill(row, '.');
        }
        
        for (long[] p : positions) {
            long x = p[0] - minimum[0];
            long y = maximum[1] - p[1];
            
            arr[(int) y][(int) x] = '*';
        }
        
        List<String> result = new ArrayList<>();
        
        for (char[] row : arr) {
            String str = "";
            for (char chr : row) {
                str += chr;
            }
            result.add(str); 
        }
        
        String[] answer = result.toArray(new String[result.size()]);
        
        return answer;
    }
}