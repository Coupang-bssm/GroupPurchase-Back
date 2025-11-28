package co.kr.grouppurchace.domain.comment.repository;

import co.kr.grouppurchace.domain.comment.entity.GroupPurchaseComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupPurchaseCommentRepository extends JpaRepository<GroupPurchaseComment, Long> {

    List<GroupPurchaseComment> findAllByGroupPurchaseId(Long groupPurchaseId);
}
