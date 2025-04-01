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
    public List<EmployeeResponse> getNodesByIdTo(Integer id) {
        Node node = dataOnt.getDataOnto().getNodeByID(id);
        ArrayList<Node> nodesAPathOf = dataOnt.getDataOnto().getNodesLinkedTo(node, "a_path_of");
        ArrayList<Node> nodesIsA = dataOnt.getDataOnto().getNodesLinkedTo(node, "is_a");
        ArrayList<Node> result = new ArrayList<>(nodesIsA.size() + nodesAPathOf.size());
        result.addAll(nodesIsA);
        result.addAll(nodesAPathOf);

        return result.stream()
                .map(it -> new EmployeeResponse(
                        it.getID(),
                        it.getName(),
                        it.getStorage())
                ).toList();
    }

    @Override
    public List<EmployeeResponse> getNodesByIdFrom(Integer id) {
        Node node = dataOnt.getDataOnto().getNodeByID(id);
        ArrayList<Node> nodes = dataOnt.getDataOnto().getNodesLinkedFrom(node, "postion");

        return nodes.stream()
                .map(it -> new EmployeeResponse(
                        it.getID(),
                        it.getName(),
                        it.getStorage())
                ).toList();
    }
}
