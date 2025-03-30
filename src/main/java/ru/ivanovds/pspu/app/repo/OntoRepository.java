package ru.ivanovds.pspu.app.repo;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.ivanovds.pspu.app.model.Onto;

import java.io.IOException;

@Getter
@Repository
public class OntoRepository {
    private final Onto dataOnto;

    public OntoRepository(@Value("${file.onto.url}") Resource resource) throws IOException {
        dataOnto = new Onto(resource.getInputStream());
    }
}
