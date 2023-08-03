function solution(n) {
    let arr = [0, 1]
    let count = 0;
    
    // (A+B)%C = ((A%C)+(B%C))%C
    while (count < n) {
        arr.push(arr[0] % 1234567 + arr[1] % 1234567);
        arr.shift();
        count += 1;
    }
    
    return arr[0] % 1234567;
}