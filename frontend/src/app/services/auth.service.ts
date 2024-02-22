import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UserInfo } from '../interfaces/user-info';
import { Observable } from 'rxjs';
import { SignupRequest } from '../interfaces/signup-request';
import { LoginRequest } from '../interfaces/login-request';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private authenticatedUser: UserInfo | null = null;
  private baseUrl = 'http://localhost:8080/api/auth';
  constructor(private http: HttpClient) {}

  public login(requestBody: LoginRequest): Observable<UserInfo> {
    return this.http.post<UserInfo>(`${this.baseUrl}/login`, requestBody);
  }

  public signUp(requestBody: SignupRequest): Observable<UserInfo> {
    return this.http.post<UserInfo>(`${this.baseUrl}/sign-up`, requestBody);
  }

  public logout() {
    this.authenticatedUser = null;
    localStorage.removeItem('user');
  }

  get user(): UserInfo | null {
    return this.authenticatedUser;
  }

  set user(userInfo: UserInfo) {
    this.authenticatedUser = userInfo;
  }
}
