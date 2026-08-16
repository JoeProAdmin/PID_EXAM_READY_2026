package be.iccbxl.pid.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import be.iccbxl.pid.model.TarifService;

@RestController
public class TarifApiController {

    @Autowired
    private TarifService service;

    @GetMapping("/api/tarifs/types")
    public List<String> types() {
        return service.getTypes();
    }
}
