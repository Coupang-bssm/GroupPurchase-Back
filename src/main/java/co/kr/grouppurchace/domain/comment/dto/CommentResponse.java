package co.kr.grouppurchace.domain.comment.dto;

import co.kr.grouppurchace.domain.comment.entity.GroupPurchaseComment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CommentResponse {

    private Long id;
    private String content;
    private Long userId;
    private Long parentId;
    private LocalDateTime createdAt;
    private List<CommentResponse> children = new ArrayList<>();

    public CommentResponse(GroupPurchaseComment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.userId = comment.getUser().getId();
        this.createdAt = comment.getCreatedAt();
        if (comment.getParent() != null) {
            this.parentId = comment.getParent().getId();
        }
    }
}
