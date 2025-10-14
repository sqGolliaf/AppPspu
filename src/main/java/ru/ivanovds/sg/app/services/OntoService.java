package ru.ivanovds.sg.app.services;

import ru.ivanovds.sg.app.domain.OntoResponse;
import ru.ivanovds.sg.app.domain.SaveOntoResponse;

import java.util.List;

public interface OntoService {
    List<OntoResponse> nodes();
    List<OntoResponse> firstNodeByName(String name);
    List<OntoResponse> getNodesByIdToPath(Integer id);
    List<OntoResponse> getNodesByIdToIs(Integer id);
    List<OntoResponse> getNodesByIdFrom(Integer id);
    String result(List<SaveOntoResponse> saveOntoResponses);
}
