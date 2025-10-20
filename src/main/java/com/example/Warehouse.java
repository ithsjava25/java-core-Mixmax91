package com.example;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


public class Warehouse {
    private final Set<Product> products = new HashSet<>();
    private static final Map<String, Warehouse> CACHEMAP = new ConcurrentHashMap<>();
    private final Set<UUID> uuids;
    private final String name;

    private Warehouse(String name){
        this.name = name;
        uuids = new HashSet<>();
    }

    private static Warehouse cacheAndAdd(String name) {
        if (!CACHEMAP.containsKey(name)) {
            CACHEMAP.put(name, new Warehouse(name));
        }
        return CACHEMAP.get(name);
    }

    public static Warehouse getInstance(){
        return cacheAndAdd("Default");
    }

    public static Warehouse getInstance(String name){
        return cacheAndAdd(name);
    }

    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null.");
        }
        if (uuids.contains(product.uuid())) {
            throw new IllegalArgumentException("Product with that id already exists, use updateProduct for updates.");
        } else {
            uuids.add(product.uuid());
        }
        products.add(product);
    }

    public List<Product> getProducts() {
        return Collections.unmodifiableList(new ArrayList<>(products));
    }

    public Optional<Product> getProductById(UUID id) {
        return products.stream()
                .filter(product -> product.uuid().equals(id))
                .findFirst();

    }

    public void updateProductPrice(UUID id, BigDecimal price) {
        Product product = products.stream()
                .filter(p -> p.uuid().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Product not found with id:" + id));
        product.price(price);
    }

    public void clearProducts() {
        products.clear();
        uuids.clear();
    }

    public boolean isEmpty() {
        return products.isEmpty();
    }

    public void remove(UUID uuid) {
        products.removeIf(product -> product.uuid().equals(uuid));
        uuids.remove(uuid);
    }

    public Map<Category, List<Product>> getProductsGroupedByCategories() {

        return getProducts().stream().collect(Collectors.groupingBy(Product::category));
    }

    public List<Perishable> expiredProducts() {
        return products.stream()
                .filter(product -> product instanceof Perishable)
                .map(product -> (Perishable) product)
                .filter(Perishable::isExpired)
                .toList();
    }

    public List<Shippable> shippableProducts() {
        return products.stream()
                .filter(product -> product instanceof Shippable)
                .map(product -> (Shippable) product)
                .toList();
    }
}
