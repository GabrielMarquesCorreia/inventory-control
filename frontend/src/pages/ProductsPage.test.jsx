import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import ProductsPage from './ProductsPage.jsx';

import * as productService from '../api/productService.js';
import * as rawMaterialService from '../api/rawMaterialService.js';
import * as productMaterialService from '../api/productService.js';

beforeEach(() => {
  vi.resetAllMocks();
});

describe('ProductsPage', () => {
  const mockReloadPlan = vi.fn();

  const mockProducts = [
    { id: 1, name: 'Table', value: 50, materials: [] },
    { id: 2, name: 'Chair', value: 30, materials: [] },
  ];

  const mockMaterials = [
    { id: 1, name: 'Wood' },
    { id: 2, name: 'Steel' },
  ];

  vi.stubGlobal('alert', vi.fn());

  it('renders form inputs', async () => {
    vi.spyOn(productService, 'getProducts').mockResolvedValue({ data: [] });
    vi.spyOn(rawMaterialService, 'getRawMaterials').mockResolvedValue({ data: [] });

    render(<ProductsPage reloadPlan={mockReloadPlan} />);

    expect(await screen.findByPlaceholderText('Product name')).toBeInTheDocument();
    expect(screen.getByPlaceholderText('Value')).toBeInTheDocument();
    expect(screen.getByRole('button', { name: /Add Product/i })).toBeInTheDocument();
  });

  it('renders products table', async () => {
    vi.spyOn(productService, 'getProducts').mockResolvedValue({ data: mockProducts });
    vi.spyOn(rawMaterialService, 'getRawMaterials').mockResolvedValue({ data: [] });

    render(<ProductsPage reloadPlan={mockReloadPlan} />);

    expect(await screen.findByText('Table')).toBeInTheDocument();
    expect(screen.getByText('Chair')).toBeInTheDocument();
  });

  it('allows editing a product', async () => {
    vi.spyOn(productService, 'getProducts').mockResolvedValue({ data: mockProducts });
    vi.spyOn(rawMaterialService, 'getRawMaterials').mockResolvedValue({ data: [] });
    const updateProductMock = vi.spyOn(productService, 'updateProduct').mockResolvedValue({});

    render(<ProductsPage reloadPlan={mockReloadPlan} />);

    const editButtons = await screen.findAllByText('Edit');
    fireEvent.click(editButtons[0]); 

    const nameInput = screen.getByPlaceholderText('Product name');
    expect(nameInput.value).toBe('Table');

    await userEvent.clear(nameInput);
    await userEvent.type(nameInput, 'Big Table');

    const updateButton = screen.getByRole('button', { name: /Update Product/i });
    fireEvent.click(updateButton);

    await waitFor(() => {
      expect(updateProductMock).toHaveBeenCalledWith(1, { name: 'Big Table', value: 50 });
      expect(mockReloadPlan).toHaveBeenCalled();
    });
  });

  it('allows deleting a product', async () => {
    vi.spyOn(productService, 'getProducts').mockResolvedValue({ data: mockProducts });
    vi.spyOn(rawMaterialService, 'getRawMaterials').mockResolvedValue({ data: [] });
    const deleteProductMock = vi.spyOn(productService, 'deleteProduct').mockResolvedValue({});

    render(<ProductsPage reloadPlan={mockReloadPlan} />);

    const deleteButtons = await screen.findAllByText('Delete');
    fireEvent.click(deleteButtons[0]);

    await waitFor(() => {
      expect(deleteProductMock).toHaveBeenCalledWith(1);
      expect(mockReloadPlan).toHaveBeenCalled();
    });
  });
});
