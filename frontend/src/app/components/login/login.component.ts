import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  loginForm = this.formBuilder.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required]],
  });
  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private authService: AuthService
  ) {}

  get email() {
    return this.loginForm.controls['email'];
  }

  get password() {
    return this.loginForm.controls['password'];
  }

  onSubmit() {
    const { email, password } = this.loginForm.value;
    this.authService
      .login({
        email: email as string,
        password: password as string,
      })
      .subscribe({
        next: (value) => {
          this.authService.user = value;
          localStorage.setItem('user', JSON.stringify(value));
          this.router.navigate(['/home']);
        },
        error: (err) => {
          console.log(err);
        },
      });
  }
}
