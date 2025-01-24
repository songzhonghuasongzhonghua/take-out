package com.song.service;

import com.song.dto.ShoppingCartDTO;
import com.song.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {

    void addShoppingCart(ShoppingCartDTO shoppingCartDTO);

    List<ShoppingCart> getShoppingCart();

    void clean();
}
