package br.com.eshop.api.service;

import br.com.eshop.api.payload.ProductDTO;
import br.com.eshop.api.payload.ProductReponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {
    ProductDTO addProduct(ProductDTO product, Long categoryId);

    ProductReponse getAllProducts();

    ProductReponse getProductsByCategory(Long categoryId);

    ProductReponse getProductsByKeyword(String keyword);

    ProductDTO updateProduct(ProductDTO product, Long productId);

    ProductDTO deleteProduct(Long productId);

    ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException;
}
