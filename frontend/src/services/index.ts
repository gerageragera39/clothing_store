import { apiClient } from './api';
import type { AuthResponse, Clothes } from '../types';

export interface LoginRequest {
  email: string;
  password: string;
}

export interface RegisterRequest {
  email: string;
  password: string;
  firstName: string;
  lastName: string;
}

export const authService = {
  login: async (data: LoginRequest): Promise<AuthResponse> => {
    const response = await apiClient.post<AuthResponse>('/auth/login', data);
    return response.data;
  },

  register: async (data: RegisterRequest): Promise<AuthResponse> => {
    const response = await apiClient.post<AuthResponse>('/auth/register', data);
    return response.data;
  },

  logout: async (): Promise<void> => {
    await apiClient.post('/auth/logout');
  },

  refreshToken: async (refreshToken: string): Promise<AuthResponse> => {
    const response = await apiClient.post<AuthResponse>('/auth/refresh', {
      refreshToken,
    });
    return response.data;
  },
};

export const clothesService = {
  getAll: async (params?: {
    page?: number;
    size?: number;
    sex?: string;
    type?: string;
    brandId?: number;
  }): Promise<Clothes[]> => {
    const response = await apiClient.get<Clothes[]>('/clothes', { params });
    return response.data;
  },

  getById: async (id: number): Promise<Clothes> => {
    const response = await apiClient.get<Clothes>(`/clothes/${id}`);
    return response.data;
  },

  create: async (data: {
    title: string;
    sex: string;
    type: string;
    price: number;
    quantity: number;
    description?: string;
  }): Promise<Clothes> => {
    const response = await apiClient.post<Clothes>('/clothes', data);
    return response.data;
  },
};
