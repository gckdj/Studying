package hello.springtx.order;

// Exception을 상속받은 체크예외
public class NotEnoughMoneyException extends Exception {

    public NotEnoughMoneyException(String message) {
        super(message);
    }
}
