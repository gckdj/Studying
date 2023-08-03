function solution(k, score) {
    var answer = [];
    var arr = [];
    for (var i = 0; i < score.length; i++) {
        var s = score[i];
        arr.push(s);
        // 주어진 점수들을 받을 때마다 자체배열에 넣고 정렬
        arr.sort((a, b) => {
           return b - a; 
        });
                
        // 인덱스가 k 이하인 경우는 arr 마지막 인덱스를 사용
        if (k > i) {
            answer.push(arr.at(-1));
        } else {
        // 그 이상인 경우는 k를 인덱스로 사용
            answer.push(arr[k - 1]);
        }        
    }
    return answer;
}