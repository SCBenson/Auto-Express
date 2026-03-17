# 4-Week Realistic Sprint Plan for Intern Developer

## Week 1: Core Backend (Days 1-7)

### Days 1-2: Entity Relationships & Basic Validation
- [X] Add bidirectional `@OneToMany` in `Dealer` entity for vehicles
- [X] Add `@Valid` and basic constraints (`@NotNull`, `@Email`, `@Min`)
- [X] Write 2-3 simple unit tests for entity validation

### Days 3-4: Basic Security with JWT
- [X] Add Spring Security dependency to `build.gradle`
- [X] Create simple `JwtUtil` class (copy from tutorial) Add parameters location & dealershipName to DTO, Service, Controller
- [X] Create `AuthController` with login endpoint only
- [X] Unit Test the `AuthController`
- [X] Test login manually with Postman

### Days 5-6: Move Logic to Services
- [ ] Create service methods in `DealerService`, `UserService`, `VehicleService`
- [ ] Move logic from controllers to services
- [ ] Update controllers to call services
- [ ] Write 3-5 unit tests for one service (start with `VehicleService`)

### Day 7: Basic Error Handling
- [ ] Add custom exception for "Not Found" scenarios
- [ ] Update `GlobalExceptionHandler` to return proper JSON errors
- [ ] Test error responses in Postman

---

## Week 2: Essential Features (Days 8-14)

### Days 8-9: Search & Pagination
- [ ] Add pagination to `GET /api/vehicles` using `Pageable`
- [ ] Create simple search by `make` in `VehicleRepository`
- [ ] Test pagination and search in Postman

### Days 10-11: Purchase Request Feature
- [ ] Create `PurchaseRequest` entity with `@ManyToOne` relationships
- [ ] Create `PurchaseRequestRepository`
- [ ] Add `POST /api/purchase-requests` endpoint
- [ ] Add `GET /api/dealers/{id}/requests` endpoint
- [ ] Test creating and viewing requests

### Days 12-13: User Favorites (Simplified)
- [ ] Add `@ManyToMany` favorites in `User` entity
- [ ] Create `POST /api/users/{id}/favorites/{vehicleId}`
- [ ] Create `GET /api/users/{id}/favorites`
- [ ] Test adding and viewing favorites

### Day 14: Basic Documentation
- [ ] Add SpringDoc OpenAPI dependency
- [ ] Add `@Operation` to 3-4 main endpoints
- [ ] Visit Swagger UI and verify it works

---

## Week 3: React Frontend Basics (Days 15-21)

### Days 15-16: React Setup & Login Page
- [ ] Create Vite React app: `npm create vite@latest frontend -- --template react`
- [ ] Install `axios` and `react-router-dom`
- [ ] Create basic folder structure (components, pages, services)
- [ ] Create `Login` page with form
- [ ] Create `api.js` with Axios base URL
- [ ] Test login flow (store token in localStorage)

### Days 17-18: Vehicle List Page
- [ ] Create `VehicleList` page
- [ ] Create `VehicleCard` component (simple card with vehicle info)
- [ ] Fetch vehicles from backend
- [ ] Display vehicles in a grid
- [ ] Add basic pagination (Previous/Next buttons)

### Days 19-20: User Dashboard
- [ ] Create `Dashboard` page
- [ ] Show user's email and name
- [ ] Add "My Favorites" section (display vehicle names)
- [ ] Create button to add favorite (click vehicle card)
- [ ] Test favorites flow

### Day 21: Basic Styling
- [ ] Add Tailwind CSS or use simple CSS
- [ ] Make navbar and buttons look decent
- [ ] Ensure layout works on desktop (mobile later if time)

---

## Week 4: Polish & Demo (Days 22-28)

### Days 22-23: Dealer Features
- [ ] Create `DealerDashboard` page
- [ ] Show dealer's vehicles in a list
- [ ] Add simple "Add Vehicle" form
- [ ] Show purchase requests for dealer's vehicles
- [ ] Add "Approve" button (just update status to APPROVED)

### Days 24-25: Testing & Bug Fixes
- [ ] Write 5-10 backend unit tests (focus on services)
- [ ] Write 2-3 integration tests using `@SpringBootTest`
- [ ] Fix any bugs found during testing
- [ ] Test all features manually

### Day 26: Documentation
- [ ] Update `README.md` with:
    - How to run backend (`./gradlew bootRun`)
    - How to run frontend (`npm run dev`)
    - List of main features
- [ ] Add comments to complex code sections

### Day 27: Demo Data
- [ ] Create `data.sql` with 10-15 sample vehicles
- [ ] Add 2-3 sample dealers
- [ ] Add 2-3 sample users
- [ ] Test that seed data loads correctly

### Day 28: Final Prep
- [ ] Clean up code (remove `System.out.println`, unused imports)
- [ ] Practice demo walkthrough:
    1. Show login
    2. Browse vehicles
    3. Add favorite
    4. Submit purchase request
    5. Login as dealer
    6. Approve request
- [ ] Take screenshots for presentation

---

## Simplified Demo Features

### Backend (5 min)
- [ ] Show Swagger UI with documented endpoints
- [ ] Demonstrate JWT login in Postman
- [ ] Show pagination working
- [ ] Show purchase request creation

### Frontend (5 min)
- [ ] Login as user
- [ ] Browse vehicles with pagination
- [ ] Add vehicle to favorites
- [ ] Submit purchase request
- [ ] Login as dealer and approve request

---

## Realistic Tech Stack

### Backend
- Spring Boot 3.4+
- Spring Security (basic JWT)
- Spring Data JPA
- MariaDB
- Gradle
- JUnit 5 (5-10 tests minimum)
- SpringDoc OpenAPI

### Frontend
- React 18+ (Vite)
- React Router v6
- Axios
- Basic CSS or Tailwind CSS

### Testing
- Focus on **unit tests** for services
- 2-3 **integration tests** for controllers
- **Manual testing** in Postman/browser

---

## What We Cut (And Why)

- ❌ **Extensive Test Coverage** - Aim for 30-40% instead of 80%
- ❌ **Advanced Error Handling** - Basic exceptions are enough
- ❌ **Docker & CI/CD** - Too complex for first project
- ❌ **E2E Testing** - Manual testing is sufficient
- ❌ **Advanced Search Filters** - One simple filter is enough
- ❌ **Responsive Design** - Desktop-first is fine
- ❌ **Performance Testing** - Not needed for demo

---

## Daily Workflow (Simplified)

### Backend Day
1. Implement feature
2. Test in Postman
3. Write 1-2 tests
4. Commit code

### Frontend Day
1. Build component/page
2. Connect to backend
3. Test in browser
4. Commit code

---

## Tips for Success

- **Don't aim for perfection** - Get it working first
- **Copy code from tutorials** - It's okay to learn this way
- **Ask for help** - Google, ChatGPT, Stack Overflow
- **Commit often** - At least once per day
- **Focus on core features** - Skip nice-to-haves

---

This is a **realistic, achievable plan** for someone new to full-stack development. You'll have a working demo that shows you understand the basics, even if it's not production-perfect.
