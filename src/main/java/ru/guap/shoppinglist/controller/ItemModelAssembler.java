package ru.guap.shoppinglist.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import ru.guap.shoppinglist.model.Item;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import static ru.guap.shoppinglist.controller.LinkRel.ITEMS_REL;
import static ru.guap.shoppinglist.controller.LinkRel.LIST_REL;

@Component
public class ItemModelAssembler implements RepresentationModelAssembler<Item, EntityModel<Item>> {
    @Override
    public EntityModel<Item> toModel(Item item) {
        return EntityModel.of(item,
                linkTo(methodOn(ItemController.class).get(item.getId(), item.getList().getId())).withSelfRel()
                        .andAffordance(afford(methodOn(ItemController.class).update(null, item.getId(), item.getList().getId())))
                        .andAffordance(afford(methodOn(ItemController.class).delete(item.getId(), item.getList().getId()))),
                linkTo(methodOn(ItemController.class).all(item.getList().getId())).withRel(ITEMS_REL),
                linkTo(methodOn(ListController.class).get(item.getList().getId())).withRel(LIST_REL)
        );
    }

    public CollectionModel<EntityModel<Item>> toCollectionModel(Iterable<? extends Item> items, int id) {
        return StreamSupport.stream(items.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        (i) -> CollectionModel.of(i, linkTo(methodOn(ItemController.class).all(id)).withSelfRel()))
                );

    }
}
