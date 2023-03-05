package core.bgroup.yandex.service.impl;

import core.bgroup.yandex.service.YandexTestService;
import org.springframework.stereotype.Service;

@Service
public class YandexTestServiceImpl implements YandexTestService {

    @Override
    public void test() {
        System.out.println("message from yandex module");
    }

}
