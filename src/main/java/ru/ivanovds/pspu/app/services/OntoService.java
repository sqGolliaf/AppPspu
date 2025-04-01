package ru.ivanovds.pspu.app.services;

import ru.ivanovds.pspu.app.domain.EmployeeResponse;

import java.util.List;

public interface OntoService {
    List<EmployeeResponse> nodes();
    List<EmployeeResponse> firstNodeByName(String name);

    List<EmployeeResponse> getNodesById(Integer id);
}
