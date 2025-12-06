# 💳 Stripe Payment Service Provider (PSP) Integration System

## 📌 Overview
Contributed to building a secure and scalable Stripe Payment Integration System using Java Spring Boot in a microservices 
architecture on AWS. Participated in implementing payment processing, payment status management, Stripe PSP REST API 
integration, notification handling, security enhancements, and error management. Explored Spring AI with OpenAI LLM and 
integrated ActiveMQ for asynchronous messaging. Followed RESTful standards, applied design patterns, and strengthened 
debugging and problem-solving skills. 
This project is a **Stripe Payment Service Provider Integration System** built using **Core Java** and **Spring Boot**.  
The main goal is to simulate how real-world **payment processing systems (PSPs)** like **Stripe** or **Razorpay** work in production environments.

It provides **secure APIs for user/merchant onboarding, payment processing, webhook handling, refunds, disputes, and reporting.**

## ✨ Features

### 🔑 User & Merchant Management
- Secure **user & merchant registration/login** (Spring Security).
- Merchants can store business details such as **name, email, and Stripe account ID**.

### 💳 Payment Flow (Stripe Integration)
- Supports **Payment Intents API** (Stripe).
- Multiple payment methods: **Cards, UPI, Google Pay, Apple Pay, Wallets**.
- Payment statuses: **Pending → Success → Failed → Refunded → Disputed**.
- Sensitive card details are **never stored**; only masked details are shown.

### 🔔 Real-time Webhooks
- Listens to **Stripe webhook events** like:
    - `payment_intent.succeeded`
    - `payment_intent.payment_failed`
    - `charge.refunded`
    - `charge.dispute.created`
- Updates transaction records in **real time**.

## 🛠️ Tech Stack

•	**Programming Languages:** Java 17
•	**Frameworks:** Spring Boot, Spring AI,  Spring Boot JDBC
•	Microservices Architecture
•	**AI:** Spring AI, OpenAI LLM, Llama LLM, Vibe Coding(Github Copilot, Cursor AI)
•	**RESTful APIs:** JSON, CURL, Postman, Swagger
•	**Databases:** MySQL (RDS)
•	**Version Control Systems:** Git, BitBucket, SourceTree
•	**Build Tools:** Maven
•	**Application Server:** Tomcat
•	**Testing Frameworks:** Unit Testing(JUnit), Mocking(Mockito), Code Coverage
•	**Cloud Platforms:** AWS: EC2, RDS, SecretManager
•	**IDEs:** Intellij IDE, DBEaver
•	**Async technology:** ActiveMQ
•	**Agile Methodologies:** Scrum, Agile Development
•	**Project management tool:** Jira
•	Logging, Debugging & Troubleshooting: Eclipse, Slf4J with Logback
•	Performance Tuning | Design Patterns | Code Review | Data Structures | Algorithms
•	**Other tools:** Jackson, Lombok, Sonar Lint, Mobaxterm


## ⚙️ Project Setup

### 1. Clone the repository
    git clone https://github.com/your-username/stripe-psp-system.git
    cd stripe-psp-system
 Or run this Jar Files:  `https://drive.google.com/drive/folders/1EigT1pD_F5GNsbWajSyCizjxiJX1s3Mc?usp=sharing`
   
Use CMD: `java -jar Payment-Processing-Service.jar` and `java -jar stripe-provider-service.jar`
    
### 2. Configure Environment Variables
  
   `stripe.secret.key=<stripe-secret-key>`
   
   `stripe.webhook.secret=<stripe-webhook-secret>`

### 3. Setup Database (MySQL)
   Use: `https://github.com/VivekVishwakarma12345/Stripe-Payment-Integration/tree/main/DB`
   
   Run `ddl-script.sql` and `dml-script.sql` in DBeaver or Mysql WorkBanch
   
   Use `SELECT * FROM payments.Payment_Method;`
       ``SELECT * FROM payments.`transaction` t`` To See Data

### 4. Notification System
   Install Stripe ClI: `https://drive.google.com/drive/folders/175jjWHmsU887JmNSVCKBWVSPYl3T7hDt?usp=sharing` or `https://docs.stripe.com/stripe-cli/install`
   
   USE: `https://docs.stripe.com/stripe-cli/use-cli`
   
   _Stripe listen forward CMD:_ 👉 `stripe listen --forward-to localhost:8083/v1/stripe/webhook`
   
### 5. Run the Application User POSTMAN
**Payment APIs For Stripe Payment Integraction**

_GET: For Health Check_ 👉 `localhost:8083/health_check`
    
_POST : For Create Stripe Session_ 👉 `http://localhost:8083/payment`
    
_POST : For Update Stripe Session_ 👉 `http://localhost:8083/payment/{"id"}`
     
_GET : For Retieve Stripe Session_ 👉 `http://localhost:8083/payment/{"id"}`
    
_GET : For Expire Stripe Session_ 👉 `http://localhost:8083/payment/expire/{"id"}`

**Payment Stripe CLI:**
    
_Stripe listen forward CMD:_ 👉 `stripe listen --forward-to localhost:8083/v1/stripe/webhook`

**Payment APIs For Payment Processing System:**
    
_GET: For Health Check_ 👉 `localhost:8082/health_check`
    
_POST: For Create Transection_ 👉 `localhost:8082/v1/payments` 

_Body - row - JSON_

    `{
      "userId": 102,
      "paymentMethod": "APM",
      "provider": "STRIPE",
      "paymentType": "SALE",
      "amount": 5.75,
      "currency": "EUR",
      "merchantTransactionReference": "ORD-20250903-XYZ"
    }`
    
_POST: For Initiate Transection_ 👉 `localhost:8082/v1/payments/{"txnReference"}>/initiate` 
    
_Body - row - JSON_

    `{
        "successUrl": "https://yourdomain.com/payment/success",
        "cancelUrl": "https://yourdomain.com/payment/cancel",
        "lineItems": [
            {
            "currency": "USD",
            "quantity": 1,
            "productName": "Test Product",
            "unitAmount": 1000
            }
        ]
    }`
    

📊 Demo Flow
Merchant registers their account.
Merchant creates a payment intent via API.
User completes payment using Stripe Checkout.
Stripe sends webhook events → Application updates transaction status.
Merchant can view dashboard, trigger refunds, or handle disputes.
Admin can monitor everything, view analytics & reconcile records.

🔐 Security & Compliance
✅ No sensitive card data stored locally.
✅ Stripe keys secured in environment variables.
✅ Only masked card details displayed.

🚀 Project Learnings
Hands-on experience integrating third-party PSP (Stripe).
Building production-like payment workflows (refunds, disputes, reconciliation).
Working with Spring Boot Webhooks.
Designing secure, real-time fintech systems.

👤 Author
Vivek Vishwakarma
Java Full Stack Developer
[LinkedIn](https://www.linkedin.com/in/vivek-vishwakarma-) | [GitHub](https://github.com/VivekVishwakarma12345) | [BitBucket](https://bitbucket.org/spspismain/stripe-psp-integration/src/main/)
