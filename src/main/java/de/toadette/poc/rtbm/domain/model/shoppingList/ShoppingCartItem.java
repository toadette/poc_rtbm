package de.toadette.poc.rtbm.domain.model.shoppingList;

import android.location.Location;

/**
 * Created by Melanie on 24.10.2016.
 */
public class ShoppingCartItem {
    private String itemName;
    private Location location;
    private String describtion;

    public ShoppingCartItemBuilder shoppingCartItemBuilder() {
        return new ShoppingCartItemBuilder();
    }

    public class ShoppingCartItemBuilder {
        ShoppingCartItem shoppingCartItem;

        ShoppingCartItemBuilder() {
            shoppingCartItem = new ShoppingCartItem();
        }

        ShoppingCartItemBuilder withItemName(String itemName) {
            shoppingCartItem.itemName = itemName;
            return this;
        }

        ShoppingCartItemBuilder withLocation(Location location) {
            shoppingCartItem.location = location;
            return this;
        }

        ShoppingCartItemBuilder withDescribtion(String describtion) {
            shoppingCartItem.describtion = describtion;
            return this;
        }

        public ShoppingCartItem build() {
//            notNull(itemName);
//            notNull(location);
            return shoppingCartItem;
        }
    }

}
