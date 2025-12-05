package co.kr.grouppurchace.domain.product.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductUpdateRequest {

    private String name;
    private String description;
    private Integer price;
    private String imageUrl;
}
