package ru.ivanovds.pspu.app.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ivanovds.pspu.app.domain.EmployeeResponse;
import ru.ivanovds.pspu.app.domain.SaveResponse;
import ru.ivanovds.pspu.app.model.FieldChecker;
import ru.ivanovds.pspu.app.model.Node;
import ru.ivanovds.pspu.app.repo.OntoRepository;
import ru.ivanovds.pspu.app.services.OntoService;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                        it.getStorage(),
                        it.getUniqueAttribute("finalyze")))
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
                        it.getStorage(),
                        it.getUniqueAttribute("finalyze"))
                ).toList();
    }

    @Override
    public List<EmployeeResponse> getNodesByIdToPath(Integer id) {
        Node node = dataOnt.getDataOnto().getNodeByID(id);
        ArrayList<Node> result = dataOnt.getDataOnto().getNodesLinkedTo(node, "a_path_of");

        return result.stream()
                .map(it -> new EmployeeResponse(
                        it.getID(),
                        it.getName(),
                        it.getStorage(),
                        it.getUniqueAttribute("finalyze"))
                ).toList();
    }

    @Override
    public List<EmployeeResponse> getNodesByIdToIs(Integer id) {
        Node node = dataOnt.getDataOnto().getNodeByID(id);
        ArrayList<Node> result = dataOnt.getDataOnto().getNodesLinkedTo(node, "is_a");

        return result.stream()
                .map(it -> new EmployeeResponse(
                        it.getID(),
                        it.getName(),
                        it.getStorage(),
                        it.getUniqueAttribute("finalyze"))
                ).toList();
    }

    @Override
    public List<EmployeeResponse> getNodesByIdFrom(Integer id) {
        Node node = dataOnt.getDataOnto().getNodeByID(id);
        ArrayList<Node> nodes = dataOnt.getDataOnto().getNodesLinkedFrom(node, "position");

        return nodes.stream()
                .map(it -> new EmployeeResponse(
                        it.getID(),
                        it.getName(),
                        it.getStorage(),
                        it.getUniqueAttribute("finalyze"))
                ).toList();
    }

    @Override
    public String result(List<SaveResponse> saveResponses) {
        Map<String, String> fields = new HashMap<>();

        for (SaveResponse saveResponse: saveResponses) {
            Node node = dataOnt.getDataOnto().getNodeByID(saveResponse.id());
            Node res = dataOnt.getDataOnto().getNodesLinkedTo(node, "pattern").stream().findFirst().orElseThrow();
            String result = autoFix(res.getName(), node.getName(), saveResponse.text());
            fields.put(node.getName().toLowerCase(), result);
        }

        return formatGOSTCitation(
                fields.getOrDefault("название", ""),
                fields.getOrDefault("сведения об ответственности", ""),
                fields.getOrDefault("сведения об издании", ""),
                fields.getOrDefault("место публикации", ""),
                fields.getOrDefault("сведения об издании", ""),
                fields.getOrDefault("дата публикации", ""),
                fields.getOrDefault("сведения о нумерации", ""),
                fields.getOrDefault("сведения о страницах", ""),
                fields.getOrDefault("сведение об авторе", "")
        );
    }

    public String autoFix(String regex, String type, String input) {
        input = input.trim();

        return switch (type.toLowerCase()) {
            case "название", "место публикации" -> capitalize(input);
            case "сведения об ответственности" -> formatPersonList(input);
            case "сведения об издании" -> fixEdition(input);
            case "дата публикации" -> input.replaceAll("\\D", "").substring(0, Math.min(4, input.length()));
            case "сведения о нумерации" -> fixNumbering(input);
            case "сведения о страницах" -> {
                Matcher pageMatcher = Pattern.compile("(\\d+)").matcher(input);
                yield pageMatcher.find() ? pageMatcher.group(1) : input;
            }
            default -> {
                Matcher m = Pattern.compile(regex).matcher(input);
                yield m.find() ? m.group(1).trim() : input;
            }
        };
    }

    private String capitalize(String text) {
        if (text == null || text.isBlank()) return "";
        return text.substring(0, 1).toUpperCase() + text.substring(1).toLowerCase();
    }

    private String formatPersonList(String input) {
        String[] parts = input.trim().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String p : parts) {
            if (p.contains(".")) {
                sb.append(p.substring(0, 1).toUpperCase()).append(p.substring(1).toLowerCase()).append(" ");
            } else {
                sb.append(capitalize(p)).append(" ");
            }
        }
        return sb.toString().trim().replaceAll("\\s{2,}", " ");
    }

    private String fixEdition(String input) {
        return input
                .replaceAll("(?i)(\\d)е\\s*изд", "$1-е изд.")
                .replaceAll("(?i)\\s*испр", ", испр.")
                .replaceAll("(?i)\\s*доп", ", доп.")
                .replaceAll("\\s{2,}", " ")
                .trim();
    }

    private String fixNumbering(String input) {
        input = input.toLowerCase().trim();
        return capitalize(input);
    }

    public String formatGOSTCitation(
            String title,
            String responsibility,
            String edition,
            String place,
            String publisher,
            String year,
            String numbering,
            String pages,
            String author
    ) {
        StringBuilder sb = new StringBuilder();

        if (!responsibility.isBlank()) sb.append(responsibility).append(". ");
        if (!title.isBlank()) sb.append(title).append(": ");
        if (!edition.isBlank()) sb.append(edition).append(" / ").append(responsibility).append(". ");
        if (!place.isBlank()) sb.append("— ").append(place).append(": ");
        if (!author.isBlank()) sb.append("— ").append(author).append(" ");
        if (!publisher.isBlank()) sb.append(publisher).append(", ");
        if (!year.isBlank()) sb.append(year).append(". ");
        if (!numbering.isBlank()) sb.append("— ").append(numbering).append(". ");
        if (!pages.isBlank()) sb.append("— ").append(pages).append(" с.");

        return sb.toString().replaceAll("\\s{2,}", " ").trim();
    }

}
