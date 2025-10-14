package ru.ivanovds.sg.app.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OntoController.class)
public class OntoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testStartPage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }
}