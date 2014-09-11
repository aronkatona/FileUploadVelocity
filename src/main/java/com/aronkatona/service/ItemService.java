package com.aronkatona.service;

import java.util.List;

import com.aronkatona.model.Item;


public interface ItemService {
	
	public void saveItem(Item  i);
	public void updateItem(Item i);
	public List<Item> getItems();
	public Item getItemById(int id);
	public void removeItem(int id);

}
