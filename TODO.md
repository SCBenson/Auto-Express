## 4-Week Sprint Plan for Production Full-Stack Demo with Testing
## Week 1: Backend Foundation & Testing (Days 1-7)
## Days 1-2: Fix Entity Relationships & Add Tests
- [ ] Add bidirectional @OneToMany in Dealer entity
- [ ] Add @Valid annotations to entities (email, price > 0, non-null fields)
- [ ] Unit Tests: Test entity validation constraints
- [ ] Integration Test: Test relationship persistence and cascade operations
- [ ] Test edge cases: null dealer, orphaned vehicles
## Days 3-4: Security with JWT & Tests
- [ ] Add dependencies: spring-boot-starter-security, jjwt-api, jjwt-impl, jjwt-jackson
- [ ] Create JwtUtil class for token generation/validation
- [ ] Implement AuthController (/api/auth/login, /api/auth/register)
- [ ] Configure SecurityFilterChain with JWT filter
- [ ] Unit Tests: Test JWT token generation, validation, expiration
- [ ] Integration Tests: Test login endpoint, token refresh
- [ ] Test edge cases: expired tokens, invalid credentials, duplicate registration
## Days 5-6: Service Layer Refactoring & Testing
- [ ] Move business logic from controllers to services
- [ ] Enhance GlobalExceptionHandler with custom exceptions
- [ ] Create DTOs for all responses (remove password field)
- [ ] Unit Tests: Mock repository layer, test service methods (90%+ coverage)
- [ ] Integration Tests: Test controller endpoints with MockMvc
## Test edge cases:
- [ ] Update non-existent entity
- [ ] Delete entity with relationships
- [ ] Duplicate email registration
- [ ] Invalid pagination parameters
- [ ] Day 7: API Documentation & CORS
- [ ] Add SpringDoc OpenAPI (springdoc-openapi-starter-webmvc-ui)
- [ ] Annotate controllers with @Operation, @ApiResponse
- [ ] Configure CORS for http://localhost:5173 (Vite default)
- [ ] Integration Test: Test CORS preflight requests
- [ ] Review test coverage report (aim for 80%+)

## Week 2: Advanced Backend Features & Testing (Days 8-14)
## Days 8-9: Search, Filter & Pagination with Tests
- [ ] Implement vehicle search in repository using @Query
- [ ] Update VehicleController with search endpoint
- [ ] Add pagination to all getAll endpoints
- [ ] Unit Tests: Test query methods with different parameter combinations
- [ ] Integration Tests: Test pagination, sorting, filtering
- [ ] Test edge cases: empty results, invalid price range, negative page numbers

## Days 10-11: Purchase Request Feature & Tests
- [ ] Create PurchaseRequest entity.
- [ ] Create repository, service, controller with full CRUD
- [ ] Add /api/dealers/{id}/requests endpoint
- [ ] Unit Tests: Test status transitions, validation
- [ ] Integration Tests: Test request creation, approval workflow
## Test edge cases:
- [ ] Request for sold vehicle
- [ ] Multiple requests for same vehicle
- [ ] Approve non-existent request
- [ ] Unauthorized status updates

- ## Days 12-13: User Favorites & Tests
Create @ManyToMany relationship in User entity.
- [ ] Add endpoints: POST /api/users/{id}/favorites/{vehicleId}, DELETE, GET
- [ ] Unit Tests: Test add/remove favorites, duplicate handling
- [ ] Integration Tests: Test favorites persistence across sessions
- [ ] Test edge cases: favorite non-existent vehicle, remove non-favorited vehicle

## Day 14: Database Optimization & Testing
- [ ] Add indexes on frequently queried fields (make, model, price, email)
- [ ] Configure HikariCP connection pool in application.yml
- [ ] Add Flyway for database migrations
- [ ] Performance Tests: Test query execution times with large datasets
- [ ] Create seed script with 100+ vehicles, 20+ dealers, 50+ users
- [ ] Test edge cases: connection pool exhaustion, deadlocks

## Week 3: React Frontend with Testing (Days 15-21)
## Days 15-16: React Setup & Authentication UI
- [ ] Initialize Vite React app: npm create vite@latest frontend -- --template react
- [ ] Install: axios, react-router-dom, jwt-decode, @testing-library/react, vitest
- [ ] Create folder structure and api.js with Axios interceptors
- [ ] Build AuthContext with JWT token management
- [ ] Create Login and Register pages with validation
- [ ] Unit Tests: Test form validation, API service methods
- [ ] Integration Tests: Test login flow with MSW (Mock Service Worker)
- [ ] Test edge cases: network failures, 401 redirects, token expiration 

## Days 17-18: Vehicle Listing & Details
- [ ] Create VehicleList page with VehicleCard component
- [ ] Implement pagination controls (Previous/Next buttons)
- [ ] Build search/filter form (make, model, price range)
- [ ] Create VehicleDetails page with purchase request form
- [ ] Add loading states and error boundaries
- [ ] Unit Tests: Test component rendering, event handlers
- [ ] Integration Tests: Test search/filter functionality with mock API
- [ ] Test edge cases: empty results, API errors, invalid vehicle ID

## Days 19-20: User & Dealer Dashboards
- [ ] Create user dashboard with profile and favorites list
- [ ] Build dealer dashboard with vehicle management (CRUD)
- [ ] Implement "Add Vehicle" form with validation
- [ ] Show dealer's incoming purchase requests with approve/reject
- [ ] Add favorites toggle button on vehicle cards
- [ ] Unit Tests: Test dashboard components, form submissions
- [ ] Integration Tests: Test CRUD operations with mock API
- [ ] Test edge cases: delete vehicle with active requests, update validation errors

- ## Day 21: Styling & Accessibility
- [ ] Add Tailwind CSS: npm install -D tailwindcss postcss autoprefixer
- [ ] Style all components with consistent design system
- [ ] Make responsive (mobile-first approach)
- [ ] Add ARIA labels and keyboard navigation
- [ ] Implement toast notifications for success/error messages
- [ ] Accessibility Tests: Test with screen reader, keyboard-only navigation
- [ ] Test edge cases: long text overflow, small screens (<375px)

## Week 4: Production Readiness & Demo Prep (Days 22-28)
## Days 22-23: End-to-End Testing & Bug Fixes
- [ ] Optional: Add Cypress for E2E tests (npm install -D cypress)
- [ ] Write E2E tests for critical user journeys:
- [ ] Register → Login → Browse vehicles → Add favorite
- [ ] Dealer login → Add vehicle → View requests
- [ ] User request vehicle → Dealer approves
- [ ] Fix all bugs discovered during testing
- [ ] Load Testing: Use JMeter/k6 to test backend under load (100 concurrent users)
- [ ] Test edge cases: race conditions, concurrent requests, transaction rollbacks

## Days 24-25: Deployment & CI/CD
- [ ] Create Dockerfile for Spring Boot.
- [ ] Create docker-compose.yml with backend, MariaDB, nginx for React
- [ ] Build production React bundle: npm run build
- [ ] Set up GitHub Actions for automated testing
- [ ] Deployment Test: Test full stack locally with Docker
- [ ] Test edge cases: container restarts, volume persistence, environment variables

## Day 26: Documentation & Code Quality
- [ ] Update README.md with:
- [ ] Architecture diagram (draw.io or Mermaid)
- [ ] Setup instructions (prerequisites, installation, running)
- [ ] API documentation link (Swagger UI)
- [ ] Testing instructions
- [ ] Add JavaDoc comments to public methods
- [ ] Run static analysis (SonarLint, Checkstyle)
- [ ] Ensure 80%+ test coverage for backend, 70%+ for frontend
- [ ] Create docs/ directory with:
- [ ] Database schema diagram
- [ ] API endpoint reference
- [ ] User guide

## Day 27: Demo Data & Rehearsal
- [ ] Create data.sql seed script with realistic data:
- [ ] 50+ vehicles (varied makes, models, years, prices)
- [ ] 10 dealers with 5-10 vehicles each
- [ ] 20 users with favorites and purchase requests
- [ ] Write demo-script.md with step-by-step walkthrough
- [ ] Record 5-minute demo video showcasing key features
- [ ] Practice demo presentation (aim for <10 minutes)

## Day 28: Final Polish & Backup Plan
- [ ] Code cleanup: remove System.out.println, unused imports, commented code
- [ ] Format all code (Google Java Style, Prettier for React)
- [ ] Test on fresh machine (verify setup instructions work)
- [ ] Create Postman collection with example requests
- [ ] Prepare backup slides in case of technical issues
- [ ] Final commit with tag: v1.0.0-demo

## Demo Showcase Features
- [ ] Backend (10 min walkthrough)
- [ ] Swagger UI - Live API documentation at http://localhost:8080/swagger-ui.html
- [ ] Authentication - JWT login, token in Authorization header
- [ ] CRUD Operations - Show Postman requests with validation errors
- [ ] Relationships - Dealer's vehicles, user's favorites, purchase requests
- [ ] Search/Filter - Query vehicles by make, price range with pagination
- [ ] Test Coverage - Show Gradle test report with 80%+ coverage
- [ ] Frontend (10 min walkthrough)
- [ ] Register/Login - Show JWT token storage and protected routes
- [ ] Browse Vehicles - Search, filter, pagination in action
- [ ] User Journey - Add favorite → Request purchase → View dashboard
- [ ] Dealer Journey - Login → View vehicles → Approve request
- [ ] Responsive Design - Resize browser to show mobile layout
- [ ] Error Handling - Show network error toast, validation feedback
- [ ] Testing Highlights (5 min)
- [ ] Unit Tests - Show service layer tests with mocks
- [ ] Integration Tests - Show controller tests with MockMvc
- [ ] E2E Tests - Run Cypress test showing full user flow
- [ ] Edge Cases - Demonstrate handling of invalid input, expired tokens

## Tech Stack
Backend:
Spring Boot 3.4+, Spring Security, Spring Data JPA
JWT (jjwt 0.12+)
MariaDB with Flyway migrations
Gradle with JUnit 5, Mockito, MockMvc
SpringDoc OpenAPI
Frontend:
React 18+ (Vite), React Router v6
Axios with interceptors
Tailwind CSS
Vitest + React Testing Library
MSW for API mocking
Cypress (optional E2E)
DevOps:
Docker & Docker Compose
GitHub Actions (CI)
SonarQube/SonarLint (optional)
 
