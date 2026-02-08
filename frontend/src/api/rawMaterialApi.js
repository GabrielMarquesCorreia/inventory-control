import api from "./api";

export const getRawMaterials = () =>
  api.get("/raw-materials");
