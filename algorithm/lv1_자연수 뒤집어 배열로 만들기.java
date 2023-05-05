class Solution {
    public int[] solution(long n) {        
        String s = String.valueOf(n);
        StringBuilder sb = new StringBuilder(s);
        char[] arr = sb.reverse().toString().toCharArray();
        int[] answer = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            answer[i] = arr[i] - '0';
        }
        
        return answer;
    }
}
