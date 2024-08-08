import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css'],
})
export class SignupComponent {
  signUpForm = this.formBuilder.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required]],
    name: ['', [Validators.required]],
  });

  isLoading = false;

  private isBadCredentials = false;
  private userExists = false;
  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private authService: AuthService
  ) {}

  get email() {
    return this.signUpForm.controls['email'];
  }

  get password() {
    return this.signUpForm.controls['password'];
  }

  get name() {
    return this.signUpForm.controls['name'];
  }

  get badCredentials() {
    return this.isBadCredentials;
  }

  get userExist() {
    return this.userExists;
  }

  onSubmit() {
    const { email, password, name } = this.signUpForm.value;
    this.isLoading = true;
    this.authService
      .signUp({
        email: email as string,
        password: password as string,
        name: name as string
      })
      .subscribe({
        next: (value) => {
          this.isBadCredentials = false;
          this.userExists = false;
          this.authService.user = value;
          localStorage.setItem('user', JSON.stringify(value));
          this.isLoading = false;
          this.router.navigate(['/home']);
        },
        error: (err) => {
          setTimeout(() => this.isLoading = false, 4000);
          if (err instanceof HttpErrorResponse) {
            if (err.error.status === 'UNAUTHORIZED') {
              this.isBadCredentials = true;
            }
            if (err.error.status === 'CONFLICT') {
              this.userExists = true;
            }
            // TODO : Add support of internal server error and a custom error page
          }
        },
      });
  }
}
