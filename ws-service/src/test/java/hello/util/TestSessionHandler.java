package hello.util;

import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.fail;

public class TestSessionHandler<T> extends StompSessionHandlerAdapter {

    private final AtomicReference<Throwable> failure;
    private final String topic;
    private final String subscribeTopic;
    private final TestStompFrameHandler<T> handler;

    private T response;

    private final Object payLoad;
    private final CountDownLatch latch;
    private final WebSocketStompClient stomp;

    public TestSessionHandler(AtomicReference<Throwable> failure,
                              String topic,
                              String subscribeTopic,
                              Object payLoad,
                              Class<T> responseType,
                              WebSocketStompClient stompClient) {
        this.failure = failure;
        this.topic = topic;

        this.payLoad = payLoad;
        this.latch = new CountDownLatch(1);

        this.handler = new TestStompFrameHandler<>(failure, responseType, this.latch);
        this.stomp = stompClient;
        this.subscribeTopic=subscribeTopic;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        this.failure.set(new Exception(headers.toString()));
    }

    @Override
    public void handleException(StompSession s, StompCommand c, StompHeaders h, byte[] p, Throwable ex) {
        this.failure.set(ex);
    }

    @Override
    public void handleTransportError(StompSession session, Throwable ex) {
        this.failure.set(ex);
    }


    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        handler.setSession(session);
        session.subscribe(subscribeTopic, handler);
        try {

            session.send(topic, payLoad);

        } catch (Throwable t) {
            failure.set(t);
            latch.countDown();
        }
    }

    public T getResponse() {
        return handler.getResponse();
    }

    public void startTest(String ws, WebSocketHttpHeaders headers) throws InterruptedException {
        this.stomp.connect(ws, headers, this);
        if (latch.await(3, TimeUnit.SECONDS)) {
            if (failure.get() != null) {
                throw new AssertionError("", failure.get());
            }
        }
        else {
            fail("Object not received");
        }
    }
}
