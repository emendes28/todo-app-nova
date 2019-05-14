package br.com.novatendencia.todo.controller;
// #region Imports

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
import org.springframework.web.reactive.function.client.WebClient;

import br.com.novatendencia.todo.domain.Item;
import br.com.novatendencia.todo.repository.ItemRepository;
import io.swagger.annotations.Api;
import reactor.core.publisher.Flux;

// #endregion

@RestController
@CrossOrigin("*")
@RequestMapping("/item")
@Api(value = "Todo App", tags = { "TODO" })
class ItemController {

	@Autowired
	private ItemRepository itemRepository;

	static final String URI = "https://jsonplaceholder.typicode.com";

	@PostConstruct
	void carregaDados() {
		WebClient client = WebClient.create(URI);
		Flux<Item> itens = client.get()
				  .uri("/todos?_limit={qtd}", "10")
				  .retrieve()
				  .bodyToFlux(Item.class);
		itens.subscribe(itemRepository::save);
	}
	
	@GetMapping("/")
	ResponseEntity<List<Item>> getAll(HttpServletRequest request) {
		List<Item> itens = itemRepository.findAll();
	    return ResponseEntity
	    		.status(HttpStatus.OK)
	            .body(itens);
	}

	@GetMapping("/{title}")
	ResponseEntity<Item> getByTitle(@PathVariable String title) {
		Optional<Item> itemByTitle = itemRepository.findByTitle(title);
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
	ResponseEntity update(@PathVariable String id, @RequestBody Item item) {

		if (id == null  &&  "".equals(id)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Optional<Item> itemForId = itemRepository.findById(id);
		if (!itemForId.isPresent()) {			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		itemRepository.save(item);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PatchMapping("/{id}")
	ResponseEntity updatePartial(@PathVariable String id, @RequestBody boolean status) {

		if (id == null  &&  "".equals(id)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Optional<Item> item = itemRepository.findById(id);
		if (!item.isPresent()) {			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Item itemFound = item.get();
		itemFound.completed = status;
		itemRepository.save(itemFound);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/")
	ResponseEntity save(@RequestBody Item item) {
		itemRepository.save(item);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	ResponseEntity delete(@PathVariable String id) {
		if (id == null  &&  "".equals(id)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Optional<Item> item = itemRepository.findById(id);
		if (!item.isPresent()) {			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		itemRepository.delete(item.get());
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
