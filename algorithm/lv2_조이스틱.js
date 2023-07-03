function solution(name) {
    let count = 0;
    const arr = name.split('');
    let move 0;
    
    for (let i = 0; i < arr.length; i++) {
        const from = 'A'.charCodeAt(0);
        const to = arr[i].charCodeAt(0);
        
        if (arr[i] !== 'A' && i !== 0) {
            countNotA++;
        }
        
        const u_distance = to - from
        const d_distance = 'Z'.charCodeAt(0) - to + 1;
        
        if (u_distance > d_distance) {
            count += d_distance;
        } else {
            count += u_distance;
        }
        let nextCur = i + 1;
        if (nextCur < arr.length &&)
    }
        
    return count + result;
}