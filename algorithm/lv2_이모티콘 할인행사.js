function solution(users, emoticons) {
    const answer = [];
    const permutations = [];
    const discounts = [10, 20, 30, 40];
    const leng = emoticons.length - 1;
    
    // 중복순열을 통한 모든 경우의 수 수집
    const getPermutations = function (discounts, depth, state) {
        if (depth > leng) {
            permutations.push([...state]);
            return;
        }
        
        for (var i = 0; i < discounts.length; i++) {
            const discount = discounts[i];
            getPermutations(discounts, depth + 1, state.concat(discount));
        }
    }
    
    getPermutations(discounts, 0, []);
    
    const collectedResults = [];
    
    // 할인정책 이터레이트
    permutations.forEach(dis => {
        let count = 0;
        let totalPrice = 0;
        // 각 할인정책에 따른 유저들의 반응수집
        users.forEach(user => {
            const percent = user[0];
            const limit = user[1];
            
            let price = 0;
            
            dis.forEach((d, index) => {
               if (d >= percent) {
                   const discountedPrice = emoticons[index] - (d * 0.01 * emoticons[index]);
                   price += discountedPrice;
               }
            });
            
            // 유저가 구매할 총 가격이 유저의 제한가격 이상이면 구매가격 초기화, 이모티콘 플러스 서비스가입 + 1
            if (price >= limit) {
                price = 0;
                count += 1;
            }
            
            totalPrice += price;
        });
        
        collectedResults.push([count, totalPrice]);
    });
    
    // 정렬
    collectedResults.sort((a, b) => {
        if (a[0] > b[0]) {
            return 1;
        }
        
        if (a[0] < b[0]) {
            return -1;
        }
        
        if (a[1] > b[1]) {
            return 1;
        }
        
        if (a[1] < b[1]) {
            return -1;
        }
    });
    
    return collectedResults.at(-1);
}