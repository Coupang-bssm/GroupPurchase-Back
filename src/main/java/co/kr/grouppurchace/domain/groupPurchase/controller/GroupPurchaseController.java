package co.kr.grouppurchace.domain.groupPurchase.controller;

import co.kr.grouppurchace.domain.groupPurchase.dto.GroupPurchaseJoinRequest;
import co.kr.grouppurchace.domain.groupPurchase.dto.GroupPurchaseResponse;
import co.kr.grouppurchace.domain.groupPurchase.dto.GroupPurchaseSaveRequest;
import co.kr.grouppurchace.domain.groupPurchase.dto.GroupPurchaseUpdateRequest;
import co.kr.grouppurchace.domain.groupPurchase.service.GroupPurchaseService;
import co.kr.grouppurchace.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/group-purchase")
public class GroupPurchaseController {

    private final GroupPurchaseService groupPurchaseService;

    @PostMapping
    public ResponseEntity<Map<String, String>> openGroupPurchase(@AuthenticationPrincipal User user, @RequestBody GroupPurchaseSaveRequest request) {
        Long groupPurchaseId = groupPurchaseService.open(user.getId(), request);
        return ResponseEntity.created(URI.create("/api/group-purchase/" + groupPurchaseId)).body(Map.of("message", "공동구매가 생성되었습니다."));
    }

    @PutMapping("/{gpId}")
    public ResponseEntity<Void> updateGroupPurchase(@PathVariable Long gpId, @RequestBody GroupPurchaseUpdateRequest request) {
        groupPurchaseService.update(gpId, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{gpId}")
    public ResponseEntity<Map<String, Object>> deleteGroupPurchase(@PathVariable Long gpId) {
        groupPurchaseService.delete(gpId);
        return ResponseEntity.ok(Map.of("group-purchase-id", gpId, "message", "공동구매가 삭제되었습니다."));
    }

    @GetMapping("/{gpId}")
    public ResponseEntity<GroupPurchaseResponse> getGroupPurchase(@PathVariable Long gpId) {
        GroupPurchaseResponse response = groupPurchaseService.findById(gpId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<GroupPurchaseResponse>> getGroupPurchases(Pageable pageable) {
        Page<GroupPurchaseResponse> response = groupPurchaseService.findAll(pageable);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{gpId}/join")
    public ResponseEntity<Map<String, Object>> joinGroupPurchase(@AuthenticationPrincipal User user, @PathVariable Long gpId) {
        Long joinId = groupPurchaseService.join(user.getId(), gpId);
        Map<String, Object> response = new java.util.HashMap<>();
        response.put("group-purchase-id", gpId);
        response.put("joinId", joinId);
        response.put("message", "공동구매 참여가 요청되었습니다.");
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/join/{joinId}/approve")
    public ResponseEntity<Map<String, Object>> approveJoin(@PathVariable Long joinId) {
        groupPurchaseService.approve(joinId);
        return ResponseEntity.ok(Map.of("joinId",joinId,"message", "공동구매 참여가 승인되었습니다."));
    }

    @PostMapping("/{gpId}/invite-link")
    public ResponseEntity<String> createInviteLink(@PathVariable Long gpId) {
        String link = groupPurchaseService.createInviteLink(gpId);
        return ResponseEntity.ok(link);
    }
}
