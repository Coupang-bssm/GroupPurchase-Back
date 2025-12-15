# 공동구매 백엔드 서비스 최종 보고서

- **작성일:** 2025년 12월 6일
- **프로젝트 개요:** 사용자들이 상품에 대한 공동구매를 생성하고 참여하며, 관련하여 소통할 수 있는 백엔드 API 서비스입니다.

---

## 1. 핵심 기능 구현 현황

현재까지 프로젝트의 주요 도메인에 대한 API 기능 개발이 완료되었습니다.

### 1.1. 👩‍💻 사용자 인증 (Auth) API
- **기능:** JWT 토큰 기반의 사용자 인증 시스템을 구현했습니다.
- **주요 API:**
  - `POST /api/auth/signup`: 회원가입
  - `POST /api/auth/login`: 로그인 (Access/Refresh 토큰 발급)
  - `POST /api/auth/logout`: 로그아웃
  - `GET /api/auth/me`: 인증된 사용자 정보 조회

### 1.2. 📦 상품 (Product) API
- **기능:** 공동구매의 대상이 되는 상품을 관리합니다.
- **주요 API:**
  - `POST /api/products`: 상품 등록
  - `PUT /api/products/{productId}`: 상품 정보 수정
  - `DELETE /api/products/{productId}`: 상품 삭제
  - `GET /api/products/{productId}`: 특정 상품 상세 조회
  - `GET /api/products?lastId={id}`: 전체 상품 목록 조회 **(커서 기반 무한 스크롤 적용 완료)**

### 1.3. 🤝 공동구매 (GroupPurchase) API
- **기능:** 특정 상품에 대한 공동구매를 생성하고 관리합니다.
- **주요 API:**
  - `POST /api/group-purchase`: 공동구매 생성
  - `PUT /api/group-purchase/{gpId}`: 공동구매 정보 수정
  - `DELETE /api/group-purchase/{gpId}`: 공동구매 삭제
  - `GET /api/group-purchase/{gpId}`: 특정 공동구매 상세 조회
  - `GET /api/group-purchase?page=0&size=10`: 전체 공동구매 목록 조회 (페이지네이션)
  - `POST /api/group-purchase/{gpId}/join`: 공동구매 참여
  - `PATCH /api/group-purchase/join/{joinId}/approve`: 참여 요청 승인

### 1.4. 💬 댓글 (Comment) API
- **기능:** 공동구매에 대한 문의 및 답변을 위한 댓글/대댓글 기능을 구현했습니다.
- **주요 API:**
  - `POST /api/comments/{gpId}`: 댓글/대댓글 등록
  - `GET /api/comments?groupPurchaseId={gpId}`: 특정 공동구매의 전체 댓글 목록 조회 **(N-depth 계층 구조 적용 완료)**
  - `GET /api/comments/{commentId}`: 단일 댓글 상세 조회
  - `PUT /api/comments/{commentId}`: 댓글 수정
  - `DELETE /api/comments/{commentId}`: 댓글 삭제 **(재귀 삭제 적용 완료)**

---

## 2. 시스템 아키텍처 및 심화 기술 적용

### 2.1. RESTful API 설계
- 각 도메인(사용자, 상품, 공동구매, 댓글)에 대한 자원 중심의 API 엔드포인트를 설계했습니다.
- 특히, 댓글 API는 사용자 피드백을 통해 초기 설계의 불일치성을 개선하고, 명확성과 일관성을 확보하는 방향으로 리팩토링을 완료했습니다.

### 2.2. 표준화된 예외 처리
- `@RestControllerAdvice`를 사용한 전역 예외 처리기를 구현하여 모든 API의 에러 응답을 중앙에서 관리합니다.
- `ErrorCode` Enum과 커스텀 예외 클래스(`EntityNotFoundException` 등)를 정의하여, 예측 가능한 오류(예: 리소스 조회 실패 시 404 Not Found)에 대해 일관된 JSON 형식(`{ "errorCode": "...", "errorMessage": "..." }`)으로 응답하도록 구현했습니다. 이를 통해 프론트엔드에서의 에러 처리 및 유지보수 효율성을 증대시켰습니다.

### 2.3. 데이터 모델링 및 심화 기술
- JPA를 사용하여 각 도메인 간의 관계(1:N, N:1)를 명확하게 설정한 엔티티를 설계했습니다.
- **계층형 댓글**: `parent`/`children`의 Self-referencing(자기 참조) 관계를 엔티티에 적용하고, DTO 변환 과정에서 재귀적으로 자식 댓글을 포함하도록 구현하여 무한 Depth 댓글 구조를 완성했습니다.
- **재귀 삭제**: 부모 댓글 삭제 시, 해당 댓글에 달린 모든 자식 댓글들이 연쇄적으로 삭제되도록 구현했습니다.

---

## 3. 로컬 실행 방법
1. 프로젝트 클론:
   ```bash
   git clone https://github.com/Coupang-bssm/GroupPurchase-Back
   ```
2. 디렉토리 이동:
   ```bash
   cd group-purchase-Back
    ```
3. 의존성 설치 및 빌드:
   ```bash
   ./gradlew build
   ```
4. 애플리케이션 실행:
   ```bash
   ./gradlew bootRun
   ```