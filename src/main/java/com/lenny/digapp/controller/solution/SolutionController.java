package com.lenny.digapp.controller.solution;

import com.lenny.digapp.model.Input;
import com.lenny.digapp.service.solution.SolutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class SolutionController {

    @Autowired
    SolutionService solutionService;

    @GetMapping("/test")
    public String test() {
        return "solution";
    }

    @PostMapping("/solve")
    public List<List<List<Double>>> solve(@RequestBody Input input) {
        return solutionService.solve(input);
    }
}

