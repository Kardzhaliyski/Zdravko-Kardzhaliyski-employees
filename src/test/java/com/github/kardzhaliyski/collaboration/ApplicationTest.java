package com.github.kardzhaliyski.collaboration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {Application.class})
@AutoConfigureMockMvc
class ApplicationTest {
    private static final String EXPECTED_RESPONSE = "{\"employee1\":{\"id\":218},\"employee2\":{\"id\":143},\"projectId\":10,\"days\":30}";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testWithCorrectData() throws Exception {
        Path path = Path.of("src/test/resources/sample.csv");
        InputStream inputStream = Files.newInputStream(path);
        MockMultipartFile mockFile = new MockMultipartFile("file", "sample.csv", MediaType.TEXT_PLAIN_VALUE, inputStream);
        MvcResult mvcResult = mockMvc.perform(multipart(HttpMethod.POST, "/")
                        .file(mockFile))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(EXPECTED_RESPONSE, mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testWithCorrectDataAndDifferentDateFormat() throws Exception {
        Path path = Path.of("src/test/resources/format1.csv");
        InputStream inputStream = Files.newInputStream(path);
        MockMultipartFile mockFile = new MockMultipartFile("file", "format1.csv", MediaType.TEXT_PLAIN_VALUE, inputStream);
        MvcResult mvcResult = mockMvc.perform(multipart(HttpMethod.POST, "/")
                        .file(mockFile))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(EXPECTED_RESPONSE, mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testWithCorrectDataAndDifferentDateFormat2() throws Exception {
        Path path = Path.of("src/test/resources/format2.csv");
        InputStream inputStream = Files.newInputStream(path);
        MockMultipartFile mockFile = new MockMultipartFile("file", "format2.csv", MediaType.TEXT_PLAIN_VALUE, inputStream);
        MvcResult mvcResult = mockMvc.perform(multipart(HttpMethod.POST, "/")
                        .file(mockFile))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(EXPECTED_RESPONSE, mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testWithCorrectDataAndDifferentDateFormat3() throws Exception {
        Path path = Path.of("src/test/resources/format3.csv");
        InputStream inputStream = Files.newInputStream(path);
        MockMultipartFile mockFile = new MockMultipartFile("file", "format3.csv", MediaType.TEXT_PLAIN_VALUE, inputStream);
        MvcResult mvcResult = mockMvc.perform(multipart(HttpMethod.POST, "/")
                        .file(mockFile))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(EXPECTED_RESPONSE, mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testShouldReturnBadRequestForInvalidData() throws Exception {
        MockMultipartFile mockFile = new MockMultipartFile("file", "invalidData", MediaType.TEXT_PLAIN_VALUE, "Some invalid data".getBytes());
        mockMvc.perform(multipart(HttpMethod.POST, "/")
                        .file(mockFile))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void testShouldReturnBadRequestIfNoFileIsSend() throws Exception {
        mockMvc.perform(multipart(HttpMethod.POST, "/"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}