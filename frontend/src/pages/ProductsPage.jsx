import React, { useEffect, useState } from "react";
import { getProducts, createProduct, deleteProduct, updateProduct } from "../api/productService";
import { getRawMaterials, addMaterialToProduct } from "../api/rawMaterialService";
import api from "../api/api";

function ProductsPage({ reloadPlan }) {
  const [products, setProducts] = useState([]);
  const [rawMaterials, setRawMaterials] = useState([]);
  const [name, setName] = useState("");
  const [value, setValue] = useState("");
  const [editingProductId, setEditingProductId] = useState(null);
  const [selectedMaterial, setSelectedMaterial] = useState("");
  const [materialQuantity, setMaterialQuantity] = useState("");
  const [materialsForNewProduct, setMaterialsForNewProduct] = useState([]);

  const loadProducts = async () => {
    const res = await getProducts();
    setProducts(res.data);
  };

  const loadRawMaterials = async () => {
    const res = await getRawMaterials();
    setRawMaterials(res.data);
  };

  useEffect(() => {
    loadProducts();
    loadRawMaterials();
  }, []);

  const handleAddMaterial = () => {
    if (!selectedMaterial || materialQuantity <= 0) return;
    const material = rawMaterials.find(m => m.id === parseInt(selectedMaterial));
    if (!material) return;

    setMaterialsForNewProduct(prev => [
      ...prev,
      { ...material, quantity: parseInt(materialQuantity) }
    ]);

    setSelectedMaterial("");
    setMaterialQuantity("");
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!name || !value) return;

    if (!editingProductId && materialsForNewProduct.length === 0) {
      alert("Please add at least one material for the new product.");
      return;
    }

    let productId;
    if (editingProductId) {
      await updateProduct(editingProductId, { name, value: parseFloat(value) });
      productId = editingProductId;
    } else {
      const res = await createProduct({ name, value: parseFloat(value) });
      productId = res.data.id;

      for (let mat of materialsForNewProduct) {
        await addMaterialToProduct(productId, mat.id, mat.quantity);
      }
    }

    setName("");
    setValue("");
    setEditingProductId(null);
    setMaterialsForNewProduct([]);
    await loadProducts();
    reloadPlan();
  };

  const handleUpdateMaterialQuantity = async (prmId, newQuantity) => {
    if (newQuantity <= 0) return;
    try {
      await api.put(`/product-materials/${prmId}?quantity=${parseInt(newQuantity)}`);
      await loadProducts();
      reloadPlan();
    } catch (err) {
      console.error("Error updating material quantity", err);
    }
  };

  const handleDelete = async (id) => {
    await deleteProduct(id);
    await loadProducts();
    reloadPlan();
  };

  const handleEdit = (product) => {
    setEditingProductId(product.id);
    setName(product.name);
    setValue(product.value);
    setMaterialsForNewProduct([]);
  };

  return (
    <div className="main-container">
      <h2>Products</h2>

      <form onSubmit={handleSubmit} className="form-block">

        {/* Materials */}
        <div style={{ marginBottom: "30px" }}>
          <h2>Materials for new product:</h2>

          {editingProductId === null && (
            <div className="form-row">
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

              <button type="button" onClick={handleAddMaterial}>
                Add Material
              </button>
            </div>
          )}

          {materialsForNewProduct.length > 0 && (
            <ul style={{ marginTop: "10px" }}>
              {materialsForNewProduct.map((m) => (
                <li key={m.id}>
                  {m.name} – {m.quantity}
                </li>
              ))}
            </ul>
          )}
        </div>

        {/* Product inputs */}
        <div className="form-row">
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

          <button type="submit">
            {editingProductId ? "Update Product" : "Add Product"}
          </button>
        </div>

      </form>

      <hr />

      <table>
        <thead>
          <tr>
            <th>Product</th>
            <th>Unit Value</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {products.map((p) => (
            <React.Fragment key={p.id}>
              <tr>
                <td>{p.name}</td>
                <td>${p.value?.toFixed(2) || 0}</td>
                <td>
                  <button onClick={() => handleEdit(p)}>Edit</button>
                  <button onClick={() => handleDelete(p.id)}>Delete</button>
                </td>
              </tr>
              {p.materials?.length > 0 &&
                p.materials.filter(m => m.rawMaterial).map((m) => (
                  <tr key={m.id}>
                    <td style={{ paddingLeft: "20px" }}>– {m.rawMaterial.name}</td>
                    <td>
                      <input
                        type="number"
                        value={m.quantity}
                        onChange={(e) =>
                          handleUpdateMaterialQuantity(m.id, e.target.value)
                        }
                        style={{ width: "60px" }}
                      />
                    </td>
                    <td></td>
                  </tr>
                ))
              }
            </React.Fragment>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default ProductsPage;
