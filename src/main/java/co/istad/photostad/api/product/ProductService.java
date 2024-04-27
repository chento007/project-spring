package co.istad.photostad.api.product;

import co.istad.photostad.api.product.web.ProductDto;

import java.util.List;

public interface ProductService {

    Product insertProduct(ProductDto productDto);

    List<Product> findAll();
}
