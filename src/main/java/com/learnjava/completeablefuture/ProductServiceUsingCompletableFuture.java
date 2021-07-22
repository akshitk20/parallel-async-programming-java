package com.learnjava.completeablefuture;

import com.learnjava.domain.*;
import com.learnjava.service.InventoryService;
import com.learnjava.service.ProductInfoService;
import com.learnjava.service.ReviewService;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.learnjava.util.CommonUtil.stopWatch;
import static com.learnjava.util.LoggerUtil.log;

public class ProductServiceUsingCompletableFuture {
    private ProductInfoService productInfoService;
    private ReviewService reviewService;
    private InventoryService inventoryService;

    public ProductServiceUsingCompletableFuture(ProductInfoService productInfoService, ReviewService reviewService,InventoryService inventoryService) {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
        this.inventoryService = inventoryService;
    }

    public ProductServiceUsingCompletableFuture(ProductInfoService productInfoService, ReviewService reviewService) {
    }

    public Product retrieveProductDetails(String productId) {
        stopWatch.start();

        CompletableFuture<ProductInfo> productInfoCompletableFuture = CompletableFuture.supplyAsync(() -> productInfoService.retrieveProductInfo(productId));
        CompletableFuture<Review> reviewCompletableFuture = CompletableFuture.supplyAsync(() -> reviewService.retrieveReviews(productId));

        Product product = productInfoCompletableFuture
                .thenCombine(reviewCompletableFuture, (pr, review) -> new Product(productId, pr, review))
                .join(); //block thread only once even though we have two completable futures

        stopWatch.stop();
        log("Total Time Taken : "+ stopWatch.getTime());
        return product;
    }

    public CompletableFuture<Product> retrieveProductDetails_approach2(String productId) {
        //code for server based application. just return completable future. no need to do join which will block the thread. it is the responsiblity of client/function calling
        //this method to handle this completable future.
        CompletableFuture<ProductInfo> productInfoCompletableFuture = CompletableFuture.supplyAsync(() -> productInfoService.retrieveProductInfo(productId));
        CompletableFuture<Review> reviewCompletableFuture = CompletableFuture.supplyAsync(() -> reviewService.retrieveReviews(productId));

        return productInfoCompletableFuture
                .thenCombine(reviewCompletableFuture, (pr, review) -> new Product(productId, pr, review));

    }

    public Product retrieveProductDetailsWithInventory(String productId) {
        stopWatch.start();

        CompletableFuture<ProductInfo> productInfoCompletableFuture = CompletableFuture
                        .supplyAsync(() -> productInfoService.retrieveProductInfo(productId))
                        .thenApply(productInfo -> {  //gives access to product info
                            productInfo.setProductOptions(updateInventory(productInfo));
                            return productInfo;
                        });
                
        CompletableFuture<Review> reviewCompletableFuture = CompletableFuture.supplyAsync(() -> reviewService.retrieveReviews(productId));

        Product product = productInfoCompletableFuture
                .thenCombine(reviewCompletableFuture, (pr, review) -> new Product(productId, pr, review))
                .join(); //block thread only once even though we have two completable futures

        stopWatch.stop();
        log("Total Time Taken : "+ stopWatch.getTime());
        return product;
    }


    public Product retrieveProductDetailsWithInventoryAsync(String productId) {
        stopWatch.start();

        CompletableFuture<ProductInfo> productInfoCompletableFuture = CompletableFuture
                .supplyAsync(() -> productInfoService.retrieveProductInfo(productId))
                .thenApply(productInfo -> {  //gives access to product info
                    productInfo.setProductOptions(updateInventoryAsync(productInfo));
                    return productInfo;
                });

        CompletableFuture<Review> reviewCompletableFuture = CompletableFuture
                .supplyAsync(() -> reviewService.retrieveReviews(productId))
                .exceptionally((e) ->{
                    log("Handled the exception in reviewService : "+ e.getMessage());
                    return Review.builder()
                            .noOfReviews(0)
                            .overallRating(0.0)
                            .build();
                });

        Product product = productInfoCompletableFuture
                .thenCombine(reviewCompletableFuture, (pr, review) -> new Product(productId, pr, review))
                .whenComplete(((product1, throwable) -> {
                    log("Inside whenComplete : "+ product1 + " and the exception is " +throwable);
                }))
                .join(); //block thread only once even though we have two completable futures

        stopWatch.stop();
        log("Total Time Taken : "+ stopWatch.getTime());
        return product;
    }

    private List<ProductOption> updateInventory(ProductInfo productInfo) {
        return productInfo.getProductOptions()
                .stream()
                .map(po -> {
                    Inventory inventory = inventoryService.retrieveInventory(po);
                    po.setInventory(inventory);
                    return po;
                })
                .collect(Collectors.toList());
    }

    private List<ProductOption> updateInventoryAsync(ProductInfo productInfo) {
        List<CompletableFuture<ProductOption>> productOptions = productInfo.getProductOptions()
                .stream()
                .map(po -> CompletableFuture.supplyAsync(() -> inventoryService.retrieveInventory(po))
                        .exceptionally((e)->{
                            log("Handled the exception in inventory service " +e.getMessage());
                            return Inventory.builder()
                                    .count(1)
                                    .build();
                        })
                        .thenApply(inv -> { ////gives access to inventory
                            po.setInventory(inv);
                            return po;
                        }))
                .collect(Collectors.toList());

        return productOptions.stream().map(CompletableFuture::join).collect(Collectors.toList());
        //when the collect method is executed it returns list<completable future> i.e completable future are collected as a list which means all the async call are executed
        // in parallel. so calling join on async task that is already in execution and similar other futures go through same operation. so parallelism is not lost.
        //

    }

    public static void main(String[] args) {

        ProductInfoService productInfoService = new ProductInfoService();
        ReviewService reviewService = new ReviewService();
        ProductServiceUsingCompletableFuture productService = new ProductServiceUsingCompletableFuture(productInfoService, reviewService);
        String productId = "ABC123";
        Product product = productService.retrieveProductDetails(productId);
        log("Product is " + product);

    }
}
