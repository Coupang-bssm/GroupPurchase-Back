package co.kr.grouppurchace.domain.groupPurchase.dto;

import co.kr.grouppurchace.domain.groupPurchase.entity.GroupPurchaseStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class GroupPurchaseUpdateRequest {

    private String title;
    private String description;
    private Integer targetCount;
    private LocalDateTime deadline;
    private GroupPurchaseStatus status;
}
