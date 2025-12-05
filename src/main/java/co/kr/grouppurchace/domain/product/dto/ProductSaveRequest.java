package co.kr.grouppurchace.domain.product.dto;

import co.kr.grouppurchace.domain.product.entity.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSaveRequest {

    private String name;
    private String description;
    private Integer price;
    private String imageUrl;

    public Product toEntity() {
        return Product.builder()
                .name(name)
                .description(description)
                .price(price)
                .imageUrl(imageUrl)
                .build();
    }
}
