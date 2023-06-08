class Solution {
    public int[][] solution(int[][] arr1, int[][] arr2) {
        int[][] answer = new int[arr1.length][arr2[0].length];
        
        // arr1 행렬 열 순회
        for (int i = 0; i < arr1.length; i++) {
        	// arr2 행렬 행 순회
            for (int j = 0; j < arr2[0].length; j++) {
                int sum = 0;
                // arr1 행렬 행의 각 값과 arr1 행렬의 각 열값을 곱해준다.
                for (int k = 0; k < arr1[i].length; k++) {
                    sum += arr1[i][k] * arr2[k][j];
                }
                answer[i][j] = sum;
            }
        }
        
        return answer;
    }
}
