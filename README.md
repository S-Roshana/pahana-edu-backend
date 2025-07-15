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

MongoDB Compass (running locally on default port 27017)**

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

✅ pahandb is the database name (When running the spring boot application it will be created automatically by MongoDB if not present).

## 🔧 Setup Instructions

1. **Clone the repository:**
   ```bash
   git clone https://github.com/S-Roshana/pahana-edu-backend.git
   
   cd pahana-edu-backend

**Start MongoDB (use MongoDB Compass or mongod in CLI)**

👉 While inside the pahana-edu-backend/ directory:

./mvnw spring-boot:run


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

