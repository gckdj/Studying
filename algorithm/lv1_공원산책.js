function solution(park, routes) {
    var answer = [];
    var field = [];
    var x = 0;
    var y = 0;
    
    park.forEach((p, index) => {
        const arr = p.split('');
        if (arr.indexOf('S') !== -1) {
            x = arr.indexOf('S');
            y = index;
        }
        
        field.push(arr);
    });
    
    routes.forEach(r => {
        const code = r.split(' ');
        var count = 0;
        var check_x = x;
        var check_y = y;
        var valid = true;
                
        while (count < code[1]) {
            switch (code[0]) {
                case 'N': {
                    check_y -= 1;
                    break;
                }
                case 'E': {
                    check_x += 1;
                    break;
                }
                case 'S': {
                    check_y += 1;
                    break;
                }
                case 'W': {
                    check_x -= 1;
                    break;
                }
            }
            
            if (check_x > field[0].length - 1 || 
                check_y > field.length - 1 ||
                check_x < 0 ||
                check_y < 0) {
                valid = false;
            }
            
            if (valid && field[check_y][check_x] === 'X') {
                valid = false;
            }
            
            count++;
        }
        
        if (valid) {
            x = check_x;
            y = check_y;
        }
    });
    
    return [y, x];
}