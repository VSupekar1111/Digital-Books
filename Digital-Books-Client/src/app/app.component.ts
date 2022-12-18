import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from './_services/auth.service';
import { TokenStorageService } from './_services/token-storage.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  private roles: string[] = [];
  isLoggedIn = false;
  showAuthorBoard = false;
  showReaderBoard = false;
  username?: string;

  login=false;
  form: any = {
    username: null,
    password: null
  };
 
  isLoginFailed = false;
  errorMessage = '';
  constructor(private tokenStorageService: TokenStorageService, private tokenStorage: TokenStorageService,private router: Router,private authService: AuthService) { }

  ngOnInit(): void {
    this.login=false;
  }

  logout(): void {
    this.tokenStorageService.signOut();
    this.isLoggedIn = false;
    this.showAuthorBoard = false;
    this.showReaderBoard = false;
    this.username="";
    this.login=false;
    this.router.navigate(['/home']);
  //  window.location.reload();
  }

  onSubmit(): void {
    const { username, password } = this.form;

    this.authService.login(username, password).subscribe(
      data => {
        this.tokenStorage.saveToken(data.accessToken);
        this.tokenStorage.saveUser(data);

        this.isLoginFailed = false;
        this.isLoggedIn = true;

        const user = this.tokenStorageService.getUser();
        this.roles = user.roles;
        this.showAuthorBoard = this.roles.includes('ROLE_AUTHOR');
        this.showReaderBoard = this.roles.includes('ROLE_READER');
        this.username = user.username;

        this.router.navigate(['/search']);
        this.login=false;

      },
      err => {
        this.errorMessage = err.error.message;
        this.isLoginFailed = true;
      }
    );
  }

  doLogin(): void {
    this.router.navigate(['/home']);
    this.login=true;
  }

}
