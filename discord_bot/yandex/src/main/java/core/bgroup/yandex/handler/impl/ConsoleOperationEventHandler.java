package core.bgroup.yandex.handler.impl;

import core.bgroup.yandex.handler.OperationEventHandler;

public class ConsoleOperationEventHandler implements OperationEventHandler {
    @Override
    public void onSuccess() {
        System.out.println("success");
    }

    @Override
    public void onFail() {
        System.out.println("fail");
    }
}
