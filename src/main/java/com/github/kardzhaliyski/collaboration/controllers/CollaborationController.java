package com.github.kardzhaliyski.collaboration.controllers;

import com.github.kardzhaliyski.collaboration.app.CollaborationInfo;
import com.github.kardzhaliyski.collaboration.app.CollaborationTimeApplication;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStreamReader;

@Controller
@RequestMapping("/")
public class CollaborationController {

    @GetMapping({"/"})
    public String get() {
        return "index.html";
    }

    @ResponseBody
    @PostMapping({"/"})
    public CollaborationInfo collaboration(@RequestBody MultipartFile file) throws IOException {
        if (file == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        String contentType = file.getContentType();
        if(contentType == null || !contentType.startsWith("text/")) {
            throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Input should be a type of text.");
        }

        InputStreamReader reader = new InputStreamReader(file.getInputStream());
        return CollaborationTimeApplication.find(reader);
    }
}
