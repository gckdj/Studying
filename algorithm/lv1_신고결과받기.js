function solution(id_list, report, k) {
    const users = {};
    id_list.forEach(id => {
        users[id] = [];
    });
        
    const banditList = {};
    
    report.forEach(r => {
        const arr = r.split(" ");
        const citizen = arr[0];
        const bandit = arr[1];
        
        if (!users[citizen].includes(bandit)) {
            if (banditList[bandit] === undefined) {
                banditList[bandit] = 1;
            } else {
                banditList[bandit] += 1;
            }
            users[citizen].push(bandit);
        }
    });
    answer = [];
    id_list.forEach(id => {
        let count = 0;
        users[id].forEach(bandit => {
            if (banditList[bandit] > k - 1) {
                count++;
            }
        });
        answer.push(count);
    });
    return answer;
}