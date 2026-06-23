package be.iccbxl.pid.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="representations")
public class Representation {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name="show_id", nullable=false)
	private Show show;

	private LocalDateTime when;

	@ManyToOne
	@JoinColumn(name="room_id", nullable=false)
	private Room room;

	@ManyToMany
	@JoinTable(
			name = "reservations",
			joinColumns = @JoinColumn(name = "representation_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<User> users = new ArrayList<>();

	public Representation() { }

	public Representation(Show show, LocalDateTime when, Room room) {
		this.show = show;
		this.when = when;
		this.room = room;
	}

	public Show getShow() {
		return show;
	}

	public void setShow(Show show) {
		this.show = show;
	}

	public LocalDateTime getWhen() {
		return when;
	}

	public void setWhen(LocalDateTime when) {
		this.when = when;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Long getId() {
		return id;
	}

	public List<User> getUsers() {
		return users;
	}

	public Representation addUser(User user) {
		if(!this.users.contains(user)) {
			this.users.add(user);
			user.addRepresentation(this);
		}

		return this;
	}

	public Representation removeUser(User user) {
		if(this.users.contains(user)) {
			this.users.remove(user);
			user.getRepresentations().remove(this);
		}

		return this;
	}

	@Override
	public String toString() {
		return "Representation [id=" + id + ", show=" + show + ", when=" + when
				+ ", room=" + room + "]";
	}
}