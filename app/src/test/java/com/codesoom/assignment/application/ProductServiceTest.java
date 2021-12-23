package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("Product Service")
class ProductServiceTest {
    //1. list   -> getProducts
    //2. detail -> getProduct (with ID)
    //3. create -> createProduct (with source)
    //4. update -> updateProduct (with ID, source)
    //5. delete -> deleteProduct (with ID)

    private static final String NAME = "TestName ";
    private static final String MAKER = "TestMaker";
    private static final long PRICE = 1000;
    private static final String IMAGE_URL = "http://gdimg.gmarket.co.kr/1482334965/still/600?ver=1535083811";

    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp(){
        productService = new ProductService(productRepository);

        Product product = new Product(NAME, MAKER, PRICE, IMAGE_URL);

        productService.createProduct(product);
    }

    @Nested
    @DisplayName("getProducts 메소드는")
    class Describe_getProducts{
        
        @Test
        @DisplayName("저장되어 있는 제품 리스트를 리턴합니다.")
        void it_returns_products(){
            List<Product> products = productService.getProducts();
            assertThat(products).hasSize(1);

            Product product = products.get(0);
            assertThat(product.getName()).isEqualTo(NAME);
            assertThat(product.getMaker()).isEqualTo(MAKER);
            assertThat(product.getPrice()).isEqualTo(PRICE);
            assertThat(product.getImageUrl()).isEqualTo(IMAGE_URL);
        }
    }
}
