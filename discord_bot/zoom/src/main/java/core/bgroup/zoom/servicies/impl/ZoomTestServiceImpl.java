package core.bgroup.zoom.servicies.impl;

import core.bgroup.zoom.servicies.ZoomTestService;
import org.springframework.stereotype.Service;

@Service
public class ZoomTestServiceImpl implements ZoomTestService {
    @Override
    public void test() {
        System.out.println("message from yandex module");
    }
}
