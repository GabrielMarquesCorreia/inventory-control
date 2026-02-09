import { useState, useEffect } from "react";
import ProductsPage from "./pages/ProductsPage";
import RawMaterialsPage from "./pages/RawMaterialsPage";
import ProductionPlan from "./pages/ProductionPlan";
import { getProductionPlan } from "./api/productionPlanApi";

function App() {
  const [currentPage, setCurrentPage] = useState("products");
  const [productionPlan, setProductionPlan] = useState({ items: [], totalValue: 0 });
  const [loading, setLoading] = useState(true);

  const loadProductionPlan = async () => {
    setLoading(true);
    try {
      const res = await getProductionPlan();
      setProductionPlan(res.data);
    } catch (err) {
      console.error("Error loading production plan", err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadProductionPlan();
  }, []);

  return (
    <div style={{ padding: "20px" }}>
      <h1>Inventory Control</h1>

      <div style={{ marginBottom: "20px" }}>
        <button onClick={() => setCurrentPage("products")}>Products</button>
        <button onClick={() => setCurrentPage("raw-materials")}>Raw Materials</button>
        <button onClick={() => setCurrentPage("production-plan")}>Production Plan</button>
      </div>

      {currentPage === "products" && <ProductsPage reloadPlan={loadProductionPlan} />}
      {currentPage === "raw-materials" && <RawMaterialsPage reloadPlan={loadProductionPlan} />}
      {currentPage === "production-plan" && (
        <ProductionPlan plan={productionPlan} loading={loading} />
      )}
    </div>
  );
}

export default App;
