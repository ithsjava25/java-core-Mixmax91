package com.example;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


public class Warehouse {
    private final List<Product> products = new ArrayList<>();
    private static final Map<String, Warehouse> CACHEMAP = new HashMap<>();
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
            Random rand = new Random();
            int defaultInt = rand.nextInt(10000);
            String name = String.valueOf(defaultInt);
            if(!CACHEMAP.containsKey(name)){
                return cacheAndAdd("Warehouse" + name);
            }
            return cacheAndAdd("Default Name");

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
        return Collections.unmodifiableList(products);
    }

    public Optional<Product> getProductById(UUID id) {
        return products.stream()
                .filter(product -> product.uuid().equals(id))
                .findFirst();
    }

    public void updateProductPrice(UUID id, BigDecimal price) {
        Product product = getProductById(id).orElseThrow(() -> new NoSuchElementException("Product not found with id:" + id));
        product.price(price);
    }

    public void clearProducts() {
        products.clear();
    }

    public boolean isEmpty() {
        return products.isEmpty();
    }

    public void remove(UUID uuid) {
        products.removeIf(product -> product.uuid().equals(uuid));
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
