import api from "./api";

export const getProducts = () => api.get("/products");

export const createProduct = (product) =>
  api.post("/products", product);

export const deleteProduct = (id) =>
  api.delete(`/products/${id}`);
