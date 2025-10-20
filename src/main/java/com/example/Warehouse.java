package com.example;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Stores and handles Products
 *
 */
public class Warehouse {
    private final Set<Product> products = new HashSet<>();
    private static final Map<String, Warehouse> CACHEMAP = new ConcurrentHashMap<>();
    private final Set<UUID> uuids;
    private final String name;

    private Warehouse(String name){
        this.name = name;
        uuids = new HashSet<>();
    }

    /**
     *
     * @param name
     * Checks if Warehouse instance exists, if not, creates a new
     * @return Warehouse
     */
    private static Warehouse cacheAndAdd(String name) {
        if (!CACHEMAP.containsKey(name)) {
            CACHEMAP.put(name, new Warehouse(name));
        }
        return CACHEMAP.get(name);
    }

    /**
     *
     * @return Warehouse instance with default name
     */
    public static Warehouse getInstance(){
        return cacheAndAdd("Default");
    }

    /**
     *
     * @param name
     *
     * @return the same Warehouse instance per unique name
     */
    public static Warehouse getInstance(String name){
        return cacheAndAdd(name);
    }

    /**
     *
     * @param product
     * Adds Product to HashSet and saves uuid for internal checks
     * @throws IllegalArgumentException if null or if id already exists
     */
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

    /**
     *
     * @return Unmodifiable List copy
     */
    public List<Product> getProducts() {
        return Collections.unmodifiableList(new ArrayList<>(products));
    }

    /**
     *
     * @param id
     * @return Optional
     */
    public Optional<Product> getProductById(UUID id) {
        return products.stream()
                .filter(product -> product.uuid().equals(id))
                .findFirst();

    }

    /**
     *
     * @param id
     * @param price
     *
     * @throws NoSuchElementException if product not found with id
     */
    public void updateProductPrice(UUID id, BigDecimal price) {
        Product product = products.stream()
                .filter(p -> p.uuid().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Product not found with id:" + id));
        product.price(price);
    }

    /**
     *clears product set and uuids set
     */
    public void clearProducts() {
        products.clear();
        uuids.clear();
    }

    /**
     *
     * @return boolean
     */
    public boolean isEmpty() {
        return products.isEmpty();
    }

    /**
     * removes uuid from products set and uuid set
     * @param uuid
     */
    public void remove(UUID uuid) {
        products.removeIf(product -> product.uuid().equals(uuid));
        uuids.remove(uuid);
    }

    /**
     * Groups each product by their category in a map
     * @return Map
     */
    public Map<Category, List<Product>> getProductsGroupedByCategories() {
        return getProducts().stream().collect(Collectors.groupingBy(Product::category));
    }

    /**
     * Returns a list of expired products
     * @return List
     */
    public List<Perishable> expiredProducts() {
        return products.stream()
                .filter(product -> product instanceof Perishable)
                .map(product -> (Perishable) product)
                .filter(Perishable::isExpired)
                .toList();
    }

    /**
     * returns a list of shippable products
     * @return List
     */
    public List<Shippable> shippableProducts() {
        return products.stream()
                .filter(product -> product instanceof Shippable)
                .map(product -> (Shippable) product)
                .toList();
    }
}
