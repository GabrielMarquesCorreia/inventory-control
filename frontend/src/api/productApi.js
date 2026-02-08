const API_URL = "http://localhost:8080/products";

// Get all products
export async function getAllProducts() {
  const response = await fetch(API_URL);

  if (!response.ok) {
    throw new Error("Error loading products");
  }

  return response.json();
}
