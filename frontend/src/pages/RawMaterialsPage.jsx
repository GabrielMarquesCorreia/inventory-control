import { useEffect, useState } from "react";
import { getRawMaterials } from "../api/rawMaterialService";

function RawMaterialsPage() {
  const [materials, setMaterials] = useState([]);
  const [error, setError] = useState(null);

  useEffect(() => {
  getRawMaterials()
    .then((response) => {
      setMaterials(response.data);
    })
    .catch(() => {
      setError("Error loading raw materials");
    });
}, []);

  return (
    <div>
      <h1>Raw Materials</h1>

      {error && <p style={{ color: "red" }}>{error}</p>}

      <table border="1" cellPadding="5">
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Stock</th>
          </tr>
        </thead>
        <tbody>
          {materials.map((m) => (
            <tr key={m.id}>
              <td>{m.id}</td>
              <td>{m.name}</td>
              <td>{m.stock}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default RawMaterialsPage;
