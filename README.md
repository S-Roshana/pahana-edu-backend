📘 Pahana Edu Backend
This is the Spring Boot backend server for the Pahana Edu Online Bookshop.
It provides REST APIs for:

Customer registration and login
Customer profile management (with update features)
Order placement and order history
Book information management
The backend is built with:
Spring Boot
MongoDB (for data storage)

🚀 How to Run the Backend Server
1️⃣ Clone the repository
👉 In your terminal, navigate to where you want to place the project, and run:


git clone https://github.com/S-Roshana/pahana-edu-backend.git

cd pahana-edu-backend
➡ This will put you inside the pahana-edu-backend directory.

2️⃣ Prerequisites
Before running the project, ensure you have:

**Java 17 (or compatible version)
Maven 3.6+
MongoDB (running locally on default port 27017)**

👉 Verify installations:

java -version
mvn -version
mongo --version

3️⃣ Configure MongoDB connection
➡ Open src/main/resources/application.properties
➡ Example configuration:

**properties**

spring.data.mongodb.uri=mongodb://localhost:27017/pahanaedu
spring.jpa.hibernate.ddl-auto=none
server.port=8080

✅ pahanaedu is the database name (When running the spring boot application it will be created automatically by MongoDB if not present).

4️⃣ Build the project
👉 While inside the pahana-edu-backend/ directory:


mvn clean install
➡ **This will compile your code and package it as a jar file under:**

pahana-edu-backend/target/

5️⃣ Run the server
👉 From the pahana-edu-backend/ directory:

**mvn spring-boot:run**
or

**java -jar target/pahana-edu-backend-0.0.1-SNAPSHOT.jar**


✅ The backend will start at:
http://localhost:8080

⚡ Example API Endpoints

POST /api/customers/register → Register a new customer
POST /api/customers/login → Customer login
PUT /api/customers/{id} → Update customer profile
GET /api/orders/byCustomer/{contact} → Fetch order history by contact
POST /api/orders/place → Place a new order
GET /api/books → List books (if implemented)

📝 Notes
✅ Ensure your MongoDB server is running before starting the Spring Boot app:

✅ By default, it connects to:

**mongodb://localhost:27017/pahanaedu**

You can adjust this in application.properties.

