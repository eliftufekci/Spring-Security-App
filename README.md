Spring Security Full Demo

📌 Project Overview
This project is a fully-featured security application developed with Spring Boot and Spring Security. It demonstrates comprehensive user authentication and authorization processes.
A JWT-based stateless authentication system has been implemented, including token generation and validation.

Key Features

* OAuth 2.0 login (GitHub)
* reCAPTCHA integration to prevent bot access
* Sign Up / Login functionality
* Email account activation: Upon registration, an activation code is sent to the user’s email for account verification.

---

📌 Features

1. User Registration (Sign Up)

* Users can register by providing the required information.
* reCAPTCHA is used during registration to prevent bots.
* An activation code is sent to the user’s email.
* The account becomes active once the activation code is verified.

2. Login

* Users can log in with their email and password.
* Alternatively, users can log in via GitHub OAuth 2.0

3. Account Activation

* Users must enter the activation code received by email to activate their account.
* Accounts cannot log in before activation.

4. Security

* Role-based access control using Spring Security
* Password encryption
* Bot protection via reCAPTCHA
* Secure third-party login with OAuth 2.0

---

📌 Technologies

* Java 8
* Spring Boot
* Spring Security
* OAuth 2.0
* reCAPTCHA
* JavaMail (for activation emails)
* Maven

---

📌 Installation and Running

1. Clone the repository:

bash
>> git clone https://github.com/elif4/security-FullDemo.git
>> cd security-FullDemo

2. Install Maven dependencies:

bash
>> mvn clean install

3. Run the application:

bash
>> mvn spring-boot:run


---

📌 Notes

* Configure OAuth and email settings in application.properties or .env
* Obtain reCAPTCHA keys from the Google reCAPTCHA dashboard.
