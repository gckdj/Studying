def solution(cap, n, deliveries, pickups):
    
    answer = 0
    deli_cap = 0
    pick_cap = 0
    
    for i in range(n-1, -1, -1):
        
        count = 0
        
        deli_cap -= deliveries[i]
        pick_cap -= pickups[i]
        
        while deli_cap < 0 or pick_cap < 0:
            deli_cap += cap
            pick_cap += cap
            count += 1
    
        answer += (i + 1) * 2 * count
    return answer