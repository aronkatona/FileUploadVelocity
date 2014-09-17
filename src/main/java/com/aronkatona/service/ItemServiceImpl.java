package com.aronkatona.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.aronkatona.dao.ItemDAO;
import com.aronkatona.model.Item;

public class ItemServiceImpl implements ItemService{
	
	private ItemDAO itemDAO;

	public void setItemDAO(ItemDAO itemDAO) {
		this.itemDAO = itemDAO;
	}

	@Override
	@Transactional
	public void saveItem(Item i) {
		this.itemDAO.saveItem(i);
		
	}

	@Override
	@Transactional
	public void updateItem(Item i) {
		this.itemDAO.updateItem(i);
	}

	@Override
	@Transactional
	public List<Item> getItems() {
		return this.itemDAO.getItems();
	}

	@Override
	@Transactional
	public Item getItemById(int id) {
		return this.itemDAO.getItemById(id);
	}

	@Override
	@Transactional
	public void removeItem(int id) {
		this.itemDAO.removeItem(id);
	}

	@Override
	@Transactional
	public Item getItemByImgAddress(String fileName) {
		return this.itemDAO.getItemByImgAddress(fileName);
	}

}
