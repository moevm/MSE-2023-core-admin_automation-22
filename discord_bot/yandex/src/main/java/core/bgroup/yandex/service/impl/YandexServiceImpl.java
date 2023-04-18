package core.bgroup.yandex.service.impl;

import com.yandex.disk.rest.RestClient;
import com.yandex.disk.rest.exceptions.ServerIOException;
import com.yandex.disk.rest.json.Link;
import core.bgroup.yandex.handler.OperationEventHandler;
import core.bgroup.yandex.listener.OperationListener;
import core.bgroup.yandex.service.YandexService;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class YandexServiceImpl implements YandexService {

    private final RestClient yandexClient;

    public YandexServiceImpl(RestClient yandexClient) {
        this.yandexClient = yandexClient;
    }

    public void uploadFileFromUrl(String url, String path, OperationEventHandler operationEventHandler) throws ServerIOException, IOException {
        Link operationLink = yandexClient.saveFromUrl(url, path);
        OperationListener operationListener = new OperationListener(operationLink, yandexClient, operationEventHandler);
        operationListener.listen(1000);
    }
}
