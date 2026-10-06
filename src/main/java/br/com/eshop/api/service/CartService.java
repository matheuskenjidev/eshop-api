package br.com.eshop.api.service;

import br.com.eshop.api.payload.CartDTO;

public interface CartService {
    CartDTO addProductToCart(Long productId, Integer quantity);
}
