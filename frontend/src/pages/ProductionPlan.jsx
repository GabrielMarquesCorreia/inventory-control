import { useEffect, useState } from "react";
import { getProductionPlan } from "../api/productionPlanApi";

function ProductionPlan() {
  const [items, setItems] = useState([]);
  const [totalValue, setTotalValue] = useState(0);
  const [loading, setLoading] = useState(true);

  const loadProductionPlan = async () => {
    try {
      const response = await getProductionPlan();
      setItems(response.data.items);
      setTotalValue(response.data.totalValue);
    } catch (error) {
      console.error("Error loading production plan", error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadProductionPlan();
  }, []);

  if (loading) {
    return <p>Loading production plan...</p>;
  }

  return (
    <div>
      <h2>Production Plan</h2>

      {items.length === 0 ? (
        <p>No products can be produced with current stock.</p>
      ) : (
        <>
          <table border="1" cellPadding="8">
            <thead>
              <tr>
                <th>Product</th>
                <th>Unit Value</th>
                <th>Quantity</th>
                <th>Total Value</th>
              </tr>
            </thead>
            <tbody>
              {items.map((item) => (
                <tr key={item.productId}>
                  <td>{item.productName}</td>
                  <td>${item.unitValue}</td>
                  <td>{item.quantity}</td>
                  <td>${item.totalValue}</td>
                </tr>
              ))}
            </tbody>
          </table>

          <h3>Total Production Value: ${totalValue}</h3>
        </>
      )}
    </div>
  );
}

export default ProductionPlan;
