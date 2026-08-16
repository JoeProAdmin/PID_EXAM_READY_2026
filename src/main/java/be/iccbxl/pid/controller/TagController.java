package be.iccbxl.pid.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import be.iccbxl.pid.model.Show;
import be.iccbxl.pid.model.ShowService;

@Controller
public class TagController {

    @Autowired
    private ShowService showService;

    @GetMapping("/tags/{tag}/shows-without")
    public String showsWithoutTag(@PathVariable("tag") String tag, Model model) {
        try {
            List<Show> shows = showService.getWithoutTag(tag);
            model.addAttribute("tag", tag);
            model.addAttribute("shows", shows);
            model.addAttribute("title", "Spectacles sans mot-clé");
            return "tag/shows-without";
        } catch (IllegalArgumentException exception) {
            return "redirect:/shows";
        }
    }
}
