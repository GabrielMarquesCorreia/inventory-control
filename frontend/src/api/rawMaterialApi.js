import api from "./api";

export const getRawMaterials = () => api.get("/raw-materials");
export const createRawMaterial = (material) => api.post("/raw-materials", material);
export const deleteRawMaterial = (id) => api.delete(`/raw-materials/${id}`);
