package co.kr.grouppurchace.domain.comment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentSaveRequest {

    private String content;
    private Long parentId;
}
