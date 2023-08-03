function solution(name) {
    // name = "AAAACB"
    // name = 'AABAAAAAAABBB'
    const arr = name.split('');
    let move = arr.length - 1;
    
    for (let i = 0; i < arr.length; i++) {
        const from = 'A'.charCodeAt(0);
        const to = arr[i].charCodeAt(0);
        const u_distance = to - from
        const d_distance = 'Z'.charCodeAt(0) - to + 1;
        
        count += Math.min(d_distance, u_distance);
        
        if (i < arr.length - 1 && arr[i + 1] === 'A') {
            let nextCur = i + 1;
            while (nextCur < arr.length && arr[nextCur] === 'A') {
                nextCur += 1;
            }
            // 정방향, 정방향 -> 역방향, 역방향 -> 정방향
            move = Math.min(move,
                            i * 2 + (arr.length - nextCur),
                            (arr.length - nextCur) * 2 + i);
            
            console.log(move);
        }
    }
    
    return count + move;
}