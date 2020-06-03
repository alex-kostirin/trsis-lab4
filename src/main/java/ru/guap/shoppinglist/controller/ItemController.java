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
import ru.guap.shoppinglist.repository.ItemRepository;
import ru.guap.shoppinglist.repository.ListRepository;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

@RestController
@RequestMapping("/lists/{listId}/items")
@RequiredArgsConstructor
public class ItemController {
    private final ListRepository listRepository;
    private final ItemRepository itemRepository;
    private final ItemModelAssembler assembler;
    private final EntityValidator<Item> entityValidator;

    @GetMapping("")
    CollectionModel<EntityModel<Item>> all(@PathVariable Integer listId) {
        var list = getListOrThrow(listId);
        return assembler.toCollectionModel(list.getItems(), listId);
    }

    @PostMapping("")
    ResponseEntity<?> create(@RequestBody Item newItem, @PathVariable Integer listId) throws URISyntaxException {
        newItem.setList(getListOrThrow(listId));
        var model = assembler.toModel(itemRepository.save(newItem));
        return ResponseEntity
                .created(new URI(model.getRequiredLink(IanaLinkRelations.SELF).getHref()))
                .body(model);
    }

    @GetMapping("/{id}")
    EntityModel<Item> get(@PathVariable Integer id, @PathVariable Integer listId) {
        getListOrThrow(listId);
        var item = itemRepository.findByIdAndListId(id, listId)
                .orElseThrow(() -> new ItemNotFoundException(id));
        return assembler.toModel(item);
    }

    @PatchMapping("/{id}")
    EntityModel<Item> update(@RequestBody Map<String, Object> updatedFields, @PathVariable Integer id, @PathVariable Integer listId) {
        getListOrThrow(listId);
        var item = itemRepository.findByIdAndListId(id, listId)
                .orElseThrow(() -> new ItemNotFoundException(id));
        // очень жаль что спринг не умеет нормально в патч
        // и приходится делать подобные фокусы с рефлексией
        updatedFields.forEach((k, v) -> {
            var field = ReflectionUtils.findField(Item.class, k);
            if (field != null && !k.equals("id")) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, item, v);
            }
        });
        // и это тоже некрасиво но спрингу видимо все равно
        entityValidator.validate(item);
        return assembler.toModel(itemRepository.save(item));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable Integer id, @PathVariable Integer listId) {
        getListOrThrow(listId);
        var item = itemRepository.findByIdAndListId(id, listId)
                .orElseThrow(() -> new ListNotFoundException(id));
        itemRepository.delete(item);
        return ResponseEntity.noContent().build();
    }

    private List getListOrThrow(Integer id) {
        return listRepository.findById(id)
                .orElseThrow(() -> new ListNotFoundException(id));
    }
}
