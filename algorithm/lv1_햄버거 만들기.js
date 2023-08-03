function solution(ingredient) {
    let burgerCnt = 0;
    let i = 0;
        
    while (i < ingredient.length) {
        if (ingredient[i] === 1 && ingredient[i + 1] === 2 && ingredient[i + 2] === 3 && ingredient[i + 3] === 1) {
            // 일치하는 부분은 잘라내기
            ingredient.splice(i, 4);
            // 인덱스 뒤로 돌리기 (한 싸이클 기준)
            i = i - 4;
            burgerCnt++;
        }
        i++;
    }
    
    return burgerCnt;
}