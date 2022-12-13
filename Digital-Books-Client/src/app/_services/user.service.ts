import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs'; 

const API_URL = 'http://localhost:8090/digitalbooks/';
const TOKEN_HEADER_KEY = 'Authorization'; 


@Injectable({
  providedIn: 'root'
})
export class UserService {
 
  pathvariable="";
  isfirstPathVariable=true;

  constructor(private http: HttpClient) { }

  

  getPublicContent(): Observable<any> {
    return this.http.get(API_URL + 'all', { responseType: 'text' });
  }

  getUserBoard(): Observable<any> {
    return this.http.get(API_URL + 'user', { responseType: 'text' });
  }

  getModeratorBoard(): Observable<any> {
    return this.http.get(API_URL + 'mod', { responseType: 'text' });
  }

  getAdminBoard(): Observable<any> {
    return this.http.get(API_URL + 'admin', { responseType: 'text' });
  }

  getBooks(search :any): Observable<any> {
    console.log(search);
    this.pathvariable="";
    this.isfirstPathVariable=true;
    if(search.category !="")
    {
      if(this.isfirstPathVariable)
      this.pathvariable ='?';
      else
      this.pathvariable +='&';
    this.pathvariable +='category='+search.category;
    this.isfirstPathVariable=false;
   }
   if(search.title !="")
   {
     if(this.isfirstPathVariable)
     this.pathvariable ='?';
     else
     this.pathvariable +='&';
   this.pathvariable +='title='+search.title;
   this.isfirstPathVariable=false;
  }
  if(search.author !="")
  {
    if(this.isfirstPathVariable)
    this.pathvariable ='?';
    else
    this.pathvariable +='&';
  this.pathvariable +='author='+search.author;
  this.isfirstPathVariable=false;
 }
 if(search.price !="")
 {
   if(this.isfirstPathVariable)
   this.pathvariable ='?';
   else
   this.pathvariable +='&';
 this.pathvariable +='price='+search.price;
 this.isfirstPathVariable=false;
}
if(search.publisher !="")
{
  if(this.isfirstPathVariable)
  this.pathvariable ='?';
  else
  this.pathvariable +='&';
this.pathvariable +='publisher='+search.publisher;
this.isfirstPathVariable=false;
}
 console.log("Path :"+ API_URL+'search'+this.pathvariable )
    return this.http.get(API_URL + 'search'+this.pathvariable, { responseType: 'text' });
  }


  subcribeBook(reader: string | undefined, bookId: number) {
    const httpOptions = {
      headers: new HttpHeaders({ 'Content-Type': 'application/json'})
    };
    let url=bookId+'/subscribe';
    return this.http.post(API_URL+ url, {
      bookId,
      reader
    }, httpOptions);
  }
}
