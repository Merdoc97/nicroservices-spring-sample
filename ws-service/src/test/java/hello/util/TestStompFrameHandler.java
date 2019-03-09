package hello.util;

import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;

import java.lang.reflect.Type;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

/**
 * test stomp frame handler adapter
 * current class created for create easiest way to test web sockets via stomp
 *
 * @param <T>
 */
public class TestStompFrameHandler<T> implements StompFrameHandler {

    private Class<T> type;

    private T response;
    private final AtomicReference<Throwable> failure;
    private StompSession session;
    private final CountDownLatch latch;

    public TestStompFrameHandler(AtomicReference<Throwable> failure,
                                 Class<T> type,
                                 CountDownLatch latch) {
        this.type = type;
        this.failure = failure;
        this.latch = latch;
    }

    @Override
    public Type getPayloadType(StompHeaders stompHeaders) {
        return type;
    }

    @Override
    public void handleFrame(StompHeaders stompHeaders, Object payload) {
        try {
            response = (T) payload;
        } catch (Throwable t) {
            failure.set(t);
        } finally {
            session.disconnect();
            latch.countDown();
        }
    }

    public T getResponse() {
        return response;
    }

    public void setSession(StompSession session) {
        this.session = session;
    }
}
