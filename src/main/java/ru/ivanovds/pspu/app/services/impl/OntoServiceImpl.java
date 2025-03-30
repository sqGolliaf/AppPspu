package ru.ivanovds.pspu.app.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ivanovds.pspu.app.domain.EmployeeResponse;
import ru.ivanovds.pspu.app.model.Node;
import ru.ivanovds.pspu.app.repo.OntoRepository;
import ru.ivanovds.pspu.app.services.OntoService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OntoServiceImpl implements OntoService {

    private final OntoRepository dataOnt;

    @Override
    public List<EmployeeResponse> nodes() {
        return Arrays.stream(dataOnt.getDataOnto().getNodes())
                .map(it -> new EmployeeResponse(
                        it.getID(),
                        it.getName(),
                        it.getStorage()))
                .toList();
    }

    @Override
    public List<EmployeeResponse> firstNodeByName(String name) {
        Node firstNodeByName = dataOnt.getDataOnto().getFirstNodeByName(name);
        ArrayList<Node> getNodesForStart = dataOnt.getDataOnto().getNodesLinkedFrom(firstNodeByName, "");
        return getNodesForStart.stream()
                .map(it -> new EmployeeResponse(
                        it.getID(),
                        it.getName(),
                        it.getStorage())
                ).toList();
    }

    @Override
    public List<EmployeeResponse> getNodesFrom(Integer id) {
        Node node = dataOnt.getDataOnto().getNodeByID(id);
        ArrayList<Node> nodes = dataOnt.getDataOnto().getNodesLinkedFrom(node, "a_path_of");
        return nodes.stream()
                .map(it -> new EmployeeResponse(
                        it.getID(),
                        it.getName(),
                        it.getStorage())
                ).toList();
    }

    @Override
    public List<EmployeeResponse> getNodesTo(Integer id) {
        Node node = dataOnt.getDataOnto().getNodeByID(id);
        ArrayList<Node> nodes = dataOnt.getDataOnto().getNodesLinkedTo(node, "is_a");
        return nodes.stream()
                .map(it -> new EmployeeResponse(
                                it.getID(),
                                it.getName(),
                                it.getStorage())
                ).toList();
    }
}
