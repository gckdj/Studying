// function findCombinations(numbers, targetDepth) {
//     const result = [];
//     if (targetDepth === 1) return numbers.map(n => [n]);
    
//     numbers.forEach((currentValue, index, array) => {
//         const remainder = array.slice(index + 1);
//         const combination = findCombinations(remainder, targetDepth - 1);
//         const c = combination.map(cc => {
//             return [currentValue, ...cc];
//         });
//         result.push(...c);
//     });
    
//     return result;
// }

// function solution(number, k) {
//     const numbers = number.split('');
//     const targetDepth = numbers.length - k;
//     const combinations = findCombinations(numbers, targetDepth);
//     let max = Number.MIN_SAFE_INTEGER;
//     combinations.forEach(c => {
//         const num = Number(c.join(''));
//         if (num > max) {
//             max = num;
//         }
//     })
//     return String(max);
// }

function solution(number, k) {
    const arr = [];
    const nums = number.split('');
    
    for (let i = 0; i < nums.length; i++) {
        while (nums[i] > arr.at(-1) && k > 0 && arr.length > 0) {
            arr.pop();
            k--;
        }
        arr.push(nums[i]);
    }
    
    return arr.join('').substring(0, number.length - k);
}