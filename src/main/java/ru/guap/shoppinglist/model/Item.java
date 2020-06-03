package ru.guap.shoppinglist.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@Entity(name = "items")
public class Item {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Integer id;
    private @NotBlank(message = "name is mandatory") String name;
    private @NotNull @Min(0) @Max(Integer.MAX_VALUE) Integer count = 0;
    private @NotNull Boolean bought = false;
    private @JsonIgnore @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "list_id", nullable = false) List list;
}
