package ru.guap.shoppinglist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.guap.shoppinglist.model.Item;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Integer> {
//    @Query("select i from items i where i.list.id = ?1")
    Optional<Item> findByIdAndListId(Integer id, Integer listId);
}
