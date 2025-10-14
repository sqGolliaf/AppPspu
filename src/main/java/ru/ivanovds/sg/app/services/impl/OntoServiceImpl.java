package ru.ivanovds.sg.app.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ivanovds.models.Node;
import ru.ivanovds.sg.app.domain.OntoResponse;
import ru.ivanovds.sg.app.domain.SaveOntoResponse;
import ru.ivanovds.sg.app.repo.OntoRepository;
import ru.ivanovds.sg.app.services.OntoService;

import java.util.*;

@Service
@RequiredArgsConstructor
public class OntoServiceImpl implements OntoService {

    private final OntoRepository dataOnt;

    @Override
    public List<OntoResponse> nodes() {
        return Arrays.stream(dataOnt.getDataOnto().getNodes())
                .map(it -> new OntoResponse(
                        it.getID(),
                        it.getName(),
                        it.getStorage(),
                        it.getUniqueAttribute("finalyze")))
                .toList();
    }

    @Override
    public List<OntoResponse> firstNodeByName(String name) {
        Node firstNodeByName = dataOnt.getDataOnto().getFirstNodeByName(name);
        ArrayList<Node> getNodesForStart = dataOnt.getDataOnto().getNodesLinkedFrom(firstNodeByName, "");
        return getNodesForStart.stream()
                .map(it -> new OntoResponse(
                        it.getID(),
                        it.getName(),
                        it.getStorage(),
                        it.getUniqueAttribute("finalyze"))
                ).toList();
    }

    @Override
    public List<OntoResponse> getNodesByIdToPath(Integer id) {
        Node node = dataOnt.getDataOnto().getNodeByID(id);
        ArrayList<Node> result = dataOnt.getDataOnto().getNodesLinkedTo(node, "a_path_of");

        return result.stream()
                .map(it -> new OntoResponse(
                        it.getID(),
                        it.getName(),
                        it.getStorage(),
                        it.getUniqueAttribute("finalyze"))
                ).toList();
    }

    @Override
    public List<OntoResponse> getNodesByIdToIs(Integer id) {
        Node node = dataOnt.getDataOnto().getNodeByID(id);
        ArrayList<Node> result = dataOnt.getDataOnto().getNodesLinkedTo(node, "is_a");

        return result.stream()
                .map(it -> new OntoResponse(
                        it.getID(),
                        it.getName(),
                        it.getStorage(),
                        it.getUniqueAttribute("finalyze"))
                ).toList();
    }

    @Override
    public List<OntoResponse> getNodesByIdFrom(Integer id) {
        Node node = dataOnt.getDataOnto().getNodeByID(id);
        ArrayList<Node> nodes = dataOnt.getDataOnto().getNodesLinkedFrom(node, "position");

        return nodes.stream()
                .map(it -> new OntoResponse(
                        it.getID(),
                        it.getName(),
                        it.getStorage(),
                        it.getUniqueAttribute("finalyze"))
                ).toList();
    }

    @Override
    public String result(List<SaveOntoResponse> saveOntoRespons) {
        return "";
    }
}
