package co.kr.grouppurchace.domain.comment.controller;

import co.kr.grouppurchace.domain.comment.dto.CommentResponse;
import co.kr.grouppurchace.domain.comment.dto.CommentSaveRequest;
import co.kr.grouppurchace.domain.comment.dto.CommentUpdateRequest;
import co.kr.grouppurchace.domain.comment.service.CommentService;
import co.kr.grouppurchace.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{gpId}")
    public ResponseEntity<Map<String, String>> createComment(@AuthenticationPrincipal User user, @PathVariable Long gpId, @RequestBody CommentSaveRequest request) {
        Long commentId = commentService.save(user.getId(), gpId, request);
        return ResponseEntity.created(URI.create("/api/comments/" + commentId)).body(Map.of("message", "댓글이 등록되었습니다."));
    }

    @GetMapping
    public ResponseEntity<List<CommentResponse>> getComments(@RequestParam Long groupPurchaseId) {
        List<CommentResponse> response = commentService.findAll(groupPurchaseId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentResponse> getComment(@PathVariable Long commentId) {
        CommentResponse response = commentService.findById(commentId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Void> updateComment(@PathVariable Long commentId, @RequestBody CommentUpdateRequest request) {
        commentService.update(commentId, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Map<String, Object>> deleteComment(@PathVariable Long commentId) {
        commentService.delete(commentId);
        return ResponseEntity.ok().body(Map.of("commentId", commentId, "message", "댓글이 삭제되었습니다."));
    }
}
