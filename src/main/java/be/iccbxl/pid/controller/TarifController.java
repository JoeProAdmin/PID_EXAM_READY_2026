package be.iccbxl.pid.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import be.iccbxl.pid.model.Show;
import be.iccbxl.pid.model.ShowService;
import be.iccbxl.pid.model.TarifService;

@Controller
public class TarifController {

    @Autowired
    private TarifService tarifService;

    @Autowired
    private ShowService showService;

    @GetMapping("/tarifs/create")
    public String create(Model model) {
        prepareForm(model, null, null, null);
        return "tarif/create";
    }

    @PostMapping("/tarifs/create")
    public String store(@RequestParam("showId") String showId,
                        @RequestParam("type") String type,
                        @RequestParam("prix") String prix,
                        Model model,
                        RedirectAttributes redirectAttributes) {
        Long id;
        Double amount;
        try {
            id = Long.parseLong(showId);
            amount = Double.parseDouble(prix);
        } catch (RuntimeException exception) {
            return formWithError(model, "Le spectacle et le prix doivent être valides.", showId, type, prix);
        }

        try {
            tarifService.create(id, type, amount);
        } catch (IllegalArgumentException exception) {
            return formWithError(model, exception.getMessage(), showId, type, prix);
        }

        redirectAttributes.addFlashAttribute("tarifSuccess", "Le tarif spécial a été ajouté.");
        return "redirect:/shows/" + id + "/tarifs";
    }

    @GetMapping("/shows/{id}/tarifs")
    public String showTarifs(@PathVariable("id") String id, Model model) {
        Show show = showService.get(id);
        if (show == null) {
            return "redirect:/shows";
        }
        model.addAttribute("show", show);
        model.addAttribute("tarifs", tarifService.getForShow(show));
        model.addAttribute("title", "Tarifs du spectacle");
        return "tarif/show";
    }

    private String formWithError(Model model, String error, String showId, String type, String prix) {
        model.addAttribute("tarifError", error);
        prepareForm(model, showId, type, prix);
        return "tarif/create";
    }

    private void prepareForm(Model model, String selectedShowId, String selectedType, String selectedPrix) {
        model.addAttribute("shows", showService.getAll());
        model.addAttribute("selectedShowId", selectedShowId);
        model.addAttribute("selectedType", selectedType);
        model.addAttribute("selectedPrix", selectedPrix);
    }
}
