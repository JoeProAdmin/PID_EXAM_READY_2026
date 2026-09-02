package be.iccbxl.pid.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import be.iccbxl.pid.model.Artist;
import be.iccbxl.pid.model.Type;
import be.iccbxl.pid.model.TypeService;
import be.iccbxl.pid.model.Typology;
import be.iccbxl.pid.model.TypologyService;

@Controller
public class TypologyController {

    @Autowired
    private TypologyService typologyService;

    @Autowired
    private TypeService typeService;

    @GetMapping("/typologies")
    public String index(Model model) {
        model.addAttribute("typologies", typologyService.getAll());
        model.addAttribute("title", "Liste des typologies");
        return "typology/index";
    }

    @GetMapping("/typologies/{id}")
    public String show(Model model, @PathVariable("id") String id) {
        Typology typology = typologyService.get(id);

        if (typology == null) {
            return "redirect:/typologies";
        }

        List<Artist> artists = new ArrayList<>();

        for (Type type : typology.getTypes()) {
            for (Artist artist : type.getArtists()) {
                if (!artists.contains(artist)) {
                    artists.add(artist);
                }
            }
        }

        artists.sort(Comparator
                .comparing(Artist::getLastname)
                .thenComparing(Artist::getFirstname));

        model.addAttribute("typology", typology);
        model.addAttribute("artists", artists);
        model.addAttribute("types", typeService.getAll());
        model.addAttribute("title", "Fiche d'une typologie");

        return "typology/show";
    }

    @PostMapping("/typologies/{id}/types")
    public String addType(
            @PathVariable("id") String id,
            @RequestParam("typeId") String typeId,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {

        if (authentication == null ||
                authentication.getAuthorities().stream()
                        .noneMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {

            return "redirect:/login";
        }

        Typology typology = typologyService.get(id);
        Type type = typeService.get(typeId);

        if (typology == null || type == null) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    "Typologie ou type introuvable."
            );
            return "redirect:/typologies";
        }

        Typology oldTypology = type.getTypology();

        if (oldTypology != null && !oldTypology.getId().equals(typology.getId())) {
            redirectAttributes.addFlashAttribute(
                    "warning",
                    "Le type appartenait déjà à la typologie "
                            + oldTypology.getName()
                            + " et a été déplacé."
            );
        } else if (oldTypology != null) {
            redirectAttributes.addFlashAttribute(
                    "info",
                    "Ce type appartient déjà à cette typologie."
            );
            return "redirect:/typologies/" + id;
        }

        type.setTypology(typology);
        typeService.update(typeId, type);

        redirectAttributes.addFlashAttribute(
                "success",
                "Le type a été ajouté à la typologie."
        );

        return "redirect:/typologies/" + id;
    }
}