package ariefsyaifu.gymmem.subscription.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ariefsyaifu.gymmem.subscription.service.ProductService;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;

    }

    private ProductService productService;

    @GetMapping
    public ResponseEntity<Object> getProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }

}
