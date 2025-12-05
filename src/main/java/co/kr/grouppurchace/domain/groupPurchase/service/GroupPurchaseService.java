package co.kr.grouppurchace.domain.groupPurchase.service;

import co.kr.grouppurchace.domain.groupPurchase.dto.GroupPurchaseResponse;
import co.kr.grouppurchace.domain.groupPurchase.dto.GroupPurchaseSaveRequest;
import co.kr.grouppurchace.domain.groupPurchase.dto.GroupPurchaseUpdateRequest;
import co.kr.grouppurchace.domain.groupPurchase.entity.GroupPurchase;
import co.kr.grouppurchace.domain.groupPurchase.entity.GroupPurchaseJoin;
import co.kr.grouppurchace.domain.groupPurchase.repository.GroupPurchaseJoinRepository;
import co.kr.grouppurchace.domain.groupPurchase.repository.GroupPurchaseRepository;
import co.kr.grouppurchace.domain.product.entity.Product;
import co.kr.grouppurchace.domain.product.repository.ProductRepository;
import co.kr.grouppurchace.domain.user.entity.User;
import co.kr.grouppurchace.domain.user.repository.UserRepository;
import co.kr.grouppurchace.global.exception.EntityNotFoundException;
import co.kr.grouppurchace.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GroupPurchaseService {

    private final GroupPurchaseRepository groupPurchaseRepository;
    private final GroupPurchaseJoinRepository groupPurchaseJoinRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long open(Long userId, GroupPurchaseSaveRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.PRODUCT_NOT_FOUND));
        User host = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));
        return groupPurchaseRepository.save(request.toEntity(product, host)).getId();
    }

    @Transactional
    public void update(Long groupPurchaseId, GroupPurchaseUpdateRequest request) {
        GroupPurchase groupPurchase = groupPurchaseRepository.findById(groupPurchaseId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.GROUP_PURCHASE_NOT_FOUND));
        groupPurchase.update(request.getTitle(), request.getDescription(), request.getTargetCount(), request.getDeadline(), request.getStatus());
    }

    @Transactional
    public void delete(Long groupPurchaseId) {
        groupPurchaseRepository.deleteById(groupPurchaseId);
    }

    public GroupPurchaseResponse findById(Long groupPurchaseId) {
        GroupPurchase groupPurchase = groupPurchaseRepository.findById(groupPurchaseId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.GROUP_PURCHASE_NOT_FOUND));
        return new GroupPurchaseResponse(groupPurchase);
    }

    public Page<GroupPurchaseResponse> findAll(Pageable pageable) {
        return groupPurchaseRepository.findAll(pageable).map(GroupPurchaseResponse::new);
    }

    @Transactional
    public Long join(Long userId, Long groupPurchaseId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));
        GroupPurchase groupPurchase = groupPurchaseRepository.findById(groupPurchaseId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.GROUP_PURCHASE_NOT_FOUND));
        GroupPurchaseJoin join = GroupPurchaseJoin.builder()
                .groupPurchase(groupPurchase)
                .user(user)
                .quantity(1)
                .build();
        return groupPurchaseJoinRepository.save(join).getId();
    }

    @Transactional
    public void approve(Long joinId) {
        GroupPurchaseJoin join = groupPurchaseJoinRepository.findById(joinId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.GROUP_PURCHASE_JOIN_NOT_FOUND));
        join.approve();
    }

    public String createInviteLink(Long groupPurchaseId) {
        return "/api/group-purchase/" + groupPurchaseId;
    }
}
