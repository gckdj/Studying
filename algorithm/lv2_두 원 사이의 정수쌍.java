class Solution {
    public long solution(int r1, int r2) {
        long answer = 0;
        // 그래프의 양인 부분에서 조건에 일치하는 점의 개수를 구하고 거기에 4를 곱한 값이 정답
        for (long i = 1; i <= r2; i++) {
            // r2 * r2식으로 제곱처리 방법을 (long) Math.pow(r2, 2)과 같이 처리해 오버플로우 방지가 매우 중요하다.
            double maxY = Math.sqrt((long) Math.pow(r2, 2) - (long) Math.pow(i, 2));
            double minY = Math.sqrt((long) Math.pow(r1, 2) - (long) Math.pow(i, 2));
            long count = -1;
            
            if (Double.isNaN(minY)) {
                count = (long) Math.floor(maxY) + 1;
            } else {
                // 실수 사이에 해당하는 정수의 개수를 구하기 위해 높은 값에서는 '내림' 낮은 값에서는 '올림'처리
                count = (long) (Math.floor(maxY) - Math.ceil(minY) + 1);
            }
            answer += count;
        }
        return answer * 4;
    }
}