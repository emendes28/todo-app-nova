package br.com.novatendencia.todo.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.novatendencia.todo.domain.Item;

public interface ItemRepository extends MongoRepository<Item, String> {

	/**
	 * Search an item in dabase with title
	 * 
	 * @param title an String that contains the value of title
	 * @return Optional<Item> retorns or not the item
	 */
	public Optional<Item> findByTitle(String title);
	
}
