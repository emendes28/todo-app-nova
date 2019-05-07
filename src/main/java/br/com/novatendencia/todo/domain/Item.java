package br.com.novatendencia.todo.domain;

import org.springframework.data.annotation.Id;

public class Item {
	@Id public String id;	
	public String title;
	public int userId = 1;
	public boolean completed;
	
}
