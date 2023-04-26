package core.bgroup.yandex.service.impl;

import com.yandex.disk.rest.ResourcesArgs;
import com.yandex.disk.rest.RestClient;
import com.yandex.disk.rest.exceptions.ServerIOException;
import com.yandex.disk.rest.json.Link;
import core.bgroup.yandex.handler.YandexOperationHandler;
import core.bgroup.yandex.listener.YandexOperationListener;
import core.bgroup.yandex.service.YandexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.function.Consumer;

@Service
@Slf4j
public class YandexServiceImpl implements YandexService {

    private final RestClient yandexClient;

    public YandexServiceImpl(RestClient yandexClient) {
        this.yandexClient = yandexClient;
    }

    public void uploadFileFromUrl(String url, String path, Consumer<String> consumer) throws ServerIOException, IOException {
        Link operationLink = yandexClient.saveFromUrl(url, path);
        YandexOperationListener yandexOperationListener = new YandexOperationListener(operationLink, yandexClient, new YandexOperationHandler() {
            @Override
            public void onSuccess() {
                try {
                    consumer.accept(publishFile(path));
                } catch (ServerIOException | IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail() {
                log.error("Failed to upload recording from " + url + " to " + path + "!");
            }
        });
        yandexOperationListener.listen(1000);
    }

    public String publishFile(String path) throws ServerIOException, IOException {
        yandexClient.publish(path);
        return yandexClient.getResources(new ResourcesArgs.Builder().setPath(path).build()).getPublicUrl();
    }
}
