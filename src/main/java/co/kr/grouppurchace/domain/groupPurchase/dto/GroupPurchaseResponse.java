package co.kr.grouppurchace.domain.groupPurchase.dto;

import co.kr.grouppurchace.domain.groupPurchase.entity.GroupPurchase;
import co.kr.grouppurchace.domain.groupPurchase.entity.GroupPurchaseStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class GroupPurchaseResponse {

    private Long id;
    private Long productId;
    private Long hostUserId;
    private String title;
    private String description;
    private Integer targetCount;
    private Integer currentCount;
    private LocalDateTime deadline;
    private GroupPurchaseStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public GroupPurchaseResponse(GroupPurchase groupPurchase) {
        this.id = groupPurchase.getId();
        this.productId = groupPurchase.getProduct().getId();
        this.hostUserId = groupPurchase.getHost().getId();
        this.title = groupPurchase.getTitle();
        this.description = groupPurchase.getDescription();
        this.targetCount = groupPurchase.getTargetCount();
        this.currentCount = groupPurchase.getCurrentCount();
        this.deadline = groupPurchase.getDeadline();
        this.status = groupPurchase.getStatus();
        this.createdAt = groupPurchase.getCreatedAt();
        this.updatedAt = groupPurchase.getUpdatedAt();
    }
}
