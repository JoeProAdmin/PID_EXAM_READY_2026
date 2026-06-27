package be.iccbxl.pid.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import be.iccbxl.pid.model.Video;
import be.iccbxl.pid.model.VideoService;

@Controller
public class VideoController {

    @Autowired
    private VideoService service;

    @GetMapping("/videos")
    public String index(Model model) {

        List<Video> videos = service.getAll();

        model.addAttribute("videos", videos);
        model.addAttribute("title", "Liste des vidéos");

        return "video/index";
    }

    @GetMapping("/videos/{id}")
    public String show(Model model, @PathVariable("id") Long id) {

        Video video = service.get(id);

        model.addAttribute("video", video);
        model.addAttribute("title", "Fiche d'une vidéo");

        return "video/show";
    }
}