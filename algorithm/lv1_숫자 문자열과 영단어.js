function solution(s) {
    const dictionary = {
        zero: 0,
        one: 1,
        two: 2,
        three: 3,
        four: 4,
        five: 5,
        six: 6,
        seven: 7,
        eight: 8,
        nine: 9,
    };
    
    let result = '';
    let temp = '';
    
    for (let i = 0; i < s.length; i++) {
        const char = s.charAt(i);
        const ascii = char.charCodeAt(0);
        
        // 숫자여부 아스키코드 체크
        if (ascii > 47 && ascii < 58) {
            // 숫자라면 별도처리없이 결과에 추가
            result += char;
        } else {
            // 숫자가 아니라면 temp 변수에 추가하고, 딕셔너리 키 중 있는지 확인
            temp += char;
            if (Object.keys(dictionary).includes(temp)) {
                // 있다면 딕셔너리의 값을 결과에 추가
                result += dictionary[temp];
                // temp는 초기화
                temp = '';
            }
        }
    }
    
    return Number(result);
}