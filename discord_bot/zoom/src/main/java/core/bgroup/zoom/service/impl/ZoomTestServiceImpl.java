package core.bgroup.zoom.service.impl;

import core.bgroup.zoom.service.ZoomTestService;
import org.springframework.stereotype.Service;

@Service
public class ZoomTestServiceImpl implements ZoomTestService {
    @Override
    public void test() {
        System.out.println("message from yandex module");
    }
}
