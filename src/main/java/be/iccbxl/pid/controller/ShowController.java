package be.iccbxl.pid.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import be.iccbxl.pid.model.Artist;
import be.iccbxl.pid.model.ArtistType;
import be.iccbxl.pid.model.Show;
import be.iccbxl.pid.model.ShowService;
import be.iccbxl.pid.model.Tag;
import be.iccbxl.pid.model.TagService;

@Controller
public class ShowController {

    @Autowired
    private ShowService service;

    @Autowired
    private TagService tagService;

    @GetMapping("/shows")
    public String index(@RequestParam(value = "tag", required = false) String tag, Model model) {
        String selectedTag = tag == null ? "" : tag.trim();
        List<Show> shows = selectedTag.isEmpty() ? service.getAll() : service.searchByTag(selectedTag);

        model.addAttribute("shows", shows);
        model.addAttribute("selectedTag", selectedTag);
        model.addAttribute("resultCount", shows.size());
        model.addAttribute("title", "Liste des spectacles");
        return "show/index";
    }

    @GetMapping("/shows/{id}")
    public String show(Model model, @PathVariable("id") String id) {
        Show show = service.get(id);
        if (show == null) {
            return "redirect:/shows";
        }

        Map<String, ArrayList<Artist>> collaborateurs = new TreeMap<>();
        for (ArtistType artistType : show.getArtistTypes()) {
            String type = artistType.getType().getType();
            if (collaborateurs.get(type) == null) {
                collaborateurs.put(type, new ArrayList<Artist>());
            }
            collaborateurs.get(type).add(artistType.getArtist());
        }

        model.addAttribute("collaborateurs", collaborateurs);
        model.addAttribute("show", show);
        model.addAttribute("title", "Fiche d'un spectacle");
        return "show/show";
    }

    @PostMapping("/shows/{showId}/tags")
    public String addTag(@PathVariable("showId") String showId,
                         @RequestParam("tag") String tag,
                         RedirectAttributes redirectAttributes) {
        Long id;
        try {
            id = Long.parseLong(showId);
            Tag savedTag = tagService.findOrCreate(tag);
            service.addTag(id, savedTag);
            redirectAttributes.addFlashAttribute("tagSuccess", "Le mot-clé a été ajouté.");
        } catch (NumberFormatException exception) {
            redirectAttributes.addFlashAttribute("tagError", "Le spectacle sélectionné est invalide.");
            return "redirect:/shows";
        } catch (IllegalArgumentException exception) {
            redirectAttributes.addFlashAttribute("tagError", exception.getMessage());
        }
        return "redirect:/shows/" + showId;
    }
}
