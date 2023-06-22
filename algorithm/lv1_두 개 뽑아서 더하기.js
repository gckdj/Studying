function solution(numbers) {
    const answer = [];
  	// 모든 경우의 수 완전탐색
    for (let i = 0; i < numbers.length - 1; i++) {
        for (let j = i + 1; j < numbers.length; j++) {
            const val = numbers[i] + numbers[j];
          	// 합친 값이 기존 배열에 있다면 제외
            if (!answer.includes(val)) {
              	// 없다면 push
                answer.push(val);
            }
        }
    }
  	// 정렬된 값으로 반환
    return answer.sort((a, b) => a - b);
}