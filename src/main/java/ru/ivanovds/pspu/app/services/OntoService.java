package ru.ivanovds.pspu.app.services;

import ru.ivanovds.pspu.app.domain.EmployeeResponse;
import ru.ivanovds.pspu.app.domain.SaveResponse;

import java.util.List;

public interface OntoService {
    List<EmployeeResponse> nodes();
    List<EmployeeResponse> firstNodeByName(String name);
    List<EmployeeResponse> getNodesByIdToPath(Integer id);
    List<EmployeeResponse> getNodesByIdToIs(Integer id);
    List<EmployeeResponse> getNodesByIdFrom(Integer id);
    String result(List<SaveResponse> saveResponses);
}
