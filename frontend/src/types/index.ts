export interface User {
  id: number;
  email: string;
  role: string;
}

export interface AuthResponse {
  accessToken: string;
  refreshToken: string;
  tokenType: string;
  expiresIn: number;
  user: User;
}

export interface Clothes {
  id: number;
  title: string;
  sex: string;
  type: string;
  description: string;
  compound: string;
  clg: string;
  price: number;
  quantity: number;
  brand: BrandInfo | null;
  images: ImageInfo[];
  colors: string[];
  sizes: string[];
  enabled: boolean;
  createdAt: string;
}

interface BrandInfo {
  id: number;
  name: string;
}

interface ImageInfo {
  id: number;
  url: string;
}

export interface ClothesCreateRequest {
  title: string;
  sex: string;
  type: string;
  description?: string;
  compound?: string;
  clg?: string;
  price: number;
  quantity: number;
  brandId?: number;
  colorIds?: number[];
  sizeIds?: number[];
}

export interface CartItem {
  clothes: Clothes;
  quantity: number;
  size?: string;
}
