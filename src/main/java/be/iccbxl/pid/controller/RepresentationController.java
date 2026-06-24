package be.iccbxl.pid.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import be.iccbxl.pid.model.Representation;
import be.iccbxl.pid.model.RepresentationService;
import be.iccbxl.pid.model.Room;
import be.iccbxl.pid.model.RoomService;
import be.iccbxl.pid.model.Show;
import be.iccbxl.pid.model.ShowService;

@Controller
public class RepresentationController {

	@Autowired
	RepresentationService service;

	@Autowired
	ShowService showService;

	@Autowired
	RoomService roomService;

	@GetMapping("/representations")
	public String index(Model model) {

		List<Representation> representations = service.getAll();

		model.addAttribute("representations", representations);
		model.addAttribute("title", "Liste des representations");

		return "representation/index";
	}

	@GetMapping("/representations/{id}")
	public String show(Model model, @PathVariable("id") String id) {

		Representation representation = service.get(id);

		model.addAttribute("representation", representation);
		model.addAttribute("date", representation.getWhen().toLocalDate());
		model.addAttribute("heure", representation.getWhen().toLocalTime());
		model.addAttribute("title", "Fiche d'une representation");

		return "representation/show";
	}

	@GetMapping("/representations/create")
	public String create(Model model) {

		model.addAttribute("representation", new Representation());
		model.addAttribute("shows", showService.getAll());
		model.addAttribute("rooms", roomService.getAll());

		return "representation/create";
	}

	@PostMapping("/representations/create")
	public String store(
			@RequestParam("showId") String showId,
			@RequestParam("roomId") Long roomId,
			@RequestParam("when") String when,
			Model model) {

		Show show = showService.get(showId);

		Room room = roomService.get(roomId);

		LocalDateTime dateTime = LocalDateTime.parse(when);

		if (service.isRoomOccupied(room, dateTime)) {

			model.addAttribute("error",
					"La salle est déjà occupée à cette date.");

			model.addAttribute("shows", showService.getAll());
			model.addAttribute("rooms", roomService.getAll());

			return "representation/create";
		}

		Representation representation =
				new Representation(show, dateTime, room);

		service.add(representation);

		return "redirect:/representations/" + representation.getId();
	}
}