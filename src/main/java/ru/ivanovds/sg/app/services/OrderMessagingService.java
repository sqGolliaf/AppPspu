package ru.ivanovds.sg.app.services;

import ru.ivanovds.sg.app.domain.OntoResponse;

public interface OrderMessagingService {
    void sendOrder(OntoResponse ontoResponse);
}
