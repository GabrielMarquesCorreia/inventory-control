import api from "./api";

// Get all raw materials
export const getRawMaterials = () => {
  return api.get("/raw-materials");
};
