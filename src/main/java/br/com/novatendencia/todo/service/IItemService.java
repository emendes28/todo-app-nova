package br.com.novatendencia.todo.service;

import br.com.novatendencia.todo.domain.Item;
import reactor.core.publisher.Flux;

public interface IItemService {

    /**
     * Retorna todos os itens modificados
     * 
     * @return Flux<Item> um Stream de eventos do Item
     */
    public Flux<Item> findAllStream();
    
}
