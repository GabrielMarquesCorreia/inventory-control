📦 Inventory Control System

Frontend application for inventory and production control, developed with React and Vite.

This application provides a graphical interface to manage products, raw materials, and visualize production capacity based on available stock, consuming a REST API.

🚀 Technologies

React

Vite

JavaScript (ES6+)

Axios

HTML5

CSS3

📋 Requirements

Before running the project, make sure you have:

Node.js 18 or higher

npm

Backend API running

⚙️ Application Configuration

The frontend communicates with the backend through a REST API using Axios.

API configuration file:

src/api/api.js


Example configuration:

import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8080",
});

export default api;

📁 Project Structure
src/
├── api
│   ├── api.js
│   ├── productService.js
│   ├── rawMaterialService.js
│   └── productionPlanApi.js
├── pages
│   ├── ProductsPage.jsx
│   ├── RawMaterialsPage.jsx
│   └── ProductionPlan.jsx
├── App.jsx
├── main.jsx
└── index.css

⚙️ Features
📌 Products

Create, read, update, and delete products

Associate raw materials with products

📌 Raw Materials

Create, read, update, and delete raw materials

Manage stock quantity

📌 Production Planning

List products that can be produced based on available stock

Calculate the maximum production quantity per product

Prioritize products with higher value

Display the total estimated production value

💡 Product prioritization is handled by the backend to ensure correct business rules.

▶️ How to Run the Project
Install dependencies
npm install

Run the application
npm run dev


The application will be available at:

http://localhost:5173

🌐 Supported Browsers

Google Chrome

Mozilla Firefox

Microsoft Edge

📌 Notes

The frontend is fully decoupled from the backend.

The layout is responsive and works across different screen sizes.

All source code and naming conventions are written in English.

📄 License

This project was developed exclusively for technical evaluation purposes.