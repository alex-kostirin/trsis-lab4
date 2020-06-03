package ru.guap.shoppinglist.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;


@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "lists")
public class List {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Integer id;
    private @NotBlank(message = "name is mandatory") String name;
    private @JsonIgnore @OneToMany(mappedBy = "list", cascade = CascadeType.ALL, orphanRemoval = true) @OrderBy("id") java.util.List<Item> items;

    public List(String name) {
        this(null, name, null);
    }
}
