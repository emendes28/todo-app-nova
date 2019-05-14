package br.com.novatendencia.todo.controller;
// #region Imports

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.novatendencia.todo.domain.Item;
import br.com.novatendencia.todo.service.IItemService;
import br.com.novatendencia.todo.repository.ItemRepository;

import io.swagger.annotations.Api;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Optional;

// #endregion

@RestController
@CrossOrigin("*")
@RequestMapping("/v2/item")
@Api(value = "Todo Reactive App", tags = { "TODO" })
class ItemV2Controller {

	@Autowired
	private IItemService itemService;

	@Autowired
	private ItemRepository itemRepository;

	@GetMapping(path = "/",
			produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Item> getAll(HttpServletRequest request) {
		return itemService.findAllStream();
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
}
