package com.trailblazers.freewheelers.web;

import com.trailblazers.freewheelers.configuration.ToggledFeature;
import com.trailblazers.freewheelers.model.Item;
import com.trailblazers.freewheelers.service.ItemService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static java.util.Arrays.asList;
import static junit.framework.Assert.assertFalse;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class HomeControllerTest {
    @Mock
    ItemService itemService;

    @Mock
    RedirectAttributes redirectAttributes;

    HomeController homeController;
    Model model;
    Item item, item1;
    MockHttpSession session;

    @Before
    public void setUp() {
        initMocks(this);
        homeController = new HomeController(itemService);
        model = new ExtendedModelMap();
        item = new Item();
        item1 = new Item();
        model = new ExtendedModelMap();
        session = new MockHttpSession();
    }

    @Test
    public void shouldAddItemToSession() throws Exception {

        if (ToggledFeature.SHOPPING_CART.isActive()) {
            when(itemService.get(item.getItemId())).thenReturn(item);

            homeController.addToCart(model, item, redirectAttributes, session);

            assertTrue(model.containsAttribute("items"));
            assertEquals(asList(item), session.getAttribute("items"));
        } else {
            assertFalse(model.containsAttribute("items"));
        }

    }

    @Test
    public void shouldAddMultipleItemToSession() throws Exception {
        when(itemService.get(item.getItemId())).thenReturn(item);
        homeController.addToCart(model, item, redirectAttributes, session);
        when(itemService.get(item.getItemId())).thenReturn(item1);

        homeController.addToCart(model, item, redirectAttributes, session);

        assertTrue(model.containsAttribute("items"));
        assertEquals(asList(item, item1), session.getAttribute("items"));
    }

    @Test
    public void shouldDisplayAddToCartMessage() {
        item.setName("Simplex");
        when(itemService.get(item.getItemId())).thenReturn(item);
        homeController.addToCart(model, item, redirectAttributes, session);

        verify(redirectAttributes).addFlashAttribute("message", "Simplex has been added to your cart.");
    }


}
