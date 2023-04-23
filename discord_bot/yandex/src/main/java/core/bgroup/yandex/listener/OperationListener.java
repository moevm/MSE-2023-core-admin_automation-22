package core.bgroup.yandex.listener;

import com.yandex.disk.rest.RestClient;
import com.yandex.disk.rest.exceptions.ServerIOException;
import com.yandex.disk.rest.exceptions.WrongMethodException;
import com.yandex.disk.rest.json.Link;
import com.yandex.disk.rest.json.Operation;
import core.bgroup.yandex.handler.OperationHandler;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class OperationListener extends TimerTask {
    private final Link operationLink;
    private final RestClient yandexClient;
    private final OperationHandler operationHandler;

    private Timer timer;

    public OperationListener(Link operationLink, RestClient yandexClient, OperationHandler operationHandler) {
        this.operationLink = operationLink;
        this.yandexClient = yandexClient;
        this.operationHandler = operationHandler;
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
                operationHandler.onSuccess();
            }
        } catch (WrongMethodException | IOException | ServerIOException e) {
            timer.cancel();
            operationHandler.onFail();
        }
    }
}
