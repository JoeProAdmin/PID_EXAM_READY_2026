package be.iccbxl.pid.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import be.iccbxl.pid.model.Room;
import be.iccbxl.pid.model.RoomService;

@Controller
public class RoomController {

    @Autowired
    private RoomService service;

    @GetMapping("/rooms")
    public String index(Model model) {
        List<Room> rooms = service.getAll();

        model.addAttribute("rooms", rooms);
        model.addAttribute("title", "Liste des salles");

        return "room/index";
    }

    @GetMapping("/rooms/{id}")
    public String show(Model model, @PathVariable("id") Long id) {
        Room room = service.get(id);

        model.addAttribute("room", room);
        model.addAttribute("title", "Fiche d'une salle");

        return "room/show";
    }
}