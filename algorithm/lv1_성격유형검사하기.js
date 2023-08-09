function solution(survey, choices) {
    var answer = {
        R: 0,
        T: 0,
        C: 0,
        F: 0,
        J: 0,
        M: 0,
        A: 0,
        N: 0,
    };
    
    for (var i = 0; i < choices.length; i++) {
        var c = choices[i];
        var s = survey[i];
        var key = null;
        var point = 0;
        
        if (c > 4) {
            key = s.charAt(1);
            point = c - 4;
            answer[key] += point;
        } else if (c < 4) {
            key = s.charAt(0);
            point = 4 - c;
            answer[key] += point;
        }
    }
    
    var result = '';
    
    if (answer.R >= answer.T) {
        result += 'R';
    } else if (answer.R < answer.T) {
        result += 'T';
    }
    
    if (answer.C >= answer.F) {
        result += 'C';
    } else if (answer.C < answer.F) {
        result += 'F';
    }
    
    if (answer.J >= answer.M) {
        result += 'J';
    } else if (answer.J < answer.M) {
        result += 'M';
    }
    
    if (answer.A >= answer.N) {
        result += 'A';
    } else if (answer.A < answer.N) {
        result += 'N';
    }
        
    return result;
}