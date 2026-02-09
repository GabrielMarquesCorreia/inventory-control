import React, { useEffect, useState } from "react";
import {
  getRawMaterials,
  createRawMaterial,
  deleteRawMaterial,
  updateRawMaterial,
} from "../api/rawMaterialService";

function RawMaterialsPage({ reloadPlan }) {
  const [materials, setMaterials] = useState([]);
  const [name, setName] = useState("");
  const [stock, setStock] = useState("");
  const [editingId, setEditingId] = useState(null);

  const loadMaterials = async () => {
    const res = await getRawMaterials();
    setMaterials(res.data);
  };

  useEffect(() => {
    loadMaterials();
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!name || !stock) {
      alert("Preencha todos os campos");
      return;
    }

    const parsedStock = parseInt(stock, 10);
    if (isNaN(parsedStock)) {
      alert("Stock precisa ser um número válido");
      return;
    }

    try {
      if (editingId) {
        await updateRawMaterial(editingId, { name, stockQuantity: parsedStock });
      } else {
        await createRawMaterial({ name, stockQuantity: parsedStock });
      }

      // Reset form
      setName("");
      setStock("");
      setEditingId(null);

      // Reload
      await loadMaterials();
      reloadPlan();
    } catch (error) {
      console.error("Error saving material:", error);
      alert("Failed to save material. Please check the data and try again.");
    }
  };

  const handleEdit = (material) => {
    setEditingId(material.id);
    setName(material.name);
    setStock(material.stockQuantity);
  };

  const handleDelete = async (id) => {
    await deleteRawMaterial(id);
    await loadMaterials();
    reloadPlan();
  };

  return (
    <div className="main-container">
      <h2>Raw Materials</h2>

      <form onSubmit={handleSubmit}>
        <div className="form-row">
          <input
            placeholder="Material name"
            value={name}
            onChange={(e) => setName(e.target.value)}
          />
          <input
            type="number"
            placeholder="Stock"
            value={stock}
            onChange={(e) => setStock(e.target.value)}
          />
          <button type="submit">{editingId ? "Update Material" : "Add Material"}</button>
        </div>
      </form>

      <table>
        <thead>
          <tr>
            <th>Material</th>
            <th>Stock</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {materials.map((m) => (
            <tr key={m.id}>
              <td>{m.name}</td>
              <td>{m.stockQuantity}</td>
              <td>
                <button onClick={() => handleEdit(m)}>Edit</button>
                <button onClick={() => handleDelete(m.id)}>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default RawMaterialsPage;
