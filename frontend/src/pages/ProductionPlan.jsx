function ProductionPlan({ plan, loading }) {
  if (loading) return <p>Loading production plan...</p>;

  if (!plan.items.length) return <p>No products can be produced with current stock.</p>;

  return (
    <div>
      <h2>Production Plan</h2>

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
          {plan.items.map((item, idx) => (
            <tr key={idx}>
              <td>{item.productName}</td>
              <td>${(item.value / item.quantity).toFixed(2)}</td>
              <td>{item.quantity}</td>
              <td>${item.value.toFixed(2)}</td>
            </tr>
          ))}
        </tbody>
      </table>

      <h3>Total Production Value: ${plan.totalValue.toFixed(2)}</h3>
    </div>
  );
}

export default ProductionPlan;
