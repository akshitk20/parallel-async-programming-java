package com.learnjava.completeablefuture;

import com.learnjava.domain.Product;
import com.learnjava.service.InventoryService;
import com.learnjava.service.ProductInfoService;
import com.learnjava.service.ProductService;
import com.learnjava.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceUsingCompletableFutureExceptionTest {

    @Mock
    ProductInfoService productInfoService;
    @Mock
    ReviewService reviewService;
    @Mock
    InventoryService inventoryService;

    @InjectMocks
    ProductServiceUsingCompletableFuture productServiceUsingCompletableFuture;

    @Test
    void test() {
        String pid = "ABC";
        when(productInfoService.retrieveProductInfo(any())).thenCallRealMethod();
        when(reviewService.retrieveReviews(any())).thenThrow(new RuntimeException("Exception occurred"));
        when(inventoryService.retrieveInventory(any())).thenCallRealMethod();

        Product product = productServiceUsingCompletableFuture.retrieveProductDetailsWithInventoryAsync(pid);
        assertNotNull(product);
        assertEquals(0,product.getReview().getNoOfReviews());

    }
}