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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/group-purchase/{gpId}/comments")
    public ResponseEntity<Void> createComment(@AuthenticationPrincipal User user, @PathVariable Long gpId, @RequestBody CommentSaveRequest request) {
        Long commentId = commentService.save(user.getId(), gpId, request);
        return ResponseEntity.created(URI.create("/api/comments/" + commentId)).build();
    }

    @GetMapping("/group-purchase/{gpId}/comments")
    public ResponseEntity<List<CommentResponse>> getComments(@PathVariable Long gpId) {
        List<CommentResponse> response = commentService.findAll(gpId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponse> getComment(@PathVariable Long commentId) {
        CommentResponse response = commentService.findById(commentId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<Void> updateComment(@PathVariable Long commentId, @RequestBody CommentUpdateRequest request) {
        commentService.update(commentId, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.delete(commentId);
        return ResponseEntity.noContent().build();
    }
}
