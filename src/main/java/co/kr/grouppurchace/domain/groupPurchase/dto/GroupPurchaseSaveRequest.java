package co.kr.grouppurchace.domain.groupPurchase.dto;

import co.kr.grouppurchace.domain.groupPurchase.entity.GroupPurchase;
import co.kr.grouppurchace.domain.product.entity.Product;
import co.kr.grouppurchace.domain.user.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class GroupPurchaseSaveRequest {

    private Long productId;
    private String title;
    private String description;
    private Integer targetCount;
    private LocalDateTime deadline;

    public GroupPurchase toEntity(Product product, User host) {
        return GroupPurchase.builder()
                .product(product)
                .host(host)
                .title(title)
                .description(description)
                .targetCount(targetCount)
                .deadline(deadline)
                .build();
    }
}
