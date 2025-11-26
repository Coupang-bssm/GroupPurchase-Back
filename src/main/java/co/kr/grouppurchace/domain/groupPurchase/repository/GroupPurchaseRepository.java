package co.kr.grouppurchace.domain.groupPurchase.repository;

import co.kr.grouppurchace.domain.groupPurchase.entity.GroupPurchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupPurchaseRepository extends JpaRepository<GroupPurchase, Long> {
}
