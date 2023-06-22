function solution(numbers, hand) {
    let answer = '';
    let l_stat = '*';
    let r_stat = '#';
    let numberPad = 
        [
            [1, 2, 3],
            [4, 5, 6],
            [7, 8, 9],
            ['*', 0, '#']
        ]
        
    numbers.forEach(item => {
       if (item === 1 || item === 4 || item === 7) {
           l_stat = item;
           answer += 'L';
       } else if (item === 3 || item === 6 || item === 9) {
           r_stat = item;
           answer += 'R';
       } else {
           if (l_stat === item) {
               answer += 'L';
           } else if (r_stat === item) {
               answer += 'R';
           } else {
               let padIdx = [];
               let leftIdx = [];
               let rightIdx = [];

               for (let i = 0; i < numberPad.length; i++) {
                   for (let j = 0; j < numberPad[0].length; j++) {
                       if (item === numberPad[i][j]) {
                           padIdx = [i, j];
                       }

                       if (l_stat === numberPad[i][j]) {
                           leftIdx = [i, j];
                       }

                       if (r_stat === numberPad[i][j]) {
                           rightIdx = [i, j];
                       }
                   }
               }

               const left_distance = getDistance(padIdx, leftIdx);
               const right_distance = getDistance(padIdx, rightIdx);

               if (left_distance === right_distance) {
                   if (hand === 'left') {
                       l_stat = item;
                       answer += 'L';
                   } else {
                       r_stat = item;
                       answer += 'R';
                   }
               } else {
                   if (left_distance < right_distance) {
                       l_stat = item;
                       answer += 'L';
                   } else {
                       r_stat = item;
                       answer += 'R';
                   }
               }
           }
       }
    })
    
    return answer;
}

function getDistance(arr1, arr2) {
    return Math.abs(arr1[0] - arr2[0]) + Math.abs(arr1[1] - arr2[1]);
}