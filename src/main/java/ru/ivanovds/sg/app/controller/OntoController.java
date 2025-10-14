package ru.ivanovds.sg.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.ivanovds.sg.app.domain.ErrorResponse;
import ru.ivanovds.sg.app.domain.OntoResponse;
import ru.ivanovds.sg.app.domain.SaveOntoResponse;
import ru.ivanovds.sg.app.exception.NotFoundException;
import ru.ivanovds.sg.app.services.OntoService;
import ru.ivanovds.sg.app.services.impl.JmsOrderMessagingServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8080")
public class OntoController {

    private final OntoService ontoService;
    private final JmsOrderMessagingServiceImpl messageService;

    @GetMapping("/")
    public ResponseEntity<List<OntoResponse>> getFirstNode() {
        List<OntoResponse> ontoList = ontoService.firstNodeByName("#Старт");
        messageService.sendOrder(ontoList.getFirst());
        return new ResponseEntity<>(ontoList, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<OntoResponse>> getNodes() {
        return new ResponseEntity<>(ontoService.nodes(), HttpStatus.OK);
    }

    @GetMapping("/to/{id}")
    public ResponseEntity<List<OntoResponse>> getNodesByIdTo(@PathVariable Integer id) {
        return new ResponseEntity<>(ontoService.getNodesByIdToIs(id), HttpStatus.OK);
    }

    @GetMapping("/path/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<OntoResponse> getNodesByIdPath(@PathVariable Integer id) {
        return ontoService.getNodesByIdToPath(id);
    }

    @GetMapping("/from/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<OntoResponse> getNodesByIdFrom(@PathVariable Integer id) {
        return ontoService.getNodesByIdFrom(id);
    }

    @PostMapping(value = "/save", produces = "application/json; charset=UTF-8", consumes = "application/json; charset=UTF-8")
    @PreAuthorize("#{hasRole('ADMIN')}")
    public ResponseEntity<String> saveAnswer(@RequestBody List<SaveOntoResponse> resp) {
        return new ResponseEntity<>(ontoService.result(resp), HttpStatus.CREATED);
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception e) {
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, List.of(e.getMessage()));
    }

    @ExceptionHandler(value = NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(NotFoundException e) {
        return new ErrorResponse(HttpStatus.NOT_FOUND, List.of(e.getMessage()));
    }
}
