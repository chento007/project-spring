package co.istad.photostad.api.product.web;

import co.istad.photostad.api.product.Product;
import co.istad.photostad.api.product.ProductService;
import co.istad.photostad.base.BaseRest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public BaseRest<?> findALl(){

        List<Product> products = productService.findAll();

        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Product has been find success.")
                .timestamp(LocalDateTime.now())
                .data(products)
                .build();
    }

    @PostMapping
    public BaseRest<?> insert(@RequestBody ProductDto productDto){

        Product product = productService.insertProduct(productDto);

        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Product has been insert success.")
                .timestamp(LocalDateTime.now())
                .data(product)
                .build();
    }
}
