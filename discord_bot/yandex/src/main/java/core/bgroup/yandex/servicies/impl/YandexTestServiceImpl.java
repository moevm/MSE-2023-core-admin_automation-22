package core.bgroup.yandex.servicies.impl;

import core.bgroup.yandex.servicies.YandexTestService;
import org.springframework.stereotype.Service;

@Service
public class YandexTestServiceImpl implements YandexTestService {

    @Override
    public void test() {
        System.out.println("message from yandex module");
    }

}
