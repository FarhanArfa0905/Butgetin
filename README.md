<img src="https://raw.githubusercontent.com/FarhanArfa0905/Butgetin/115ed12cca367f71c6dc17463265f91c50395c35/Group%2072.png" alt="Gambar" width="300">   Butgetin

Butgetin is a capstone project aimed at helping families, especially children and teenagers, manage their finances effectively by fostering financial literacy and building good budgeting habits. This project integrates machine learning, cloud computing, and mobile development to deliver a budgeting app with real-time notifications, spending breakdowns, and parental oversight.

## Table of Contents

- [Overview](#overview)
- [Cloud Computing](#cloud-computing)
- [Features](#features)
- [System Architecture](#system-architecture)
- [Technology Stack](#technology-stack)
- [APIs](#apis)
- [Deployment](#deployment)
- [Team Members](#team-members)

---

## Overview

Butgetin helps families manage their finances by offering:

- Personalized budget recommendations
- Real-time expense tracking and notifications
- Family collaboration for financial goals

The app fosters financial literacy in children and teens through educational content and actionable insights based on their spending habits.

## Cloud Computing

The cloud architecture for Butgetin is designed to ensure scalability, security, and efficient resource management. Below is an overview of the architecture:

### Key Components:

1. **Google Cloud Run**:

   - Hosting the machine learning model for budget recommendations.
   - Auto-scaling to handle varying user demands.

2. **Google Cloud SQL**:

   - Hosting a MySQL database for storing user data, transactions, and family profiles.
   - Ensuring secure and consistent data availability.

3. **Google Cloud Storage**:

   - Storing static assets and backups.
   - Providing durable and cost-effective solutions for file storage.

4. **Cloud IAM**:

   - Managing secure access to GCP resources.
   - Implementing role-based permissions for enhanced security.

5. **Google App Engine**:

   - Deploying and managing the backend services with ease.
   - Providing scalability and support for dynamic workloads.

### Deployment with Google App Engine:
Deploying the backend and machine learning services is simplified using Google App Engine:

- Run the following command to deploy the application:
  ```console
  gcloud app deploy
  ```
- Ensure the `app.yaml` file is correctly configured for the service.
- Verify deployment using the Google Cloud Console.

## Features

1. **User Profile Management**
   - Create and manage user profiles
   - Family relationships support
2. **Budget Recommendations**
   - Powered by ML models
   - Simplified recommendations for new users
3. **Transaction Tracking**
   - Categorized spending analysis
   - Visual spending breakdowns

## System Architecture

The architecture consists of:

1. **Frontend:** Mobile app developed using Kotlin on Android Studio.
2. **Backend:** Built with Node.js, providing APIs for user authentication, budget recommendations, and transaction management.
3. **Machine Learning Model:** Deployed on Google Cloud Run, generating personalized budget suggestions.
4. **Database:** MySQL hosted on Google Cloud SQL for secure and scalable storage.

## Technology Stack

- **Frontend:** Kotlin, Android Studio
- **Backend:** Node.js, Express
- **Database:** MySQL (Google Cloud SQL)
- **Machine Learning:** Python, TensorFlow, deployed on Google Cloud Run
- **Cloud Services:**
  - Google Cloud Run
  - Google Cloud Storage
  - Google Cloud IAM
  - Google App Engine

## APIs

The backend exposes the following APIs:

### Authentication APIs:

| Endpoint               | Method | Body                                   | Description                     |
|------------------------|--------|----------------------------------------|---------------------------------|
| `/auth/register`       | POST   | fullname, email, password, confirm password | Register account for new user |
| `/auth/login`          | POST   | email, password                        | Login to access features       |
| `/auth/logout`         | POST   | Bearer Token                           | Logout from user               |
| `/auth/google`         | GET    | -                                      | Login using Google             |
| `/auth/google/callback`| GET    | -                                      | Callback for Google Login      |

### Profile APIs:

| Endpoint       | Method | Body                   | Description            |
|----------------|--------|------------------------|------------------------|
| `/api/profile` | GET    | Bearer Token           | Show user details      |
| `/api/profile` | PUT    | fullname, email        | Edit user profile      |
| `/api/profile` | DELETE | Bearer Token           | Delete user profile    |

### Transaction APIs:

| Endpoint                 | Method | Body                                   | Description                      |
|--------------------------|--------|----------------------------------------|----------------------------------|
| `/api/transaction`       | POST   | userId, date, amount, category, type, familyId | Create a transaction          |
| `/api/transactions`      | GET    | -                                      | Show transaction details        |
| `/api/transaction/:id`   | PUT    | date, amount, category                 | Update a transaction            |
| `/api/transaction/:id`   | DELETE | -                                      | Delete a transaction            |
| `/api/transaction-report`| GET    | id, startDate, endDate (params)        | Show transactions within a date range |
| `/api/transactions/export`| GET   | id, startDate, endDate (params)        | Export transactions to Excel    |

### Family APIs:

| Endpoint             | Method | Body                   | Description                 |
|----------------------|--------|------------------------|-----------------------------|
| `/api/family/add`    | POST   | name, relation, age, userId | Add a family member      |
| `/api/family`        | GET    | -                      | Show all family members    |
| `/api/:userId`       | GET    | -                      | Show family by userId      |

### Notification APIs:

| Endpoint                 | Method | Body                   | Description                 |
|--------------------------|--------|------------------------|-----------------------------|
| `/api/save-device-token` | POST   | userId, token          | Save device token          |
| `/api/send-notification` | POST   | userId, token          | Send notification          |

### Model Recommendation API:

| Endpoint           | Method | Body     | Description                     |
|--------------------|--------|----------|---------------------------------|
| `/api/recommendation` | POST   | userId   | Create a budget recommendation |

For detailed API testing, refer to the [Postman Collection](https://butgetin.postman.co/workspace/ButgetIn-Workspace~bfcfb51e-b7fa-4c13-884d-9f8ffd8e68ca/collection/34858811-6afb4cdc-e8f5-4a74-9c31-9886f34b0943?action=share&creator=34858811).

## Deployment

1. **Frontend Deployment:**
   - Install Android Studio
   - Clone the repository and build the app
2. **Backend Deployment:**
   - Deploy using Google App Engine:
     ```console
     gcloud app deploy
     ```
3. **ML Model Deployment:**
   - Model hosted on Google Cloud Run
   - Accessible via REST API

To deploy the project:

- Ensure all required environment variables are set up in `.env` files.
- Use the provided deployment scripts for each service.

## Team Members

| Bangkit ID  | Name         | Learning Path         | Profile                                      |
|-------------|--------------|-----------------------|----------------------------------------------|
| M384B4KY2591 | Muammar Farhan     | Machine Learning      | [![LinkedIn](https://img.shields.io/badge/LinkedIn-Profile-blue?style=flat&logo=linkedin)](https://www.linkedin.com/in/muammarfarhan2003) |
| M384B4KY3855 | Rifqi Yafik     | Machine Learning      | [![LinkedIn](https://img.shields.io/badge/LinkedIn-Profile-blue?style=flat&logo=linkedin)](https://www.linkedin.com/in/rifqiyafik) |
| M185B4KY2111 | Julian Kurnianto     | Machine Learning      | [![LinkedIn](https://img.shields.io/badge/LinkedIn-Profile-blue?style=flat&logo=linkedin)](https://www.linkedin.com/in/julian-kurnianto-190096233) |
| A611B4KX2194 | Khadija Karepesina   | Mobile Development    | [![LinkedIn](https://img.shields.io/badge/LinkedIn-Profile-blue?style=flat&logo=linkedin)](https://www.linkedin.com/in/khadija-karepesina-2908ab310) |
| A390B4KY1490 | Feris Sebayang  | Mobile Development       | [![LinkedIn](https://img.shields.io/badge/LinkedIn-Profile-blue?style=flat&logo=linkedin)](https://www.linkedin.com/in/feris-sebayang-3672552ab) |
| C384B4KY0553 | Anggi Muammar Hanafi    |  Cloud Computing    | [![LinkedIn](https://img.shields.io/badge/LinkedIn-Profile-blue?style=flat&logo=linkedin)](https://www.linkedin.com/in/anggi-muammar-hanafi) |
| C180B4KX3921 | Rizkiyatun Nafisah   | Cloud Computing       | [![LinkedIn](https://img.shields.io/badge/LinkedIn-Profile-blue?style=flat&logo=linkedin)](https://www.linkedin.com/in/rizkiyatun-nafisah-a1644b269) |

---

For more information, refer to our [Project Wiki](wiki-link).

Feel free to report issues or contribute to the project by creating a pull request.

