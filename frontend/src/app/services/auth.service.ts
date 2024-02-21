import { Injectable } from '@angular/core';
import { Token } from '../interfaces/token';
import { HttpClient } from '@angular/common/http';
import { User } from '../interfaces/user';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private authToken: Token = { jwtToken: null };
  private userDetail: User | null = null;
  private baseUrl = 'http://localhost:8080/';
  constructor(private http: HttpClient) {}
}
