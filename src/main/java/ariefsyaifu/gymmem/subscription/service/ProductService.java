package ariefsyaifu.gymmem.subscription.service;

import java.util.List;

import org.springframework.stereotype.Component;

import ariefsyaifu.gymmem.subscription.dto.product.ViewProductDto;
import ariefsyaifu.gymmem.subscription.model.Product;
import ariefsyaifu.gymmem.subscription.repository.ProductRepository;

@Component
public class ProductService {

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    private ProductRepository productRepository;

    public List<ViewProductDto> getProducts() {
        return productRepository.findAll().stream().map(p -> ViewProductDto.valueOf(p)).toList();
    }

    public Product getProductById(String productId) {
        return productRepository.findById(productId).orElse(null);
    }
}
