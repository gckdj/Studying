class Solution {
    public String solution(String new_id) {
        String answer = "";
        
        // 1~4단계 정규식
        answer = new_id
            .toLowerCase()
            .replaceAll("[^a-z0-9\\-\\_\\.]", "")
            .replaceAll("\\.{2,}", ".")
            .replaceAll("^\\.|\\.$", "");
            
        // 5단계
        if (answer.isBlank()) {
            answer = "a";
        }
                
        // 6단계
        if (answer.length() >= 16) {
            StringBuilder sb = new StringBuilder();
            char[] c = answer.toCharArray();
            
            for (int i = 0; i < 15; i++) {
                if (!(i == 14 && c[i] == '.')) {
                    sb.append(c[i]);
                }
            }
            
            answer = sb.toString();
        }
        
        // 7단계
        if (answer.length() <= 2) {
            char[] c = answer.toCharArray();
            char lastCharacter = c[c.length - 1];
            
            StringBuilder sb = new StringBuilder();
            sb.append(answer);
            while (sb.length() <= 2) {
                sb.append(lastCharacter);
            }
            
            answer = sb.toString();
        }
                    
        return answer;
    }
}
