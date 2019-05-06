package br.com.novatendencia.todo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.novatendencia.todo.domain.Item;

public interface ItemRepository extends MongoRepository<Item, String> {

}
