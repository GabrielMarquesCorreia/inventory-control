import api from "./api";

export const getRawMaterials = () => api.get("/raw-materials");
export const createRawMaterial = (material) => api.post("/raw-materials", material);
export const updateRawMaterial = (id, material) => api.put(`/raw-materials/${id}`, material);
export const deleteRawMaterial = (id) => api.delete(`/raw-materials/${id}`);

export const addMaterialToProduct = (productId, materialId, quantity) => {
  return api.post(`/product-materials?productId=${productId}&materialId=${materialId}&quantity=${quantity}`);
};

export const removeMaterialFromProduct = (prmId) => {
  return api.delete(`/product-materials/${prmId}`);
};

export const updateMaterialQuantity = (prmId, quantity) => {
  return api.put(`/product-materials/${prmId}?quantity=${quantity}`);
};
