export interface LoginRequest {
  usuario: string;
  password: string;
}

export interface AuthResponse {
  token: string;
  usuario: string;
  roles: string[];
}
