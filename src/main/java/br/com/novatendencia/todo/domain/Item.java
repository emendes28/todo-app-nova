package br.com.novatendencia.todo.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("item")
public class Item {
	
	@Id private String id;	
	private String description;
	private boolean completed;
	
	
}
