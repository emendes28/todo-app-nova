package br.com.novatendencia.todo.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.novatendencia.todo.domain.Item;

public interface ItemRepository extends CrudRepository<Item, String> {

}
