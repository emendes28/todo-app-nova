package br.com.novatendencia.todo.controller;
// #region Imports

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	ResponseEntity<List<Item>> getAll(HttpServletRequest request) {
		List<Item> itens = itemRepository.findAll();
	    return ResponseEntity
	    		.status(HttpStatus.OK)
	            .body(itens);
	}

	@GetMapping("/{title}")
	ResponseEntity<List<Item>> getByTitle(@PathVariable String title) {
		Optional<List<Item>> itemByTitle = itemRepository.findByTitleLike(title);
		if (title == null  &&  "".equals(title)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		if (!itemByTitle.isPresent()) {			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	    return ResponseEntity
	    		.status(HttpStatus.OK)
	            .body(itemByTitle.get());
	}

	@PutMapping("/{id}")
	ResponseEntity<Item> update(@PathVariable String id, @RequestBody Item item) {

		if (id == null  &&  "".equals(id)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Optional<Item> itemForId = itemRepository.findById(id);
		if (!itemForId.isPresent()) {			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(itemRepository.save(item));
	}

	@PatchMapping("/{id}")
	ResponseEntity<Item> updatePartial(@PathVariable String id, @RequestBody boolean status) {

		if (id == null  &&  "".equals(id)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Optional<Item> item = itemRepository.findById(id);
		if (!item.isPresent()) {			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Item itemFound = item.get();
		itemFound.completed = status;
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(itemRepository.save(itemFound));
	}

	@PostMapping("/")
	ResponseEntity<Item> save(@RequestBody Item item) {
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(itemRepository.save(item));
	}

	@DeleteMapping("/{id}")
	ResponseEntity<Item> delete(@PathVariable String id) {
		if (id == null  &&  "".equals(id)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Optional<Item> item = itemRepository.findById(id);
		if (!item.isPresent()) {			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		itemRepository.delete(item.get());
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(item.get());
	}
}
