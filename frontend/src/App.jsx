import { useState } from "react";

import ProductsPage from "./pages/ProductsPage";
import RawMaterialsPage from "./pages/RawMaterialsPage";
import ProductionPlan from "./pages/ProductionPlan";

function App() {
  const [page, setPage] = useState("products");

  return (
    <div style={{ padding: "20px" }}>
      {/* Menu */}
      <div style={{ marginBottom: "20px" }}>
        <button onClick={() => setPage("products")}>
          Products
        </button>

        <button
          onClick={() => setPage("materials")}
          style={{ marginLeft: "10px" }}
        >
          Raw Materials
        </button>

        <button
          onClick={() => setPage("plan")}
          style={{ marginLeft: "10px" }}
        >
          Production Plan
        </button>
      </div>

      {/* Pages */}
      {page === "products" && <ProductsPage />}
      {page === "materials" && <RawMaterialsPage />}
      {page === "plan" && <ProductionPlan />}
    </div>
  );
}

export default App;
