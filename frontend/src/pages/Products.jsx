import { useEffect, useState } from "react";
import api from "../api";
import { getProducts, createProduct, deleteProduct } from "../api/productService";
import { getRawMaterials } from "../api/rawMaterialApi";

function Products() {
  const [products, setProducts] = useState([]);
  const [rawMaterials, setRawMaterials] = useState([]);
  const [name, setName] = useState("");
  const [value, setValue] = useState("");
  const [selectedMaterial, setSelectedMaterial] = useState("");
  const [materialQuantity, setMaterialQuantity] = useState("");
  const [materials, setMaterials] = useState([]);

  const loadProducts = async () => {
    try {
      const res = await getProducts();
      setProducts(res.data);
    } catch (err) {
      console.error("Error loading products:", err);
    }
  };

  const loadRawMaterials = async () => {
    try {
      const res = await getRawMaterials();
      setRawMaterials(res.data);
    } catch (err) {
      console.error("Error loading raw materials:", err);
    }
  };

  useEffect(() => {
    loadProducts();
    loadRawMaterials();
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!name || !value) return;

    try {
      const res = await createProduct({ name, value: parseFloat(value) });
      const productId = res.data.id;

      for (const m of materials) {
        await api.post(
          `/product-materials?productId=${productId}&materialId=${m.rawMaterialId}&quantity=${m.quantity}`
        );
      }

      setName("");
      setValue("");
      setMaterials([]);
      loadProducts();
    } catch (err) {
      console.error("Error creating product:", err);
    }
  };

  const handleDelete = async (id) => {
    try {
      await deleteProduct(id);
      loadProducts();
    } catch (err) {
      console.error("Error deleting product:", err);
    }
  };

  const addMaterial = () => {
    if (!selectedMaterial || materialQuantity <= 0) return;

    setMaterials((prev) => [
      ...prev,
      { rawMaterialId: Number(selectedMaterial), quantity: Number(materialQuantity) },
    ]);

    setSelectedMaterial("");
    setMaterialQuantity("");
  };

  return (
    <div>
      <h2>Products</h2>

      <form onSubmit={handleSubmit}>
        <h4>Raw Materials</h4>

        <select
          value={selectedMaterial}
          onChange={(e) => setSelectedMaterial(e.target.value)}
        >
          <option value="">Select material</option>
          {rawMaterials.map((m) => (
            <option key={m.id} value={m.id}>{m.name}</option>
          ))}
        </select>

        <input
          type="number"
          placeholder="Quantity"
          value={materialQuantity}
          onChange={(e) => setMaterialQuantity(e.target.value)}
        />

        <button type="button" onClick={addMaterial}>Add Material</button>

        <ul>
          {materials.map((m, index) => {
            const materialName = rawMaterials.find(r => r.id === m.rawMaterialId)?.name;
            return <li key={index}>{materialName} – Qty: {m.quantity}</li>;
          })}
        </ul>

        <input
          placeholder="Product name"
          value={name}
          onChange={(e) => setName(e.target.value)}
        />

        <input
          placeholder="Value"
          type="number"
          value={value}
          onChange={(e) => setValue(e.target.value)}
        />

        <button type="submit">Add Product</button>
      </form>

      <hr />

      <ul>
        {products.map((p) => (
          <li key={p.id}>
            {p.name} – ${Number(p.value).toFixed(2)}
            <button onClick={() => handleDelete(p.id)}>Delete</button>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default Products;
