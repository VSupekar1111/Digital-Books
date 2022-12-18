import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

const AUTH_API = 'https://mon4fn2fmc.execute-api.ap-northeast-1.amazonaws.com/dev/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' ,'Access-Control-Allow-Origin':'*'})
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private http: HttpClient) { }

  login(username: string, password: string): Observable<any> {
    return this.http.post(AUTH_API + 'signin', {
      username,
      password
    }, httpOptions);
  }

  register(username: string, email: string, password: string,phoneNumber:string,role:string): Observable<any> {
    console.log("role"+role); 
    console.log("HttpHeaders:"+httpOptions);
    return this.http.post(AUTH_API + 'signup', {
      username,
      email,
      password,
      phoneNumber,
      role
    }, httpOptions);
  }
}
