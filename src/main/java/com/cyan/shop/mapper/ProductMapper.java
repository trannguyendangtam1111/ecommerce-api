package com.cyan.shop.mapper;

import com.cyan.shop.dto.ProductRequest;
import com.cyan.shop.dto.ProductResponse;
import com.cyan.shop.entity.Product;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductResponse toDto(Product product);
    Product toEntity(ProductRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = IGNORE)
    void updateProduct(@MappingTarget Product product, ProductRequest request);
}
