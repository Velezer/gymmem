package ariefsyaifu.gymmem.subscription.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ariefsyaifu.gymmem.subscription.model.Product;

public interface ProductRepository extends JpaRepository<Product, String> {

}
