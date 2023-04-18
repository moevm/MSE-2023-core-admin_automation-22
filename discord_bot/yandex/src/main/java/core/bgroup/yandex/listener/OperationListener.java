package core.bgroup.yandex.listener;

import com.yandex.disk.rest.RestClient;
import com.yandex.disk.rest.exceptions.WrongMethodException;
import com.yandex.disk.rest.exceptions.http.HttpCodeException;
import com.yandex.disk.rest.json.Link;
import com.yandex.disk.rest.json.Operation;
import core.bgroup.yandex.handler.OperationEventHandler;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class OperationListener extends TimerTask {
    private final Link operationLink;
    private final RestClient yandexClient;
    private final OperationEventHandler operationEventHandler;

    private Timer timer;

    public OperationListener(Link operationLink, RestClient yandexClient, OperationEventHandler operationEventHandler) {
        this.operationLink = operationLink;
        this.yandexClient = yandexClient;
        this.operationEventHandler = operationEventHandler;
    }

    public void listen(long delay) {
        timer = new Timer();
        timer.scheduleAtFixedRate(this, 0, delay);
    }

    @Override
    public void run() {
        try {
            Operation operation = yandexClient.getOperation(operationLink);
            if (operation.isSuccess()) {
                timer.cancel();
                operationEventHandler.onSuccess();
            }
        } catch (WrongMethodException | IOException | HttpCodeException e) {
            timer.cancel();
            operationEventHandler.onFail();
        }
    }
}
