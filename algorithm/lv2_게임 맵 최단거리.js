function solution(maps) {
    // 큐 활용
    var q = [[0, 0, 1]];
    
    while (q.length) {
        var [y, x, answer] = q.shift();

        // 배열 범위 내 + 현 인덱스 값 체크 => 1이면 분기처리
        if (y < maps.length && y >= 0 && x < maps[0].length && x >= 0 && maps[y][x] === 1) {
            // 현 인덱스 방문금지 => 0 처리
            maps[y][x] = 0;


            // 상대방 진영 도달여부 체크
            if (y === maps.length - 1 && x === maps[0].length - 1) {
                // 큐의 매 실행마다 각 방향별로 순회하면서 진행되고,
                // 가장 먼저 도달한 값에서 return 된다.
                return answer;
            }
            
            // console.log(maps);
            
            // 미도달 => 네 방향 큐 추가!
            q.push([y + 1, x, answer + 1]);
            q.push([y - 1, x, answer + 1]);
            q.push([y, x + 1, answer + 1]);
            q.push([y, x - 1, answer + 1]);
        }
    }
    
    return -1;
}