# 💳 Stripe Payment Service Provider (PSP) Integration System
**Tech Stack:** Core Java • Spring Boot • MySQL • Stripe API • Maven • Spring Security

## 📌 Overview
This project is a **Stripe Payment Service Provider Integration System** built using **Core Java** and **Spring Boot**.  
The main goal is to simulate how real-world **payment processing systems (PSPs)** like **Stripe** or **Razorpay** work in production environments.

It provides **secure APIs for user/merchant onboarding, payment processing, webhook handling, refunds, disputes, and reporting.**

## ✨ Features

### 🔑 User & Merchant Management
- Secure **user & merchant registration/login** (Spring Security).
- Merchants can store business details such as **name, email, and Stripe account ID**.
- **Role-based access control (RBAC):**
    - Users → View their own payments.
    - Merchants → Manage their payments & customers.
    - Admins → Full system visibility.

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

### 📑 Refund & Dispute Management
- Merchants can trigger **refunds** via the system.
- Disputes are automatically logged for **admin review**.

### 📊 Transaction Dashboard
- Filter transactions by **date, status, amount, or currency**.
- Export history in **CSV or PDF format**.

### 🔄 Reconciliation & Analytics
- **Automated reconciliation** with Stripe to ensure DB matches Stripe records.
- **Analytics dashboard** with insights like:
    - Total revenue
    - Number of refunds
    - Dispute statistics

### 📩 Notifications
- **Email & SMS alerts** for:
    - Payment success/failure
    - Refunds
    - Disputes

## 🛠️ Tech Stack

- **Backend:** Core Java 17, Spring Boot
- **Database:** MySQL (transactions, users, merchants)
- **Payment Gateway:** Stripe API (Payment Intents + Webhooks)
- **Security:** Spring Security, Role-based Access Control
- **Build Tool:** Maven
- **Other Tools:** Lombok, Postman (API testing)
- **Optional:** Twilio (SMS), SendGrid/Mailgun (Email notifications)

## ⚙️ Project Setup

### 1. Clone the repository
    ```bash
    git clone https://github.com/your-username/stripe-psp-system.git
    cd stripe-psp-system

2. Configure Environment Variables
    Create a file named .env or use IntelliJ Run Configurations:
    properties
    Copy
    Edit
    STRIPE_SECRET_KEY=sk_test_xxxxxxxxxxxxx
    STRIPE_PUBLISHABLE_KEY=pk_test_xxxxxxxxx
    DB_URL=jdbc:mysql://localhost:3306/stripe_psp
    DB_USERNAME=root
    DB_PASSWORD=yourpassword
    MAIL_API_KEY=your-email-service-key
    SMS_API_KEY=your-sms-service-key

3. Setup Database (MySQL)
    sql
    Copy
    Edit
    CREATE DATABASE stripe_psp;

4. Run the Application
bash
Copy
Edit
mvn spring-boot:run
The application will start at:
👉 http://localhost:8080

📡 API Endpoints
Auth & User Management
POST /auth/register → Register User/Merchant
POST /auth/login → Login & get JWT token

Payment APIs
POST /payments/create-intent → Create payment intent
GET /payments/{id} → Get payment details
POST /payments/refund/{id} → Refund a payment

Webhook Listener
POST /webhooks/stripe → Stripe will send real-time events here
Transactions & Dashboard
GET /transactions → List all transactions (filter by status/date/etc.)
GET /transactions/export?format=csv → Export as CSV
GET /transactions/export?format=pdf → Export as PDF

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
✅ Role-based access (User/Merchant/Admin).

📌 Upcoming Improvements (Future Roadmap)
✅ More advanced reconciliation (scheduled cron jobs).
✅ Enhanced analytics dashboard with charts (Recharts/Chart.js).
✅ Multi-currency support.
✅ Integration with Razorpay as an alternative PSP.

🚀 Project Learnings
Hands-on experience integrating third-party PSP (Stripe).
Building production-like payment workflows (refunds, disputes, reconciliation).
Working with Spring Boot Webhooks.
Designing secure, real-time fintech systems.

