package co.kr.grouppurchace.domain.comment.service;

import co.kr.grouppurchace.domain.comment.dto.CommentResponse;
import co.kr.grouppurchace.domain.comment.dto.CommentSaveRequest;
import co.kr.grouppurchace.domain.comment.dto.CommentUpdateRequest;
import co.kr.grouppurchace.domain.comment.entity.GroupPurchaseComment;
import co.kr.grouppurchace.domain.comment.repository.GroupPurchaseCommentRepository;
import co.kr.grouppurchace.domain.groupPurchase.entity.GroupPurchase;
import co.kr.grouppurchace.domain.groupPurchase.repository.GroupPurchaseRepository;
import co.kr.grouppurchace.domain.user.entity.User;
import co.kr.grouppurchace.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final GroupPurchaseCommentRepository commentRepository;
    private final GroupPurchaseRepository groupPurchaseRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long save(Long userId, Long groupPurchaseId, CommentSaveRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        GroupPurchase groupPurchase = groupPurchaseRepository.findById(groupPurchaseId)
                .orElseThrow(() -> new RuntimeException("GroupPurchase not found"));

        GroupPurchaseComment parent = null;
        if (request.getParentId() != null) {
            parent = commentRepository.findById(request.getParentId())
                    .orElseThrow(() -> new RuntimeException("Parent comment not found"));
        }

        GroupPurchaseComment comment = GroupPurchaseComment.builder()
                .user(user)
                .groupPurchase(groupPurchase)
                .content(request.getContent())
                .parent(parent)
                .build();
        return commentRepository.save(comment).getId();
    }

    public List<CommentResponse> findAll(Long groupPurchaseId) {
        List<GroupPurchaseComment> rootComments = commentRepository.findAllByGroupPurchaseIdAndParentIsNull(groupPurchaseId);
        return rootComments.stream()
                .map(CommentResponse::new)
                .collect(Collectors.toList());
    }

    public CommentResponse findById(Long commentId) {
        GroupPurchaseComment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        return new CommentResponse(comment);
    }

    @Transactional
    public void update(Long commentId, CommentUpdateRequest request) {
        GroupPurchaseComment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        comment.update(request.getContent());
    }

    @Transactional
    public void delete(Long commentId) {
        GroupPurchaseComment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        delete(comment);
    }

    private void delete(GroupPurchaseComment comment) {
        comment.getChildren().forEach(this::delete);
        commentRepository.delete(comment);
    }
}
