function solution(N, stages) {
    var answer = [];
    for (let i = 1; i <= N; i++) {
        let games = 0;
        let fail = 0;
        stages.forEach(s => {
           if (s >= i) {
               games++;
               if (s == i) {
                   fail++;
               }
           } 
        });
        answer.push([i, fail / games]);
    }
        
    return answer.sort((a, b) => {
        return b[1] - a[1];
    }).map(a => a[0]);;
}