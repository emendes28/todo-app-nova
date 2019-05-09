package br.com.novatendencia.todo.controller;
// #region Imports

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.novatendencia.todo.domain.Item;
import br.com.novatendencia.todo.service.IItemService;
import io.swagger.annotations.Api;
import reactor.core.publisher.Flux;

// #endregion

@RestController
@CrossOrigin("*")
@RequestMapping("/v2/item")
@Api(value = "Todo Reactive App", tags = { "TODO" })
class ItemV2Controller {

	@Autowired
	private IItemService itemService;

	@GetMapping(path = "/",
			produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Item> getAll(HttpServletRequest request) {
		return itemService.findAllStream();
	}
}
