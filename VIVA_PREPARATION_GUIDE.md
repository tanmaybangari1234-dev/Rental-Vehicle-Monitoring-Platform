# VIVA Preparation Guide: Software Development Models & Feasibility

## Important Models for GoPro Project

---

## 1. SOFTWARE DEVELOPMENT MODELS (SDM)

### Models Overview
1. **Waterfall Model**
2. **Spiral Model**
3. **Prototype Model**
4. **Agile/Iterative Model**
5. **RAD (Rapid Application Development)**
6. **DevOps Model**
7. **V-Model**
8. **Incremental Model**

---

## COMMON VIVA QUESTIONS & ANSWERS

### Q1: Which development model did you use for GoPro?
**Answer:** We used the **Prototype Model** combined with **Incremental Development**.

**Why?**
- Quick demonstration of features
- Early feedback from stakeholders
- Can modify modules independently
- Suitable for complex systems with unclear requirements
- Each module (User, Driver, Wallet, etc.) could be developed incrementally

---

### Q2: What is the Prototype Model?
**Answer:** 
The Prototype Model is a software development process where an early, preliminary, simplified version of a system is built to demonstrate its functionality and get user feedback before full-scale development.

**Phases:**
1. **Requirement Analysis** - Gather initial requirements
2. **Quick Design** - Design core components
3. **Build Prototype** - Develop quick working model
4. **Evaluate Prototype** - Get stakeholder feedback
5. **Refine Requirements** - Based on feedback
6. **Full Development** - Build final system
7. **Deployment** - Release to production

**For GoPro:**
- Prototype: Basic booking → Driver matching → Fare calculation
- Feedback: Add wallet, payments, tracking
- Refinement: Add notifications, reviews, SOS
- Full System: All 15 modules integrated

---

### Q3: What is the Spiral Model?
**Answer:**
The Spiral Model is a risk-driven development model that combines elements of iterative and waterfall models. It follows cycles of planning, risk analysis, engineering, and evaluation.

**Four Quadrants:**
1. **Planning** - Define objectives, alternatives, constraints
2. **Risk Analysis** - Identify and mitigate risks
3. **Engineering** - Build and develop
4. **Evaluation** - Review and plan next cycle

**For GoPro:**
- Cycle 1: User management, basic booking
- Cycle 2: Add driver matching, fare calculation
- Cycle 3: Add payments, wallet
- Cycle 4: Add tracking, notifications
- Cycle 5: Add reviews, SOS, admin panel

---

### Q4: Difference between Waterfall and Prototype Model?

| Aspect | Waterfall | Prototype |
|--------|-----------|-----------|
| Approach | Linear, Sequential | Iterative, Cyclical |
| Requirements | Fixed upfront | Evolved gradually |
| User Involvement | At start and end | Continuous |
| Risk | Higher, identified late | Lower, identified early |
| Cost | Lower initially | Higher upfront |
| Testing | After development | Continuous |
| Changes | Difficult to accommodate | Easy to accommodate |
| Best For | Clear requirements | Unclear/complex requirements |

**For GoPro:** Prototype model is better because ride-sharing requirements evolve.

---

### Q5: What is the Agile Model?
**Answer:**
Agile is an iterative development approach emphasizing collaboration, flexibility, and continuous improvement through small, frequent releases.

**Key Principles:**
- Individuals and interactions over processes
- Working software over documentation
- Customer collaboration over contracts
- Responding to change over plans

**Sprints (2-4 weeks each):**
- Sprint 1: User authentication & profiles
- Sprint 2: Driver management & verification
- Sprint 3: Ride booking & matching
- Sprint 4: Fare calculation & payment
- Sprint 5: Wallet & transactions
- Sprint 6: Tracking & notifications
- Sprint 7: Reviews & ratings
- Sprint 8: Admin panel & analytics

---

### Q6: What is Feasibility Study?
**Answer:**
Feasibility Study is a preliminary analysis to determine if a project is technically viable, economically justified, operationally feasible, and legally compliant.

**Types of Feasibility:**

#### **Technical Feasibility**
- Can we build it with available technology?
- Do we have required tools, languages, frameworks?

**For GoPro - YES:**
- Java is mature, widely supported
- All required data structures available
- No exotic technology needed
- Can scale with databases

#### **Economic Feasibility**
- Is the project cost-effective?
- What's the ROI?
- Development cost vs. revenue potential?

**For GoPro - YES:**
- Ride-sharing is highly profitable
- Market size: Billions globally
- High user acquisition potential
- Multiple revenue streams (commission, surge pricing, ads)

#### **Operational Feasibility**
- Can the organization support it?
- Are resources available?
- Can staff operate and maintain it?

**For GoPro - YES:**
- Modular design allows easy team management
- Clear interface between modules
- Easy to train staff
- Maintenance is straightforward

#### **Schedule Feasibility**
- Can we complete within timeline?
- Realistic project schedule?

**For GoPro - YES:**
- 15 modules can be developed in parallel
- Each module is independent
- Realistic 2-3 month timeline for MVP
- 4-6 months for full production system

#### **Legal/Social Feasibility**
- Are there legal restrictions?
- Regulatory compliance?
- Social acceptance?

**For GoPro - YES:**
- Ride-sharing legal in most countries
- Data privacy (GDPR compliant)
- No regulatory barriers
- High social acceptance

---

### Q7: For GoPro, which feasibility is most critical?
**Answer:** 
**Technical and Economic feasibility** are most critical.

**Why?**
- Technical: Must handle real-time data, payments, GPS tracking
- Economic: Must show viable business model and profit potential
- Operational: Must be scalable to millions of users

---

### Q8: What is RAD (Rapid Application Development)?
**Answer:**
RAD is a development methodology emphasizing rapid iterative releases and user feedback, using visual tools and pre-built components.

**Characteristics:**
- Short development cycles (60-90 days)
- CASE tools and code generators
- Visual development
- Team collaboration
- Reusable components

**For GoPro:**
- Use of pre-built Java libraries
- Each module as reusable component
- Quick iteration cycles
- User feedback integration

---

### Q9: What is DevOps Model?
**Answer:**
DevOps combines software development (Dev) and IT operations (Ops) to shorten system development lifecycle and enable continuous delivery.

**Practices:**
- Continuous Integration (CI)
- Continuous Deployment (CD)
- Infrastructure as Code
- Monitoring and Logging
- Automated Testing

**For GoPro in Production:**
- CI/CD pipeline for each module
- Automated testing before deployment
- Real-time monitoring
- Rapid bug fixes and updates

---

### Q10: What is V-Model?
**Answer:**
V-Model (Verification and Validation model) extends Waterfall with testing at each stage. Each development stage has corresponding testing stage.

**Levels:**
1. Requirements → Acceptance Testing
2. Design → System Testing
3. Detailed Design → Integration Testing
4. Implementation → Unit Testing

**For GoPro:**
- Requirements: Full system specification
- Design: Module interactions defined
- Detailed Design: Each module design
- Coding: Individual module coding
- Unit Testing: Test each module alone
- Integration Testing: Test modules together
- System Testing: Test complete system
- Acceptance Testing: User acceptance

---

### Q11: What is the Incremental Model?
**Answer:**
Incremental Model delivers system in small, functional pieces (increments) with each increment adding new functionality.

**Process:**
1. Increment 1: Basic functionality (Booking + User)
2. Increment 2: Add driver management
3. Increment 3: Add fare calculation
4. Increment 4: Add payments
5. Increment 5: Add tracking
... and so on

**For GoPro:**
- Increment 1: 3 months - Basic booking system
- Increment 2: 2 months - Full ride management
- Increment 3: 2 months - Advanced features
- Increment 4: 1 month - Admin panel
- Increment 5: 1 month - Optimization

---

### Q12: Why Prototype Model for GoPro over Waterfall?

**Reasons:**
1. **Unclear Requirements** - Ride-sharing features evolve
2. **User Feedback** - Need quick validation
3. **Technology Stack** - Multiple options to explore
4. **Risk Mitigation** - Identify issues early
5. **Market Dynamics** - Competitive landscape changes
6. **Complex Features** - Real-time tracking, payments need testing

---

### Q13: What are the risks in Prototype Model?

**Risks:**
1. **Scope Creep** - Requirements keep expanding
2. **Cost Overrun** - Development takes longer than planned
3. **Gold Plating** - Adding unnecessary features
4. **Poor Documentation** - Quick development, less documentation
5. **Prototype Becomes Product** - Prototype code used in production

**Mitigation for GoPro:**
- Clear scope definition upfront
- Budget allocation per increment
- Code review and documentation standards
- Clean code from start, not "throwaway" prototype

---

### Q14: Comparison: Prototype vs Agile vs Spiral

| Aspect | Prototype | Agile | Spiral |
|--------|-----------|-------|--------|
| Cycle | Few long cycles | Many short sprints | Multiple risk-driven cycles |
| User Involvement | High | Very high | Medium |
| Documentation | Low | Medium | High |
| Cost Predictability | Low | Medium | High |
| Risk | Identified early | Managed continuously | Explicitly managed |
| Best For | New/complex systems | Small, flexible teams | Large, risky projects |
| Time to First Release | Medium | Very short | Long |

---

### Q15: What is meant by "Feasibility Report"?

**Answer:**
A feasibility report is a document that analyzes whether a project should be undertaken by evaluating technical, economic, operational, schedule, and legal aspects.

**Contents of GoPro Feasibility Report:**

**1. Executive Summary**
- Project overview
- Recommendation (Go/No-Go)

**2. Technical Feasibility**
- Architecture review
- Technology assessment
- Risk analysis
- Conclusion: FEASIBLE

**3. Economic Feasibility**
- Development cost: $500K-$1M
- Operational cost: $100K/year
- Revenue potential: $10M+ annually
- ROI: Positive in Year 2
- Conclusion: ECONOMICALLY VIABLE

**4. Operational Feasibility**
- Team size: 20-30 developers
- Training required: 2 weeks
- Support infrastructure: Scalable
- Conclusion: OPERATIONALLY FEASIBLE

**5. Schedule Feasibility**
- MVP: 3 months
- Full system: 6 months
- Market window: Open
- Conclusion: SCHEDULE ACHIEVABLE

**6. Legal Feasibility**
- Licensing: Open source friendly
- Data privacy: GDPR compliant
- Payment regulations: Compliant
- Conclusion: NO LEGAL BARRIERS

**7. Recommendation**
- **Proceed with development**
- Prototype approach recommended
- Phased rollout strategy

---

### Q16: What metrics do you use to measure success?

**For GoPro Project:**

**Technical Metrics:**
- Code coverage: >80%
- Response time: <200ms
- System availability: >99.5%
- Module coupling: Loose coupling
- Module cohesion: High cohesion

**Business Metrics:**
- User acquisition: 100K by Month 6
- Ride completion rate: >95%
- Driver acceptance rate: >90%
- Average revenue per user: $20/month
- Customer satisfaction: >4.5/5

**Development Metrics:**
- Development velocity: 50 story points/sprint
- Bug density: <1 per 1000 LOC
- Test coverage: >80%
- Documentation completeness: 100%
- Team productivity: On schedule

---

### Q17: What is the difference between SRS and Feasibility Study?

| Aspect | SRS | Feasibility Study |
|--------|-----|-------------------|
| Timing | After feasibility study | Before SRS |
| Purpose | Define what to build | Determine if to build |
| Content | Detailed requirements | Viability assessment |
| Detail Level | Very detailed | High level |
| Audience | Developers, QA | Decision makers |
| Outcome | System specification | Go/No-Go decision |

---

### Q18: What are the phases of System Development Life Cycle (SDLC)?

**Answer:**
1. **Planning & Feasibility** - Assess project viability
2. **Requirements Analysis** - Gather detailed requirements
3. **Design** - Create system architecture
4. **Implementation** - Code the system
5. **Testing** - Verify quality
6. **Deployment** - Release to production
7. **Maintenance** - Support and updates

**For GoPro:**
- Phase 1-2: 1 month (feasibility + requirements)
- Phase 3: 1.5 months (design)
- Phase 4: 2 months (coding)
- Phase 5: 1 month (testing)
- Phase 6: 0.5 months (deployment)
- Phase 7: Ongoing (maintenance)

---

### Q19: What are "Non-Functional Requirements"?

**Answer:**
Non-functional requirements specify how well the system should perform, not what it should do.

**For GoPro:**
1. **Performance** - Response time <200ms
2. **Scalability** - Support 1M+ users
3. **Reliability** - 99.9% uptime
4. **Security** - Encrypt payments, OTP for users
5. **Usability** - Intuitive UI, <5 clicks to book
6. **Availability** - 24/7 access
7. **Maintainability** - Modular design
8. **Portability** - Works on Android/iOS/Web

---

### Q20: Explain your GoPro Architecture

**Answer:**

**3-Tier Architecture:**

```
Layer 1: Presentation (GUI/CLI)
- GoProGUI.java
- User interfaces

Layer 2: Business Logic (15 Modules)
- User, Driver, Vehicle management
- Ride booking, matching
- Fare calculation, payments
- Wallet, notifications
- Tracking, reviews
- Admin dashboard

Layer 3: Data (Persistence)
- User data
- Ride records
- Transaction history
- Driver ratings
```

**Module Interactions:**
```
User Booking → RideMatching → FareCalculator → 
Payment → Wallet → Notification → RideTracker → 
RatingReview → RideHistory
```

**Advantages:**
- Loose coupling between modules
- High cohesion within modules
- Easy to scale each layer
- Independent testing
- Clear responsibilities

---

### Q21: What is "Technical Debt"?

**Answer:**
Technical debt refers to code/design shortcuts taken to meet deadlines that will require rework later.

**Examples in GoPro:**
- Using ArrayList instead of Database
- Hardcoded values instead of configuration
- Minimal error handling
- No logging system
- No encryption for payments

**To Repay Technical Debt:**
- Migrate to database
- Add proper error handling
- Implement logging
- Add encryption
- Write comprehensive tests
- Refactor code

---

### Q22: Risk Management in your project?

**Risks Identified:**
1. **Technical Risk** - Real-time tracking accuracy
   - Mitigation: Use proven GPS libraries
   
2. **Market Risk** - User adoption
   - Mitigation: MVP with core features
   
3. **Financial Risk** - Payment processing failures
   - Mitigation: Retry logic, multiple payment gateways
   
4. **Operational Risk** - Surge in users
   - Mitigation: Scalable cloud infrastructure
   
5. **Security Risk** - Data breaches
   - Mitigation: Encryption, secure authentication

---

## QUICK ANSWER TEMPLATES

### Template 1: When asked about model choice
"We chose **[Model Name]** because:
1. [Reason 1]
2. [Reason 2]
3. [Reason 3]
Compared to alternatives like [Other Model], [Model Name] provides [benefit]."

### Template 2: When asked about feasibility
"We conducted feasibility analysis on 5 dimensions:
1. **Technical:** ✓ Feasible - Java is mature, no exotic tech
2. **Economic:** ✓ Viable - $10M+ revenue potential
3. **Operational:** ✓ Feasible - Modular, scalable design
4. **Schedule:** ✓ Achievable - 6 months for MVP
5. **Legal:** ✓ No barriers - GDPR compliant
**Recommendation:** PROCEED with Prototype + Incremental approach"

### Template 3: When asked about risks
"We identified risks using [Method]:
- **Priority 1:** [Risk] - Impact: High, Probability: Medium
  - Mitigation: [Solution]
- **Priority 2:** [Risk] - Impact: High, Probability: Low
  - Mitigation: [Solution]"

---

## COMMON FOLLOW-UP QUESTIONS

1. **Why not waterfall?** - Unclear requirements, need user feedback
2. **Why not pure agile?** - System too large, need upfront design
3. **Timeline?** - 6 months for MVP, 1 year for production
4. **Team size?** - 25 developers in 5 teams (one per increment)
5. **Technology?** - Java, MySQL, Apache, Git
6. **Deployment?** - Cloud (AWS/Azure), Docker containers
7. **Scaling?** - Load balancing, database replication, caching
8. **Security?** - SSL/TLS, password hashing, OTP, encryption
9. **Testing?** - Unit (80%), Integration (60%), System (90%)
10. **Documentation?** - UML diagrams, API docs, user manual

---

## STUDY TIPS FOR VIVA

1. **Know your model inside out** - Be able to draw phases, cycles
2. **Relate to your project** - Every answer should link to GoPro
3. **Use diagrams** - Draw flowcharts, architecture diagrams
4. **Mention metrics** - Have numbers ready (users, revenue, timeline)
5. **Discuss trade-offs** - Why this model over others
6. **Know limitations** - Acknowledge what your model can't do
7. **Speak confidently** - Practice answers multiple times
8. **Ask clarifying questions** - If question is unclear, ask

---

## FINAL CHECKLIST

- [ ] Understand Waterfall, Prototype, Spiral, Agile models
- [ ] Know 5 types of feasibility studies
- [ ] Memorize GoPro's 15 modules
- [ ] Draw system architecture
- [ ] List 10 risks and mitigations
- [ ] Know development timeline
- [ ] Understand SDLC phases
- [ ] Explain module interactions
- [ ] Define technical vs. non-functional requirements
- [ ] Practice 20 sample questions

---

## LAST MINUTE NOTES

**Remember:**
- Prototype Model = Iterative + Early Feedback
- Feasibility = Technical + Economic + Operational + Schedule + Legal
- For GoPro = All feasibilities are GREEN
- Your advantage = 15 well-designed, modular components
- Highlight = Independent testing, scalability, maintainability

**Good Luck! 🎓**
