import { useEffect, useState } from "react";
import { getRawMaterials } from "../api/rawMaterialApi";
import {
  getProducts,
  createProduct,
  deleteProduct
} from "../api/productService";

function Products() {
  const [products, setProducts] = useState([]);
  const [rawMaterials, setRawMaterials] = useState([]);

  const [name, setName] = useState("");
  const [value, setValue] = useState("");

  const [selectedMaterial, setSelectedMaterial] = useState("");
  const [materialQuantity, setMaterialQuantity] = useState("");
  const [materials, setMaterials] = useState([]);

  // -------------------------
  // LOAD DATA
  // -------------------------
  const loadProducts = async () => {
    const response = await getProducts();
    setProducts(response.data);
  };

  useEffect(() => {
    loadProducts();
    getRawMaterials().then((data) => setRawMaterials(data));
  }, []);

  // -------------------------
  // ACTIONS
  // -------------------------
  const handleSubmit = async (e) => {
    e.preventDefault();

    await createProduct({
      name,
      value,
      materials
    });

    setName("");
    setValue("");
    setMaterials([]);

    loadProducts();
  };

  const handleDelete = async (id) => {
    await deleteProduct(id);
    loadProducts();
  };

  const addMaterial = () => {
    if (!selectedMaterial || materialQuantity <= 0) return;

    setMaterials((prev) => [
      ...prev,
      {
        rawMaterialId: Number(selectedMaterial),
        quantity: Number(materialQuantity)
      }
    ]);

    setSelectedMaterial("");
    setMaterialQuantity("");
  };

  // -------------------------
  // RENDER
  // -------------------------
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
            <option key={m.id} value={m.id}>
              {m.name}
            </option>
          ))}
        </select>

        <input
          type="number"
          placeholder="Quantity"
          value={materialQuantity}
          onChange={(e) => setMaterialQuantity(e.target.value)}
        />

        <button type="button" onClick={addMaterial}>
          Add Material
        </button>

        <ul>
          {materials.map((m, index) => {
            const materialName = rawMaterials.find(
              (r) => r.id === m.rawMaterialId
            )?.name;

            return (
              <li key={index}>
                {materialName} – Qty: {m.quantity}
              </li>
            );
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
            {p.name} – ${p.value}
            <button onClick={() => handleDelete(p.id)}>
              Delete
            </button>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default Products;
