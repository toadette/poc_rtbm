package de.toadette.poc.rtbm.domain.model.shoppingList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Melanie on 24.10.2016.
 */

public class ShoppingList {
    private List<ShoppingCartItem> list = new ArrayList<>();

    public class ShoppingListBuilder {
        private ShoppingList shoppingList;

        ShoppingListBuilder() {
            shoppingList = new ShoppingList();
        }

        public ShoppingListBuilder addShoppingCartItem(ShoppingCartItem shoppingCartItem) {
            shoppingList.list.add(shoppingCartItem);
            return this;
        }

        public ShoppingList build() {
            return shoppingList;
        }
    }

}
