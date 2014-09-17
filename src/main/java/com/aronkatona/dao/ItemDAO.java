package com.aronkatona.dao;

import java.util.List;

import com.aronkatona.model.Item;


public interface ItemDAO {
	
	public void saveItem(Item  i);
	public void updateItem(Item i);
	public List<Item> getItems();
	public Item getItemById(int id);
	public void removeItem(int id);
	public Item getItemByImgAddress(String fileName);

}
