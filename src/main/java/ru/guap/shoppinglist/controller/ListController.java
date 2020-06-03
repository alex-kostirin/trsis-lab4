package ru.guap.shoppinglist.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;
import ru.guap.shoppinglist.model.Item;
import ru.guap.shoppinglist.model.List;
import ru.guap.shoppinglist.repository.ListRepository;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

@RestController
@RequestMapping("/lists")
@RequiredArgsConstructor
public class ListController {
    private final ListRepository repository;
    private final ListModelAssembler assembler;
    private final EntityValidator<List> entityValidator;

    @GetMapping("")
    CollectionModel<EntityModel<List>> all() {
        var lists = repository.findAll();
        return assembler.toCollectionModel(lists);
    }

    @PostMapping("")
    ResponseEntity<?> create(@Valid @RequestBody List newList) throws URISyntaxException {
        var model = assembler.toModel(repository.save(newList));
        return ResponseEntity
                .created(new URI(model.getRequiredLink(IanaLinkRelations.SELF).getHref()))
                .body(model);
    }

    @GetMapping("/{id}")
    EntityModel<List> get(@PathVariable Integer id) {
        var list = repository.findById(id)
                .orElseThrow(() -> new ListNotFoundException(id));
        return assembler.toModel(list);
    }

    @PatchMapping("/{id}")
    EntityModel<List> update(@RequestBody Map<String, Object> updatedFields, @PathVariable Integer id) {
        var list = repository.findById(id)
                .orElseThrow(() -> new ListNotFoundException(id));
        updatedFields.forEach((k, v) -> {
            var field = ReflectionUtils.findField(List.class, k);
            if (field != null && !k.equals("id")) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, list, v);
            }
        });
        entityValidator.validate(list);
        return assembler.toModel(repository.save(list));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable Integer id) {
        var list = repository.findById(id)
                .orElseThrow(() -> new ListNotFoundException(id));
        repository.delete(list);
        return ResponseEntity.noContent().build();
    }
}
