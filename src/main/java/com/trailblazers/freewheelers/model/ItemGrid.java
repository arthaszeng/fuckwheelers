package com.trailblazers.freewheelers.model;

import com.trailblazers.freewheelers.model.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemGrid {
	
	private Map<Long, Item> itemMap;

    public ItemGrid() {
        itemMap = new HashMap<Long, Item>();
    }
	                                             
	public ItemGrid(List<Item> items) {
        this();
        for (Item item : items) {
            itemMap.put(item.getItemId(), item);

        }
    }
	
	public List<Item> getItems() {
		return new ArrayList<Item>(itemMap.values());
	}

	public Map<Long, Item> getItemMap() {
		return itemMap;
	}

	public void setItemMap(Map<Long, Item> items) {
		this.itemMap = items;
	}
	
}
