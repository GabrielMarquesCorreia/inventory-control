import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import RawMaterialsPage from './RawMaterialsPage.jsx';

import * as rawMaterialService from '../api/rawMaterialService.js';

beforeEach(() => {
  vi.resetAllMocks();
});

describe('RawMaterialsPage', () => {
  const mockReloadPlan = vi.fn();

  const mockMaterials = [
    { id: 1, name: 'Wood', stockQuantity: 100 },
    { id: 2, name: 'Steel', stockQuantity: 50 },
  ];

  vi.stubGlobal('alert', vi.fn());

  it('renders form inputs', async () => {
    vi.spyOn(rawMaterialService, 'getRawMaterials').mockResolvedValue({ data: [] });

    render(<RawMaterialsPage reloadPlan={mockReloadPlan} />);

    expect(await screen.findByPlaceholderText('Material name')).toBeInTheDocument();
    expect(screen.getByPlaceholderText('Stock')).toBeInTheDocument();
    expect(screen.getByRole('button', { name: /Add Material/i })).toBeInTheDocument();
  });

  it('renders materials table', async () => {
    vi.spyOn(rawMaterialService, 'getRawMaterials').mockResolvedValue({ data: mockMaterials });

    render(<RawMaterialsPage reloadPlan={mockReloadPlan} />);

    expect(await screen.findByText('Wood')).toBeInTheDocument();
    expect(screen.getByText('Steel')).toBeInTheDocument();
  });

  it('adds a new material', async () => {
    const createMaterialMock = vi
      .spyOn(rawMaterialService, 'createRawMaterial')
      .mockResolvedValue({ data: { id: 3, name: 'Plastic', stockQuantity: 200 } });

    vi.spyOn(rawMaterialService, 'getRawMaterials').mockResolvedValue({ data: [] });

    render(<RawMaterialsPage reloadPlan={mockReloadPlan} />);

    const nameInput = screen.getByPlaceholderText('Material name');
    const stockInput = screen.getByPlaceholderText('Stock');
    const submitButton = screen.getByRole('button', { name: /Add Material/i });

    await userEvent.type(nameInput, 'Plastic');
    await userEvent.type(stockInput, '200');

    fireEvent.click(submitButton);

    await waitFor(() => {
      expect(createMaterialMock).toHaveBeenCalledWith({
        name: 'Plastic',
        stockQuantity: 200,
      });
      expect(mockReloadPlan).toHaveBeenCalled();
    });
  });

  it('allows editing a material', async () => {
    vi.spyOn(rawMaterialService, 'getRawMaterials').mockResolvedValue({ data: mockMaterials });
    const updateMaterialMock = vi.spyOn(rawMaterialService, 'updateRawMaterial').mockResolvedValue({});

    render(<RawMaterialsPage reloadPlan={mockReloadPlan} />);

    const editButtons = await screen.findAllByText('Edit');
    fireEvent.click(editButtons[0]);

    const nameInput = screen.getByPlaceholderText('Material name');
    const stockInput = screen.getByPlaceholderText('Stock');

    expect(nameInput.value).toBe('Wood');
    expect(stockInput.value).toBe('100');

    await userEvent.clear(nameInput);
    await userEvent.type(nameInput, 'Oak Wood');
    await userEvent.clear(stockInput);
    await userEvent.type(stockInput, '120');

    const updateButton = screen.getByRole('button', { name: /Update Material/i });
    fireEvent.click(updateButton);

    await waitFor(() => {
      expect(updateMaterialMock).toHaveBeenCalledWith(1, {
        name: 'Oak Wood',
        stockQuantity: 120,
      });
      expect(mockReloadPlan).toHaveBeenCalled();
    });
  });

  it('allows deleting a material', async () => {
    vi.spyOn(rawMaterialService, 'getRawMaterials').mockResolvedValue({ data: mockMaterials });
    const deleteMaterialMock = vi.spyOn(rawMaterialService, 'deleteRawMaterial').mockResolvedValue({});

    render(<RawMaterialsPage reloadPlan={mockReloadPlan} />);

    const deleteButtons = await screen.findAllByText('Delete');
    fireEvent.click(deleteButtons[0]);

    await waitFor(() => {
      expect(deleteMaterialMock).toHaveBeenCalledWith(1);
      expect(mockReloadPlan).toHaveBeenCalled();
    });
  });
});
