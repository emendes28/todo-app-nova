package br.com.novatendencia.todo.controller;
// #region Imports

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
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
import io.swagger.annotations.Api;

// #endregion
@RestController
@CrossOrigin("*")
@RequestMapping("/item")
@Api(value = "Todo App", tags = { "TODO" })
class ItemController {

	@Autowired
	private ItemRepository itemRepository;

	static final String URI = "https://jsonplaceholder.typicode.com/todos?_limit=10";

	@PostConstruct
	void carregaDados() {

		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(URI, String.class);

		Type itensType = new TypeToken<ArrayList<Item>>() {
		}.getType();
		List<Item> itensApi = new Gson().fromJson(result, itensType);

		itemRepository.saveAll(itensApi);
	}
	@GetMapping("/")
	ResponseEntity<Iterable<Item>> getAll() {

		return ResponseEntity.ok(itemRepository.findAll());
	}

	@GetMapping("/{id}")
	ResponseEntity<Item> getById(@PathVariable String id) {
		Optional<Item> item = itemRepository.findById(id);
		return ResponseEntity.ok(item.get());
	}

	@GetMapping("/{title}")
	Item getByTitle(@PathVariable String title) {
		Optional<Item> itemByTitle = itemRepository.findByTitle(title);
		return itemByTitle.get();
	}

	@PutMapping("/{id}")
	BodyBuilder update(@PathVariable String id, @RequestBody Item item) {

		if (id == null  &&  "".equals(id)) {
			return ResponseEntity.badRequest();
		}
		Optional<Item> itemForId = itemRepository.findById(id);
		if (!itemForId.isPresent()) {			
			return ResponseEntity.badRequest();
		}
		itemRepository.save(item);
		return ResponseEntity.ok();
	}

	@PatchMapping("/{id}")
	BodyBuilder updatePartial(@PathVariable String id, @RequestBody boolean status) {

		if (id == null  &&  "".equals(id)) {
			return ResponseEntity.badRequest();
		}
		Optional<Item> item = itemRepository.findById(id);
		if (!item.isPresent()) {			
			return ResponseEntity.badRequest();
		}
		itemRepository.save(item.get());
		return ResponseEntity.ok();
	}

	@PostMapping("/")
	BodyBuilder save(@RequestBody Item item) {
		 itemRepository.save(item);
		return ResponseEntity.ok();
	}

	@DeleteMapping("/{id}")
	BodyBuilder delete(@PathVariable String id) {
		if (id == null  &&  "".equals(id)) {
			return ResponseEntity.badRequest();
		}
		Optional<Item> item = itemRepository.findById(id);
		if (!item.isPresent()) {			
			return ResponseEntity.badRequest();
		}
		itemRepository.delete(item.get());
		return ResponseEntity.ok();
	}
}
