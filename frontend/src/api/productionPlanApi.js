// src/api/productionPlanApi.js
import axios from "axios";

export const getProductionPlan = async () => {
  return axios.get("http://localhost:8080/production-plan");
};
