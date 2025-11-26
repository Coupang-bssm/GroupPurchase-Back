package co.kr.grouppurchace.domain.groupPurchase.entity;

import co.kr.grouppurchace.domain.comment.entity.GroupPurchaseComment;
import co.kr.grouppurchace.domain.product.entity.Product;
import co.kr.grouppurchace.domain.user.entity.User;
import co.kr.grouppurchace.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupPurchase extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host_user_id", nullable = false)
    private User host;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Integer targetCount;

    @Column(nullable = false)
    private Integer currentCount = 0;

    @Column(nullable = false)
    private LocalDateTime deadline;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GroupPurchaseStatus status = GroupPurchaseStatus.OPEN;

    @OneToMany(mappedBy = "groupPurchase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupPurchaseJoin> joins = new ArrayList<>();

    @OneToMany(mappedBy = "groupPurchase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupPurchaseComment> comments = new ArrayList<>();

    @Builder
    public GroupPurchase(Product product, User host, String title, String description, Integer targetCount, LocalDateTime deadline) {
        this.product = product;
        this.host = host;
        this.title = title;
        this.description = description;
        this.targetCount = targetCount;
        this.deadline = deadline;
    }

    public void update(String title, String description, Integer targetCount, LocalDateTime deadline, GroupPurchaseStatus status) {
        this.title = title;
        this.description = description;
        this.targetCount = targetCount;
        this.deadline = deadline;
        this.status = status;
    }
}
