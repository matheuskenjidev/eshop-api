package br.com.eshop.api.payload;

import br.com.eshop.api.model.Product;
import br.com.eshop.api.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductReponse {
    private List<ProductDTO> content;
}
