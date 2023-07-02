function solution(wallpaper) {
    const arr = wallpaper.map(item => {
        return item.split('');
    });
    
    let min_x = Number.MAX_SAFE_INTEGER;
    let min_y = Number.MAX_SAFE_INTEGER;
    let max_x = Number.MIN_SAFE_INTEGER;
    let max_y = Number.MIN_SAFE_INTEGER;
    
    for (let i = 0; i < arr.length; i++) {
        for(let j = 0; j < arr[0].length; j++) {
            const val = arr[i][j];
            
            if (val === '#') {
                if (min_x >= i) {
                    min_x = i;
                }
                
                if (min_y >= j) {
                    min_y = j;
                }
                
                if (max_x <= i) {
                    max_x = i + 1;
                }
                
                if (max_y <= j) {
                    max_y = j + 1;
                }
            }
        }
    }
    
    return [min_x, min_y, max_x, max_y];
}