package br.com.novatendencia.todo.controller;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import br.com.novatendencia.todo.domain.Item;
import br.com.novatendencia.todo.repository.ItemRepository;

@RestController
@CrossOrigin("*")
@RequestMapping("/item")
class ItemController {

	@Autowired
	private ItemRepository itemRepository;

	static final String URI = "https://jsonplaceholder.typicode.com/todos?_limit=10";
	
	@PostConstruct
	void carregaDados() {

		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(URI, String.class);

		Type listType = new TypeToken<ArrayList<Item>>() {}.getType();
		List<Item> yourClassList = new Gson().fromJson(result, listType);

		itemRepository.saveAll(yourClassList);
	}
	
	@GetMapping("/")
	Iterable<Item> getAll() {

		return itemRepository.findAll();
	}

	@GetMapping("/{id}")
	Item getById(@PathVariable String id) {
		return itemRepository.findById(id).orElse(new Item());
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
	void save(@RequestBody Item item) {
		itemRepository.save(item);
	}

	@DeleteMapping("/{id}")
	void delete(@PathVariable String id) {
		itemRepository.delete(itemRepository.findById(id).orElse(new Item()));
	}
}
