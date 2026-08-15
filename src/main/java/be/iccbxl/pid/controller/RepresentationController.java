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
	private RepresentationService service;

	@Autowired
	private ShowService showService;

	@Autowired
	private RoomService roomService;

	@GetMapping("/representations")
	public String index(Model model) {
		List<Representation> representations = service.getAll();

		model.addAttribute("representations", representations);
		model.addAttribute("title", "Liste des représentations");

		return "representation/index";
	}

	@GetMapping("/representations/{id}")
	public String show(Model model, @PathVariable("id") String id) {
		Representation representation = service.get(id);

		if (representation == null) {
			return "redirect:/representations";
		}

		model.addAttribute("representation", representation);
		model.addAttribute("date", representation.getWhen().toLocalDate());
		model.addAttribute("heure", representation.getWhen().toLocalTime());
		model.addAttribute("title", "Fiche d'une représentation");

		return "representation/show";
	}

	@GetMapping("/representations/create")
	public String create(Model model) {
		prepareCreateForm(model, null, null, null);

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

		if (show == null) {
			return createWithError(
					model,
					"Le spectacle sélectionné n'existe pas.",
					showId,
					roomId,
					when);
		}

		if (room == null) {
			return createWithError(
					model,
					"La salle sélectionnée n'existe pas.",
					showId,
					roomId,
					when);
		}

		LocalDateTime dateTime;

		try {
			dateTime = LocalDateTime.parse(when);
		} catch (RuntimeException exception) {
			return createWithError(
					model,
					"La date et l'heure sont invalides.",
					showId,
					roomId,
					when);
		}

		if (service.isRoomOccupied(room, dateTime)) {
			return createWithError(
					model,
					"La salle est déjà occupée à cette date et à cette heure.",
					showId,
					roomId,
					when);
		}

		Representation representation =
				new Representation(show, dateTime, room);

		service.add(representation);

		return "redirect:/representations/" + representation.getId();
	}

	private String createWithError(
			Model model,
			String error,
			String selectedShowId,
			Long selectedRoomId,
			String selectedWhen) {

		model.addAttribute("error", error);
		prepareCreateForm(
				model,
				selectedShowId,
				selectedRoomId,
				selectedWhen);

		return "representation/create";
	}

	private void prepareCreateForm(
			Model model,
			String selectedShowId,
			Long selectedRoomId,
			String selectedWhen) {

		model.addAttribute("representation", new Representation());
		model.addAttribute("shows", showService.getAll());
		model.addAttribute("rooms", roomService.getAll());
		model.addAttribute("selectedShowId", selectedShowId);
		model.addAttribute("selectedRoomId", selectedRoomId);
		model.addAttribute("selectedWhen", selectedWhen);
	}
}