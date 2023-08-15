class Solution {
    public String solution(int[] food) {
        String answer = "";
        String half = "";

        for (int i = 1; i < food.length; i++) {
            // 주어진 음식 갯수에 2를 나눈 몫 만큼만 문자열 붙이기
            int loop = food[i] / 2;
            for (int j = 0; j < loop; j++) {
                half += String.valueOf(i);
            }
        }

        // half 변수에 문자열 0을 붙이고 역전된 half 붙이기
        return half + "0" + new StringBuffer(half).reverse().toString();
    }
}