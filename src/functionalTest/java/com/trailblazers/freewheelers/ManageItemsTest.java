package com.trailblazers.freewheelers;

import org.junit.Ignore;
import org.junit.Test;

import static com.trailblazers.freewheelers.helpers.SyntaxSugar.*;

@Ignore
public class ManageItemsTest extends UserJourneyBase {

    @Test
    public void adminJourneyTest() throws Exception {
        String arno = "Arno Admin";

        String simplon_frame = "Simplon Pavo 3 Ultra";

        String new_simplon_name = "NEW - Simplon Pavo 3 Ultra";

        admin
                .there_is_an_admin(arno, SOME_PASSWORD)
                .there_is_no_item(simplon_frame)
                .there_is_no_item(new_simplon_name);
        user
                .logs_in_with(arno, SOME_PASSWORD)
                .visits_manage_items_page();

        user
                .creates_an_item(simplon_frame, "FRAME", NO_QUANTITY, REALLY_EXPENSIVE, SOME_DESCRIPTION);

        screen
                .shows_error("Please enter Item Quantity", "quantity_field");

        user
                .creates_an_item(simplon_frame, "FRAME", A_LOT, REALLY_EXPENSIVE, SOME_DESCRIPTION);

        screen
                .shows_in_manage_item_list(simplon_frame);

        user
                .changes_item_name(from(simplon_frame), to(new_simplon_name));

        screen
                .shows_in_manage_item_list(new_simplon_name);

        user
                .delete_item(new_simplon_name);

        screen
                .shows_not_in_manage_item_list(new_simplon_name);
    }


}
