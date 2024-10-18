package com.mawulidev.week5labs.services.implementions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mawulidev.week5labs.dtos.ProductDTO;
import com.mawulidev.week5labs.exceptions.EntityNotFoundException;
import com.mawulidev.week5labs.models.Product;
import com.mawulidev.week5labs.repositories.ProductRepository;
import com.mawulidev.week5labs.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ObjectMapper mapper;

    @Override
    public List<ProductDTO> getAllProduct() {
        return productRepository.findAll().stream().map(product -> mapper.convertValue(product, ProductDTO.class)).toList();
    }

    @Override
    public ProductDTO addProduct(ProductDTO productDTO) {
        Product product = productRepository.save(mapper.convertValue(productDTO, Product.class));
        return mapper.convertValue(product, ProductDTO.class);
    }

    @Override
    public ProductDTO getProductById(String id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product does not exist"));
        return mapper.convertValue(product, ProductDTO.class);
    }

    @Override
    public String updateProduct(String id, ProductDTO productDTO) {
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product does not exist"));
        product.setName(productDTO.getName());
        product.setPrice(product.getPrice());
        productRepository.save(product);
        return "Product successfully updated";
    }

    @Override
    public String deleteProductById(String id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product does not exist"));
        productRepository.delete(product);
        return "Product successfully deleted";
    }
}
