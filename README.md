Inventory Control

Web system for managing products and raw materials, with automatic production plan calculation prioritizing higher-value products. Developed as a practical coding test.

📌 Features

CRUD for Products

CRUD for Raw Materials

Association of Materials to Products (quantity needed for production)

Production Plan query (maximum producible quantity and total value)

Prioritizes higher-value products when raw materials are limited

🛠 Technologies Used

Back-end:

Java 17

Quarkus

JPA / Hibernate

PostgreSQL (or MySQL / Oracle)

Front-end:

React.js

Axios (API requests)

Vitest + React Testing Library (unit tests)

Other tools:

Git / GitHub

📂 Project Structure
frontend/      # React front-end
  src/
    pages/
    components/
    api/
backend/       # Quarkus back-end
  src/
    main/java/com/inventory
      entity/
      dto/
      repository/
      resource/
      service/
    test/
      java/com/inventory

⚡ Requirements Implemented
Functional Requirements (RF)

RF001: Product CRUD ✅

RF002: Raw Material CRUD ✅

RF003: Product ↔ Material association CRUD ✅

RF004: Query products that can be produced with available stock ✅

RF005: Product CRUD in UI ✅

RF006: Raw Material CRUD in UI ✅

RF007: Product-material association in UI ✅

RF008: Display Production Plan in UI ✅

Non-Functional Requirements (RNF)

RNF001: Web system compatible with Chrome/Firefox/Edge ✅

RNF002: API-based architecture (Back-end + Front-end) ✅

RNF003: Responsive UI ✅

RNF004: Relational database persistence (Postgres/MySQL/Oracle) ✅

RNF005: Back-end developed with Quarkus ✅

RNF006: Front-end developed with React ✅

RNF007: All code, database tables, and columns in English ✅

🚀 How to Run
Back-end

Configure your database (Postgres/MySQL/Oracle)

Update application.properties with database credentials

Run the back-end:

./mvnw quarkus:dev

Front-end

Navigate to the frontend/ folder

Install dependencies:

npm install


Run the front-end:

npm run dev


Open in browser at http://localhost:5173 (or the port Vite uses)

🧪 Testing

Front-end: Vitest + React Testing Library

npm run test


Back-end: Quarkus JUnit / REST Assured (e.g., RawMaterialResourceITest)

📈 Expected Results

Fully functional CRUD operations

Production Plan query shows:

Products that can be produced

Maximum producible quantity

Unit and total value

Products prioritized by value

Total production value calculated correctly

📌 Project Link

GitHub: https://github.com/GabrielMarquesCorreia/inventory-control