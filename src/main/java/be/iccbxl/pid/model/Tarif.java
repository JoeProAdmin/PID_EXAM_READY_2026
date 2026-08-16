package be.iccbxl.pid.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tarifs")
public class Tarif {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le type de tarif est obligatoire.")
    @Size(max = 30, message = "Le type de tarif ne peut pas dépasser 30 caractères.")
    @Column(nullable = false, length = 30)
    private String type;

    @NotNull(message = "Le prix est obligatoire.")
    @DecimalMin(value = "0.0", inclusive = true, message = "Le prix doit être positif ou nul.")
    private Double prix;

    @ManyToOne
    @JoinColumn(name = "show_id", nullable = false)
    private Show show;

    protected Tarif() {
    }

    public Tarif(String type, Double prix, Show show) {
        this.type = type;
        this.prix = prix;
        this.show = show;
    }

    public Long getId() { return id; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public Double getPrix() { return prix; }
    public void setPrix(Double prix) { this.prix = prix; }
    public Show getShow() { return show; }
    public void setShow(Show show) { this.show = show; }

    @Override
    public String toString() {
        return type + " : " + prix;
    }
}
