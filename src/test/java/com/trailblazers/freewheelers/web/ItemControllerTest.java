package com.trailblazers.freewheelers.web;

import com.trailblazers.freewheelers.model.Item;
import com.trailblazers.freewheelers.model.ItemGrid;
import com.trailblazers.freewheelers.model.ItemType;
import com.trailblazers.freewheelers.service.ItemService;
import org.apache.ibatis.session.SqlSession;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.Map;

import static java.math.BigDecimal.valueOf;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ItemControllerTest {

    @Mock
    ItemService itemService;
    @Mock
    SqlSession sqlSession;

    Model model;
    ItemGrid itemGrid;
    Item item;
    ItemController itemController;

    @Before
    public void setUp(){
        initMocks(this);
        itemController = new ItemController();
        itemController.itemService = itemService;
        model = new ExtendedModelMap();
        item = new Item();
        itemGrid = new ItemGrid(asList(item));
    }

    @Test
    public void shouldRenderItemListView() throws Exception {
        String returnedPath = itemController.get(model, item);
        assertThat(returnedPath, is(ItemController.ITEM_LIST_PAGE));
    }

    @Test
    public void shouldReturnItemsForDisplay() throws Exception {

        itemGrid = new ItemGrid();
        when(itemService.findAll()).thenReturn(itemGrid.getItems());

        itemController.get(model, item);

        verify(itemService).findAll();
        ItemGrid returnedItemGrid = (ItemGrid) model.asMap().get("itemGrid");
        assertThat(returnedItemGrid.getItems(), is(itemGrid.getItems()));
        assertThat((ItemType[])model.asMap().get("itemTypes"), is(ItemType.values()));

    }

    @Test
    public void shouldDisplayItemsAfterSavingGivenItem(){
        Item item = getItemWithoutError();

        when(itemService.saveItem(item)).thenReturn(item);

        String returnedPath = itemController.post(model, item);

        verify(itemService).saveItem(item);
        assertThat(returnedPath, is("redirect:" + ItemController.ITEM_PAGE));

    }

    @Test
    public void shouldDisplayErrorsIfValidationFailed(){

        Map errors = new HashMap<String, String>();
        errors.put("name", "Please enter Item Name");

        when(itemService.findAll()).thenReturn(asList(item));

        String returnedPath = itemController.post(model, getItemWithError());

        assertThat((HashMap<String, String>) model.asMap().get("errors"), is(errors));
        verify(itemService).findAll();
        ItemGrid returnedItemGrid = (ItemGrid) model.asMap().get("itemGrid");
        assertThat(returnedItemGrid.getItems(), is(itemGrid.getItems()));
        assertThat((ItemType[])model.asMap().get("itemTypes"), is(ItemType.values()));
        assertThat(returnedPath, is(ItemController.ITEM_LIST_PAGE));

    }

    @Test
    public void shouldNotCallServiceWhenValidationFailed() throws Exception {
        itemController.post(model, getItemWithError());

        verify(itemService, never()).saveItem(new Item());
    }

    private Item getItemWithError() {
        Item item = new Item();
        item.setName("")
                .setPrice(valueOf(123.00))
                .setDescription("example")
                .setQuantity((long)123)
                .setType(ItemType.ACCESSORIES);

        return item;
    }


    private Item getItemWithoutError() {
        Item item = new Item();
        item.setName("item")
                .setPrice(valueOf(123.00))
                .setDescription("example")
                .setQuantity((long)123)
                .setType(ItemType.ACCESSORIES);
        return item;
    }

}
