package core.bgroup.yandex.service;

import com.yandex.disk.rest.exceptions.ServerIOException;
import core.bgroup.yandex.handler.OperationEventHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface YandexService {
    void uploadFileFromUrl(String url, String path, OperationEventHandler operationEventHandler) throws ServerIOException, IOException;
}
