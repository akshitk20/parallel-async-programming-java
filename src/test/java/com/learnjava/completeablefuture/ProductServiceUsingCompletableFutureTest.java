package com.learnjava.completeablefuture;

import com.learnjava.domain.Product;
import com.learnjava.service.InventoryService;
import com.learnjava.service.ProductInfoService;
import com.learnjava.service.ReviewService;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceUsingCompletableFutureTest {

    private ProductInfoService productInfoService = new ProductInfoService();
    private ReviewService reviewService = new ReviewService();
    private InventoryService inventoryService = new InventoryService();
    ProductServiceUsingCompletableFuture pscf = new ProductServiceUsingCompletableFuture(productInfoService,reviewService,inventoryService);

    @Test
    void test(){
       String productId = "ABC123";

        Product product = pscf.retrieveProductDetails(productId);

        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size()>0);
    }

    @Test
    void retrieveProductDetails_approach2(){
        String productId = "ABC123";
        CompletableFuture<Product> productCompletableFuture = pscf.retrieveProductDetails_approach2(productId);

        productCompletableFuture.thenAccept(s->{
            assertTrue(s.getProductInfo().getProductOptions().size() > 0);
            assertTrue(s.getReview().getNoOfReviews()!= 0);
        }).join();
    }

    @Test
    void retrieveProductDetails_inventory(){
        String productId = "ABC123";
        Product product = pscf.retrieveProductDetailsWithInventory(productId);

        assertTrue(product.getProductInfo().getProductOptions().size() > 0);
        assertTrue(product.getReview().getNoOfReviews()!= 0);
        assertNotNull(product.getProductInfo().getProductOptions().get(0).getInventory());
    }



}