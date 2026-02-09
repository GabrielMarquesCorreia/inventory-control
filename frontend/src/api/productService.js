import api from "./api";

export const getProducts = () => api.get("/products");
export const createProduct = (product) => api.post("/products", product);
export const deleteProduct = (id) => api.delete(`/products/${id}`);
export const updateProduct = (id, product) => api.put(`/products/${id}`, product);
