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

  getSubscribedBooks(userid: string | undefined) {
   return this.http.get(API_URL + 'readers/'+userid+'/books', { responseType: 'text' });
  }
  //getAuthorBooks(userid: string | undefined) {
    //return this.http.get(API_URL + 'author/'+userid+'/books', { responseType: 'text' });
 // }

  UnsubcribeBook(userid: string | undefined, subscribeId: number) {
    const httpOptions = {
      headers: new HttpHeaders({ 'Content-Type': 'application/json'})
    };
    let url='readers/'+userid+'/books/'+subscribeId+'/cancel-subscription';
    console.log(API_URL+url);
    return this.http.post(API_URL+ url, {
    }, httpOptions);
  }
 
  createBook(userid:number,title: string, category: string, author: string, price: string, publisher: string, content: string) {
    const httpOptions = {
      headers: new HttpHeaders({ 'Content-Type': 'application/json'})
    };
    let url='author/'+userid+'/books';
    console.log(API_URL+url);
    return this.http.post(API_URL+ url, {
      title,
      category,
      author,
      price,
      publisher,
      content
    }, httpOptions);
  }

  getAuthorBooks(userid: string | undefined) {
    console.log("API:"+API_URL + 'author/'+userid+'/books');
    return this.http.get(API_URL + 'author/'+userid+'/books', { responseType: 'text' });
  }

}
