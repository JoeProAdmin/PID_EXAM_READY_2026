package be.iccbxl.pid.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import be.iccbxl.pid.model.Artist;
import be.iccbxl.pid.model.ArtistService;
import be.iccbxl.pid.model.Troupe;
import be.iccbxl.pid.model.TroupeService;
import be.iccbxl.pid.model.ArtistLanguageService;

@Controller
public class ArtistController {

    @Autowired
    private ArtistService service;

    @Autowired
    private TroupeService troupeService;
    @Autowired private ArtistLanguageService artistLanguageService;

    @GetMapping("/artists")
    public String index(Model model) {
        List<Artist> artists = service.getAllArtists();
        model.addAttribute("artists", artists);
        model.addAttribute("title", "Liste des artistes");
        return "artist/index";
    }

    @GetMapping("/artists/{id}")
    public String show(Model model, @PathVariable("id") String id) {
        Artist artist = service.getArtist(id);
        if (artist == null) {
            return "redirect:/artists";
        }

        model.addAttribute("artist", artist);
        model.addAttribute("troupes", troupeService.getAll());
        model.addAttribute("isActor", artistLanguageService.isActor(artist));
        model.addAttribute("languages", artistLanguageService.getLanguages());
        model.addAttribute("levels", artistLanguageService.getLevels());
        model.addAttribute("title", "Fiche d'un artiste");
        return "artist/show";
    }

    @PostMapping("/artists/{id}/languages")
    public String addLanguage(@PathVariable("id") String id, @RequestParam("languageId") String languageId,
                              @RequestParam("level") String level, RedirectAttributes redirectAttributes) {
        try { artistLanguageService.add(Long.parseLong(id), Long.parseLong(languageId), level);
            redirectAttributes.addFlashAttribute("languageSuccess", "La langue a été ajoutée.");
        } catch (RuntimeException exception) { redirectAttributes.addFlashAttribute("languageError", exception.getMessage()); }
        return "redirect:/artists/" + id;
    }

    @PostMapping("/artists/{id}/troupe")
    public String updateTroupe(@PathVariable("id") String id,
                               @RequestParam(value = "troupeId", required = false) String troupeId,
                               RedirectAttributes redirectAttributes) {
        Long artistId;
        try {
            artistId = Long.parseLong(id);
        } catch (NumberFormatException exception) {
            redirectAttributes.addFlashAttribute("troupeError", "L'artiste sélectionné n'existe pas.");
            return "redirect:/artists";
        }

        try {
            if (troupeId == null || troupeId.trim().isEmpty()) {
                service.removeFromTroupe(artistId);
                redirectAttributes.addFlashAttribute("troupeSuccess", "L'artiste n'est plus affilié à une troupe.");
            } else {
                service.affiliateToTroupe(artistId, Long.parseLong(troupeId));
                redirectAttributes.addFlashAttribute("troupeSuccess", "La troupe de l'artiste a été mise à jour.");
            }
        } catch (NumberFormatException exception) {
            redirectAttributes.addFlashAttribute("troupeError", "La troupe sélectionnée est invalide.");
        } catch (IllegalArgumentException exception) {
            redirectAttributes.addFlashAttribute("troupeError", exception.getMessage());
        }

        return "redirect:/artists/" + artistId;
    }

    @GetMapping("/artists/create")
    public String create(Model model) {
        model.addAttribute("artist", new Artist(null, null));
        return "artist/create";
    }

    @PostMapping("/artists/create")
    public String store(@Valid @ModelAttribute("artist") Artist artist,
                        BindingResult bindingResult,
                        Model model) {
        if (bindingResult.hasErrors()) {
            return "artist/create";
        }
        service.addArtist(artist);
        return "redirect:/artists/" + artist.getId();
    }

    @GetMapping("/artists/{id}/edit")
    public String edit(Model model, @PathVariable("id") String id, HttpServletRequest request) {
        Artist artist = service.getArtist(id);
        if (artist == null) {
            return "redirect:/artists";
        }
        model.addAttribute("artist", artist);
        String referrer = request.getHeader("Referer");
        model.addAttribute("back", referrer != null && !referrer.isEmpty() ? referrer : "/artists/" + artist.getId());
        return "artist/edit";
    }

    @PutMapping("/artists/{id}/edit")
    public String update(@Valid @ModelAttribute("artist") Artist artist,
                         BindingResult bindingResult,
                         @PathVariable("id") String id) {
        if (bindingResult.hasErrors()) {
            return "artist/edit";
        }
        Artist existing = service.getArtist(id);
        if (existing == null) {
            return "redirect:/artists";
        }
        artist.setId(existing.getId());
        artist.setTroupe(existing.getTroupe());
        service.updateArtist(artist.getId(), artist);
        return "redirect:/artists/" + artist.getId();
    }

    @DeleteMapping("/artists/{id}")
    public String delete(@PathVariable("id") String id) {
        Artist existing = service.getArtist(id);
        if (existing != null) {
            service.deleteArtist(existing.getId());
        }
        return "redirect:/artists";
    }
}
