package ru.ivanovds.sg.app.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import ru.ivanovds.sg.app.domain.OntoResponse;
import ru.ivanovds.sg.app.services.OrderMessagingService;

@Service
@RequiredArgsConstructor
public class JmsOrderMessagingServiceImpl implements OrderMessagingService {

    private final JmsTemplate jms;

    @Override
    public void sendOrder(OntoResponse ontoResponse) {
        String ontoQueue = "test.onto.queue";
        jms.send(ontoQueue,
                session -> session.createObjectMessage(ontoResponse));
    }
}
