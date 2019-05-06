package br.com.novatendencia.todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.novatendencia.todo.domain.Item;
import br.com.novatendencia.todo.repository.ItemRepository;

@RestController
@RequestMapping("/item")
class ItemController {
	
	@Autowired
	private ItemRepository itemRepository;
	
	@GetMapping("/")
	Iterable<Item> getAll() {
		return itemRepository.findAll();		
	}
	
	@GetMapping("/{id}")
	Item getById(@PathVariable String id) {
		return itemRepository
				.findById(id)
				.orElse(new Item()); 
	}

	@PutMapping("/{id}")
	void update(@PathVariable String id, Item item) {
		itemRepository.save(item);
	}
	
	@PatchMapping("/{id}")
	void updatePartial(@PathVariable String id, Item item) {
		itemRepository.save(item);
	}


	@PostMapping("/")
	void save(Item item) {
		itemRepository.save(item);
	}
	

	@DeleteMapping("/{id}")
	void delete(@PathVariable String id) {
		itemRepository.delete(itemRepository.findById(id).orElse(new Item()));
	}
}
