package ru.ivanovds.pspu.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ivanovds.pspu.app.domain.EmployeeResponse;
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

    @GetMapping("/to/{id}")
    public ResponseEntity<List<EmployeeResponse>> getNodesByIdTo(@PathVariable Integer id) {
        return new ResponseEntity<>(ontoService.getNodesByIdTo(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<EmployeeResponse> saveAnswer(@RequestBody EmployeeResponse resp) {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
