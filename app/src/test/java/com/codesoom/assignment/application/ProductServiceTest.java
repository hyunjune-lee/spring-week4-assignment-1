package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.dto.ProductNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@DisplayName("Product Service")
class ProductServiceTest {

    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {

        productService = new ProductService(productRepository);

        product1 = new Product(0L, "catTower", "samsung", 35900L, "https://thumbnail14.coupangcdn.com/thumbnails/remote/712x712ex/image/retail/images/451976858609946-e5186418-5f5e-4f4c-bccc-a59ac573d029.jpg");
        product2 = new Product(1L, "catBall", "love cat", 8000L, "http://mstatic1.e-himart.co.kr/contents/goods/00/05/96/13/20/0005961320__TB10__M_640_640.jpg");
    }

    @AfterEach
    public void afterEach() {
        productRepository.deleteAll();
    }

    @Nested
    @DisplayName("getProducts 메소드는")
    class Describe_getProducts {

        List<Product> testProducts;

        @BeforeEach
        void setUp() {
            testProducts = new ArrayList<>();
            testProducts.add(product1);
            testProducts.add(product2);

            for (Product testProduct : testProducts) {
                productService.createProduct(testProduct);
            }
        }

        @Test
        @DisplayName("저장되어 있는 product 리스트를 응답합니다.")
        void it_returns_products() {

            List<Product> products = productService.getProducts();

            assertThat(products).hasSize(testProducts.size());

            for (int i = 0; i < products.size(); i++) {
                Product product = products.get(i);
                assertThat(product).isEqualTo(testProducts.get(i));
            }
        }
    }

    @Nested
    @DisplayName("getProductById 메소드는")
    class Describe_getProductById {

        @Nested
        @DisplayName("만약 조회하는 id의 product가 존재한다면")
        class Context_with_exist_id {

            private Long existId;
            private Product givenProduct;

            @BeforeEach
            void setUp() {
                givenProduct = productService.createProduct(product1);
                existId = givenProduct.getId();
            }

            @Test
            @DisplayName("해당하는 id의 product를 응답합니다.")
            void it_returns_product() {
                Product product = productService.getProductById(existId);
                assertThat(product).isEqualTo(product1);
            }
        }

        @Nested
        @DisplayName("만약 조회하는 id의 product가 존재하지 않는다면")
        class Context_with_not_exist_id {

            Long notExistId;

            @BeforeEach
            void setUp() {
                productService.createProduct(product1);
                notExistId = product2.getId();
            }

            @Test
            @DisplayName("ProductNotFoundException란 예외를 던집니다.")
            void it_throw_ProductNotFoundException() {
                assertThatThrownBy(() -> productService.getProductById(notExistId))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("createProduct 메소드는")
    class Describe_createProduct {

        Product newProduct;

        @BeforeEach
        void setUp() {
            newProduct = productService.createProduct(product1);
        }

        @Test
        @DisplayName("요청한 내용으로 저장된 product를 응답합니다.")
        void it_returns_new_product() {
            assertThat(newProduct).isEqualTo(product1);
        }
    }

    @Nested
    @DisplayName("updateProduct 메소드는")
    class Describe_updateProduct {

        @Nested
        @DisplayName("만약 조회하는 id의 product가 존재한다면")
        class Context_with_exist_id {

            private Long existId;

            @BeforeEach
            void setUp() {
                Product givenProduct = productService.createProduct(product1);
                existId = givenProduct.getId();
            }

            @Test
            @DisplayName("해당하는 id의 product를 주어진 요청대로 수정하여 응답합니다.")
            void it_returns_product() {
                Product updatedProduct = productService.updateProduct(existId, product2);
                assertThat(updatedProduct).isEqualTo(product2);
            }
        }

        @Nested
        @DisplayName("만약 조회하는 id의 product가 존재하지 않는다면")
        class Context_with_not_exist_id {

            Long notExistId;

            @BeforeEach
            void setUp() {
                productService.createProduct(product1);
                notExistId = product2.getId();
            }

            @Test
            @DisplayName("ProductNotFoundException란 예외를 던집니다.")
            void it_throw_ProductNotFoundException() {
                assertThatThrownBy(() -> productService.updateProduct(notExistId, product2))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("deleteProductById 메소드는")
    class Describe_deleteProductById {

        @Nested
        @DisplayName("만약 조회하는 id의 product가 존재한다면")
        class Context_with_exist_id {

            private Long existId;

            @BeforeEach
            void setUp() {
                Product givenProduct = productService.createProduct(product1);
                existId = givenProduct.getId();
            }

            @Test
            @DisplayName("해당하는 id의 product를 삭제합니다.")
            void it_delete_product() {
                productService.deleteById(existId);
                assertThatThrownBy(() -> productService.getProductById(existId))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }

        @Nested
        @DisplayName("만약 조회하는 id의 product가 존재하지 않는다면")
        class Context_with_not_exist_id {

            Long notExistId;

            @BeforeEach
            void setUp() {
                Product givenProduct = productService.createProduct(product1);
                notExistId = givenProduct.getId();
                productService.deleteById(notExistId);
            }

            @Test
            @DisplayName("ProductNotFoundException란 예외를 던집니다.")
            void it_throw_ProductNotFoundException() {
                assertThatThrownBy(() -> productService.deleteById(notExistId))
                        .isInstanceOf(ProductNotFoundException.class);

            }
        }
    }
}
