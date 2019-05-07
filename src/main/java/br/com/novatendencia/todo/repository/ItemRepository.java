package br.com.novatendencia.todo.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.novatendencia.todo.domain.Item;

public interface ItemRepository extends MongoRepository<Item, String> {

	public Optional<Item> findByTitle(String title);
	
}
