package core.bgroup.yandex.controller;

import com.yandex.disk.rest.exceptions.ServerIOException;
import core.bgroup.yandex.handler.impl.ConsoleOperationEventHandler;
import core.bgroup.yandex.service.YandexService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/yandex")
public class TestController {
    private final YandexService yandexService;

    public TestController(YandexService yandexService) {
        this.yandexService = yandexService;
    }

    //TODO: remove
    @PostMapping
    public void testUpload(@RequestBody String link) throws ServerIOException, IOException {
        yandexService.uploadFileFromUrl(link, "test_record", new ConsoleOperationEventHandler());
    }
}
