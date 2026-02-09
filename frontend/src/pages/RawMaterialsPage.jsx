import { useEffect, useState } from "react";
import { getRawMaterials, createRawMaterial, deleteRawMaterial, updateRawMaterial } from "../api/rawMaterialService";

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
    if (!name || !stock) return;

    if (editingId) {
      await updateRawMaterial(editingId, { name, stock: parseInt(stock) });
    } else {
      await createRawMaterial({ name, stock: parseInt(stock) });
    }

    setName("");
    setStock("");
    setEditingId(null);

    await loadMaterials();
    reloadPlan();
  };

  const handleEdit = (material) => {
    setEditingId(material.id);
    setName(material.name);
    setStock(material.stock);
  };

  const handleDelete = async (id) => {
    await deleteRawMaterial(id);
    await loadMaterials();
    reloadPlan();
  };

  return (
    <div>
      <h2>Raw Materials</h2>

      <form onSubmit={handleSubmit}>
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
      </form>

      <hr />

      <ul>
        {materials.map((m) => (
          <li key={m.id}>
            <b>{m.name}</b> – {m.stock}
            <button onClick={() => handleEdit(m)}>Edit</button>
            <button onClick={() => handleDelete(m.id)}>Delete</button>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default RawMaterialsPage;
