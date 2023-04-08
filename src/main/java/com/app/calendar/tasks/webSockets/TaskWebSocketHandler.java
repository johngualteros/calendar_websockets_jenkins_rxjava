package com.app.calendar.tasks.webSockets;

import com.app.calendar.tasks.Task;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

@Component
public class TaskWebSocketHandler extends TextWebSocketHandler implements Observer<Task> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private WebSocketSession session;
    private Disposable disposable;

    @Override
    public void onNext(Task task) {
        try {
            String json = objectMapper.writeValueAsString(task);
            session.sendMessage(new TextMessage(json));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSubscribe(Disposable disposable) {
        this.disposable = disposable;
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onComplete() {
        // No hacer nada
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        this.session = session;
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    public void sendMessage(String message) throws IOException {
        TextMessage textMessage = new TextMessage(message);
        session.sendMessage(textMessage);
    }


}

