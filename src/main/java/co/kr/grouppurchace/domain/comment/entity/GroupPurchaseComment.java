package co.kr.grouppurchace.domain.comment.entity;

import co.kr.grouppurchace.domain.groupPurchase.entity.GroupPurchase;
import co.kr.grouppurchace.domain.user.entity.User;
import co.kr.grouppurchace.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE group_purchase_comment SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")
public class GroupPurchaseComment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_purchase_id", nullable = false)
    private GroupPurchase groupPurchase;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 1000)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private GroupPurchaseComment parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupPurchaseComment> children = new ArrayList<>();

    private boolean isDeleted = false;

    @Builder
    public GroupPurchaseComment(GroupPurchase groupPurchase, User user, String content, GroupPurchaseComment parent) {
        this.groupPurchase = groupPurchase;
        this.user = user;
        this.content = content;
        this.parent = parent;
    }

    public void update(String content) {
        this.content = content;
    }

    public void softDelete() {
        this.isDeleted = true;
    }
}
