function solution(n, lost, reserve) {
    const final = [];
    const lost_arr = [];
    
    // 배열 순서대로 정렬
    lost.sort();
    reserve.sort();
    
    // 도난당한 사람과 여분을 챙겨온 사람이 같은 경우를 체크하고 제외
    lost.forEach(item => {
        if (reserve.includes(item)) {
            reserve.splice(reserve.indexOf(item), 1);
        } else {
            lost_arr.push(item)
        }
    })
    
    // 도난당한 사람의 앞번호인 사람부터 뽑기, 없다면 뒷사람
    lost_arr.forEach((item) => {
        const frontman = item - 1;
        const behindman = item + 1;
        
        if (reserve.indexOf(frontman) !== -1) {
            reserve.splice(reserve.indexOf(frontman), 1);
        } else if (reserve.indexOf(behindman) !== -1) {
            reserve.splice(reserve.indexOf(behindman), 1);
        } else {
            final.push(item);
        }
    });
    
    return n - final.length;
}