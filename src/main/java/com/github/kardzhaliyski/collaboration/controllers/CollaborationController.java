package com.github.kardzhaliyski.collaboration.controllers;

import com.github.kardzhaliyski.collaboration.app.CollaborationInfo;
import com.github.kardzhaliyski.collaboration.app.CollaborationTimeApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
        InputStreamReader reader = new InputStreamReader(file.getInputStream());
        return CollaborationTimeApplication.find(reader);
    }
}
