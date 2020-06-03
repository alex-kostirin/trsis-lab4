package ru.guap.shoppinglist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.guap.shoppinglist.model.List;

public interface ListRepository extends JpaRepository<List, Integer> {
}


