function solution(players, callings) {
    const p_obj = {};
    
    for (let i = 0; i < players.length; i++) {
        p_obj[players[i]] = i;
    }
    
    for (let i = 0; i < callings.length; i++) {
        const secondPlayerName = callings[i];
        const secondPlayerIndex = p_obj[secondPlayerName];
        const firstPlayerName = players[secondPlayerIndex - 1];
        const firstPlayerIndex = p_obj[firstPlayerName];
        
        p_obj[secondPlayerName] -= 1;
        p_obj[firstPlayerName] += 1;
        
        const temp = firstPlayerName;
        
        players[firstPlayerIndex] = secondPlayerName;
        players[secondPlayerIndex] = temp;
    }
        
    return players;
}