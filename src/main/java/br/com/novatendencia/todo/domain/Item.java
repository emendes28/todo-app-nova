package br.com.novatendencia.todo.domain;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
	
	@Id private String id;	
	private String title;
	private int userId = 1;
	private boolean completed;
	
}
