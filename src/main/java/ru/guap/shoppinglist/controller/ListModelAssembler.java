package ru.guap.shoppinglist.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import ru.guap.shoppinglist.model.List;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import static ru.guap.shoppinglist.controller.LinkRel.ITEMS_REL;
import static ru.guap.shoppinglist.controller.LinkRel.LISTS_REL;

@Component
public class ListModelAssembler implements RepresentationModelAssembler<List, EntityModel<List>> {

    @Override
    public EntityModel<List> toModel(List list) {
        return EntityModel.of(list,
                linkTo(methodOn(ListController.class).get(list.getId())).withSelfRel()
                .andAffordance(afford(methodOn(ListController.class).update(null, list.getId())))
                .andAffordance(afford(methodOn(ListController.class).delete(list.getId()))),
                linkTo(methodOn(ListController.class).all()).withRel(LISTS_REL),
                linkTo(methodOn(ItemController.class).all(list.getId())).withRel(ITEMS_REL));
    }

    @Override
    public CollectionModel<EntityModel<List>> toCollectionModel(Iterable<? extends List> lists) {
        return StreamSupport.stream(lists.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        (l) -> CollectionModel.of(l, linkTo(methodOn(ListController.class).all()).withSelfRel()))
                );
    }
}
