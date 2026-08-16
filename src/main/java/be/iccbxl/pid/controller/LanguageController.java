package be.iccbxl.pid.controller;
import org.springframework.beans.factory.annotation.Autowired; import org.springframework.stereotype.Controller; import org.springframework.ui.Model; import org.springframework.web.bind.annotation.*; import be.iccbxl.pid.model.ArtistLanguageService;
@Controller public class LanguageController { @Autowired private ArtistLanguageService service;
 @GetMapping("/languages/{language}/artists-fluent") public String fluent(@PathVariable String language,Model model){model.addAttribute("language",language);model.addAttribute("artistLanguages",service.fluent(language));return "language/artists-fluent";} }
