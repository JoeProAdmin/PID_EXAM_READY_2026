package be.iccbxl.pid.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import be.iccbxl.pid.model.Troupe;
import be.iccbxl.pid.model.TroupeService;

@Controller
public class TroupeController {

    @Autowired
    private TroupeService service;

    @GetMapping("/troupes")
    public String index(Model model) {
        List<Troupe> troupes = service.getAll();
        model.addAttribute("troupes", troupes);
        model.addAttribute("title", "Liste des troupes");
        return "troupe/index";
    }

    @GetMapping("/troupes/{id}")
    public String show(Model model, @PathVariable("id") String id) {
        Troupe troupe;
        try {
            troupe = service.get(id);
        } catch (RuntimeException exception) {
            troupe = null;
        }
        if (troupe == null) {
            return "redirect:/troupes";
        }
        model.addAttribute("troupe", troupe);
        model.addAttribute("title", "Fiche d'une troupe");
        return "troupe/show";
    }
}
