package be.iccbxl.pid.controller;

import java.util.List;

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
import be.iccbxl.pid.model.Video;
import be.iccbxl.pid.model.VideoService;

@Controller
public class VideoController {

    @Autowired
    private VideoService service;

    @Autowired
    private ShowService showService;

    @GetMapping("/videos")
    public String index(Model model) {
        model.addAttribute("videos", service.getAll());
        model.addAttribute("title", "Liste des vidéos");
        return "video/index";
    }

    @GetMapping("/videos/{id}")
    public String show(Model model, @PathVariable("id") Long id) {
        Video video = service.get(id);

        if (video == null) {
            return "redirect:/videos";
        }

        model.addAttribute("video", video);
        model.addAttribute("title", "Fiche d'une vidéo");
        return "video/show";
    }

    @PostMapping("/shows/{showId}/videos")
    public String store(
            @PathVariable("showId") String showId,
            @RequestParam("title") String title,
            @RequestParam("videoUrl") String videoUrl,
            RedirectAttributes redirectAttributes) {

        Show show;

        try {
            show = showService.get(showId);
        } catch (RuntimeException exception) {
            show = null;
        }
        String cleanTitle = title == null ? "" : title.trim();
        String cleanVideoUrl = videoUrl == null ? "" : videoUrl.trim();

        redirectAttributes.addFlashAttribute("videoTitle", cleanTitle);
        redirectAttributes.addFlashAttribute("videoUrl", cleanVideoUrl);

        if (show == null) {
            redirectAttributes.addFlashAttribute("videoError", "Le spectacle n'existe pas.");
            return "redirect:/shows";
        }

        if (cleanTitle.isEmpty() || cleanTitle.length() > 255) {
            redirectAttributes.addFlashAttribute(
                    "videoError",
                    "Le titre est obligatoire et ne peut pas dépasser 255 caractères.");
            return "redirect:/shows/" + show.getId();
        }

        if (cleanVideoUrl.isEmpty() || cleanVideoUrl.length() > 30) {
            redirectAttributes.addFlashAttribute(
                    "videoError",
                    "L'URL est obligatoire et ne peut pas dépasser 30 caractères.");
            return "redirect:/shows/" + show.getId();
        }

        if (service.videoUrlExists(cleanVideoUrl)) {
            redirectAttributes.addFlashAttribute(
                    "videoError",
                    "Cette URL vidéo existe déjà.");
            return "redirect:/shows/" + show.getId();
        }

        service.add(new Video(cleanTitle, cleanVideoUrl, show));
        redirectAttributes.addFlashAttribute("videoSuccess", "La vidéo a été ajoutée.");

        return "redirect:/shows/" + show.getId();
    }

    @GetMapping("/artists/{lastname}/videos")
    public String videosByArtist(
            Model model,
            @PathVariable("lastname") String lastname) {

        List<Video> videos = service.getByArtistLastname(lastname);

        model.addAttribute("lastname", lastname);
        model.addAttribute("videos", videos);
        model.addAttribute("title", "Vidéos des spectacles de l'artiste " + lastname);

        return "video/by-artist";
    }
}
