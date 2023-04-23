package core.bgroup.yandex.service;

import com.yandex.disk.rest.exceptions.ServerIOException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.function.Consumer;

@Service
public interface YandexService {
    void uploadFileFromUrl(String url, String path, Consumer<String> consumer) throws ServerIOException, IOException;
}
