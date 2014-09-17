package com.aronkatona.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.aronkatona.model.Item;

public class ItemDAOImpl implements ItemDAO {
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}

	@Override
	public void saveItem(Item i) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(i);
	}

	@Override
	public void updateItem(Item i) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(i);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Item> getItems() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Item> itemsList = session.createQuery("from Item").list();
		return itemsList;
		}

	@Override
	public Item getItemById(int id) {
		Session session = this.sessionFactory.getCurrentSession();		
		Item i = (Item) session.get(Item.class, new Integer(id));
		return i;
	}

	@Override
	public void removeItem(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Item i = (Item) session.get(Item.class, new Integer(id));
		if(i != null){
			session.delete(i);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Item getItemByImgAddress(String fileName) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Item where imgAddress = :fileName");
		query.setParameter("fileName", fileName);		
		List<Item> itemList = query.list();
		return itemList.get(0);
	}

}
