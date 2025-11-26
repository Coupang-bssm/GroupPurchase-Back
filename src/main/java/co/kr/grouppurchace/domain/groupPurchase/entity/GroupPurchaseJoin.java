package co.kr.grouppurchace.domain.groupPurchase.entity;

import co.kr.grouppurchace.domain.user.entity.User;
import co.kr.grouppurchace.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupPurchaseJoin extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_purchase_id", nullable = false)
    private GroupPurchase groupPurchase;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GroupPurchaseJoinStatus status = GroupPurchaseJoinStatus.PENDING;

    @Builder
    public GroupPurchaseJoin(GroupPurchase groupPurchase, User user, Integer quantity) {
        this.groupPurchase = groupPurchase;
        this.user = user;
        this.quantity = quantity;
    }

    public void approve() {
        this.status = GroupPurchaseJoinStatus.APPROVED;
    }
}
