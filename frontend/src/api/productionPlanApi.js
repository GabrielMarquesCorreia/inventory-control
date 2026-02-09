import api from "./api";

export const getProductionPlan = () => api.get("/products/production-plan");
