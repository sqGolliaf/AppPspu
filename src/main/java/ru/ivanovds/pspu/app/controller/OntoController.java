package ru.ivanovds.pspu.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ivanovds.pspu.app.domain.EmployeeResponse;
import ru.ivanovds.pspu.app.domain.SaveResponse;
import ru.ivanovds.pspu.app.services.OntoService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OntoController {

    private final OntoService ontoService;

    @GetMapping("/")
    public ResponseEntity<List<EmployeeResponse>> getFirstNode() {
        return new ResponseEntity<>(ontoService.firstNodeByName("#Старт"), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<EmployeeResponse>> getNodes() {
        return new ResponseEntity<>(ontoService.nodes(), HttpStatus.OK);
    }

    @GetMapping("/to/{id}")
    public ResponseEntity<List<EmployeeResponse>> getNodesByIdTo(@PathVariable Integer id) {
        return new ResponseEntity<>(ontoService.getNodesByIdToIs(id), HttpStatus.OK);
    }

    @GetMapping("/path/{id}")
    public ResponseEntity<List<EmployeeResponse>> getNodesByIdPath(@PathVariable Integer id) {
        return new ResponseEntity<>(ontoService.getNodesByIdToPath(id), HttpStatus.OK);
    }

    @GetMapping("/from/{id}")
    public ResponseEntity<List<EmployeeResponse>> getNodesByIdFrom(@PathVariable Integer id) {
        return new ResponseEntity<>(ontoService.getNodesByIdFrom(id), HttpStatus.OK);
    }

    @PostMapping(value = "/save", produces = "application/json; charset=UTF-8", consumes = "application/json; charset=UTF-8")
    public ResponseEntity<String> saveAnswer(@RequestBody List<SaveResponse> resp) {
        return new ResponseEntity<>(ontoService.result(resp), HttpStatus.CREATED);
    }
}
