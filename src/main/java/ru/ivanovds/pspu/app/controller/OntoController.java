package ru.ivanovds.pspu.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping("/{id}")
    public ResponseEntity<List<EmployeeResponse>> getNodesById(@PathVariable Integer id) {
        return new ResponseEntity<>(ontoService.getNodesById(id), HttpStatus.OK);
    }
}
