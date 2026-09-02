package be.iccbxl.pid.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "typologies")
public class Typology {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 60, nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "typology")
    private List<Type> types = new ArrayList<>();

    public Typology() {
    }

    public Typology(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }

    public Typology addType(Type type) {
        if (!types.contains(type)) {
            types.add(type);
            type.setTypology(this);
        }
        return this;
    }

    @Override
    public String toString() {
        return "Typology [id=" + id + ", name=" + name + "]";
    }
}