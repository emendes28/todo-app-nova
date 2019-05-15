package br.com.novatendencia.todo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.novatendencia.todo.domain.Item;

public interface ItemRepository extends MongoRepository<Item, String> {

	public Optional<List<Item>> findByTitleLike(String title);
	
}
