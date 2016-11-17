package com.trailblazers.freewheelers.service.impl;

import com.trailblazers.freewheelers.mappers.ItemMapper;
import com.trailblazers.freewheelers.model.Item;
import com.trailblazers.freewheelers.model.ItemType;
import com.trailblazers.freewheelers.service.ItemService;
import org.apache.ibatis.session.SqlSession;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static java.math.BigDecimal.valueOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ItemServiceTest {

    @Mock
    ItemMapper itemMapper;

    @Mock
    SqlSession sqlSession;

    ItemService itemService;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        when(sqlSession.getMapper(ItemMapper.class)).thenReturn(itemMapper);
        itemService = new ItemService(sqlSession);
    }

    @Test
    public void shouldGetItemByNameFromMapper(){
        String name = "name";
        Item expectedItem = new Item();
        when((itemMapper.getByName(name))).thenReturn(expectedItem);

        Item returnedItem = itemService.getByName(name);
        verify(itemMapper).getByName(name);
        assertThat(returnedItem, is(expectedItem));
    }
    @Test
    public void shouldCreateItemWhenThereAreNoValidationErrors(){
        Item item = getItemWithoutError();
        itemService.saveItem(item);

        verify(itemMapper, times(1)).insert(item);
        verify(sqlSession, times(1)).commit();
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
