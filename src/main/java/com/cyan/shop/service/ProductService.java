package com.cyan.shop.service;

import com.cyan.shop.dto.ProductRequest;
import com.cyan.shop.dto.ProductResponse;
import com.cyan.shop.entity.Product;
import com.cyan.shop.mapper.ProductMapper;
import com.cyan.shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductResponse create(ProductRequest request) {
        Product product = productMapper.toEntity(request);
        productRepository.save(product);
        return productMapper.toDto(product);
    }

    public List<ProductResponse> getAll() {
        return productRepository.findAll().stream().map(productMapper::toDto).toList();
    }

    public ProductResponse getById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return productMapper.toDto(product);
    }

    public ProductResponse update(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        productMapper.updateProduct(product, request);
        productRepository.save(product);
        return productMapper.toDto(product);
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    public Page<ProductResponse> searchProducts(String name, Double min, Double max, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Product> products = productRepository.search(name, min, max, pageable);
        return products.map(productMapper::toDto);
    }

}
