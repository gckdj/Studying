package hello.advanced.trace;


import java.util.UUID;

public class TraceId {

    private String id;
    private int level;

    public TraceId() {
        this.id = createId();
        this.level = 0;
    }

    private TraceId(String id, int level) {
        this.id = id;
        this.level = level;
    }

    private String createId() {
        // 생성된 uuid 중 앞 8자리만 사용
        // 트랜잭션 아이디가 중복될 수는 있으나 그럴 확률은 매우적고, 중복된다하더라도 큰 문제는 없는 상황
        return UUID.randomUUID().toString().substring(0, 8);
    }

    private TraceId createNextId() {
        // 같은 트레이스 아이디를 추적하고, 레벨만 올려주는 메서드
        return new TraceId(id, level + 1);
    }

    private TraceId createPreviousId() {
        return new TraceId(id, level - 1);
    }

    public boolean isFirstLevel() {
        return level == 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
