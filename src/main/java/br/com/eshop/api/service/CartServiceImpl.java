package br.com.eshop.api.service;

import br.com.eshop.api.exceptions.APIException;
import br.com.eshop.api.exceptions.ResourceNotFoundException;
import br.com.eshop.api.model.Cart;
import br.com.eshop.api.model.CartItem;
import br.com.eshop.api.model.Product;
import br.com.eshop.api.payload.CartDTO;
import br.com.eshop.api.payload.ProductDTO;
import br.com.eshop.api.repository.CartItemRepository;
import br.com.eshop.api.repository.CartRepository;
import br.com.eshop.api.repository.ProductRepository;
import br.com.eshop.api.util.AuthUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private AuthUtil authUtil;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CartDTO addProductToCart(Long productId, Integer quantity) {
        Cart cart = createCart();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(cart.getCartId(), productId);
        if(cartItem != null) {
            throw new APIException("O Produto " + product.getProductName() + " ja esta no seu carrinho!!!");
        }

        if(product.getQuantity() == 0) {
            throw new APIException(product.getProductName() + " nao tem estoque disponivel para compra");
        }

        if(product.getQuantity() < quantity) {
            throw new APIException(product.getProductName() + " nao possui essa quantidade: " + quantity + " em estoque. Diminua a quantidade em ate " + product.getQuantity() + " para efetuar a compra");
        }

        CartItem newCartItem = new CartItem();
        newCartItem.setProduct(product);
        newCartItem.setCart(cart);
        newCartItem.setQuantity(quantity);
        newCartItem.setDiscount(product.getDiscount());
        newCartItem.setProductPrice(product.getSpecialPrice());

        cartItemRepository.save(newCartItem);

        product.setQuantity(product.getQuantity());

        cart.setTotalPrice(cart.getTotalPrice() + (product.getSpecialPrice() * quantity));

        cartRepository.save(cart);

        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

        List<CartItem> cartItems = cart.getCartItems();

        Stream<ProductDTO> productDTOStream = cartItems.stream().map(item -> {
            ProductDTO map = modelMapper.map(item.getProduct(), ProductDTO.class);
            map.setQuantity(item.getQuantity());
            return map;
        });

        cartDTO.setProducts(productDTOStream.toList());

        return cartDTO;
    }

    private Cart createCart() {
        Cart userCart = cartRepository.findCartByEmail(authUtil.loggedInEmail());
        if(userCart != null) {
            return userCart;
        }

        Cart cart = new Cart();
        cart.setTotalPrice(0.00);
        cart.setUser(authUtil.loggedInUser());
        Cart newCart = cartRepository.save(cart);
        return newCart;
    }
}
