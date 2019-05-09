package br.com.novatendencia.todo.service;

import java.time.Duration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.novatendencia.todo.domain.Item;
import br.com.novatendencia.todo.repository.ItemRepository;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

@Service
public class ItemServiceImpl implements IItemService {

	@Autowired
	private ItemRepository itemRepository;

	@Override
	public Flux<Item> findAllStream() {
		List<Item> itens = itemRepository.findAll();
		Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));
		Flux<Item> stockTransactionFlux = Flux.fromStream(itens.stream());
		return Flux.zip(interval, stockTransactionFlux).map(Tuple2::getT2);
	}

}
