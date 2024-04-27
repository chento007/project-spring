package co.istad.photostad.api.product;

import co.istad.photostad.api.product.web.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{


    private  final ProductMapper productMapper;
    private  final ProductMapstruct productMapstruct;

    @Override
    public Product insertProduct(ProductDto productDto) {

        Product product = new Product();
        product.setName(productDto.name());
        product.setDescription(productDto.description());
        product.setPrice(productDto.price());

        return productMapper.insert(product);
    }

    @Override
    public List<Product> findAll() {
        return productMapper.findAll();
    }
}
