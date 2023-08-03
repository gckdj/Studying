function solution(s){    
    var braceCount = 0;
    var answer = true;
    
    s.split("").forEach(b => {
        if (b === "(") {
           braceCount += 1;
        } else {
           braceCount -= 1;
        }
        if (braceCount < 0) answer = false;
    });
    
    if (braceCount > 0) {
        answer = false
    }
    
    return answer;
}