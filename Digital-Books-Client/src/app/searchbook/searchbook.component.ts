import { Component, OnInit } from '@angular/core';
import { UserService } from '../_services/user.service';
import { TokenStorageService } from '../_services/token-storage.service';

@Component({
  selector: 'app-searchbook',
  templateUrl: './searchbook.component.html',
  styleUrls: ['./searchbook.component.css']
})

export class SearchbookComponent implements OnInit {
  isSuccessful=false;
  isSearchFailed=false;
  isSubscribeFailed=false;
  private roles: string[] = [];
  isLoggedIn = false;
  showAuthorBoard = false;
  showReaderBoard = false;
  subscribedid=1;
  userid?: string;
  errorMessage="";
  bookList?:Book[];
  content?: string;
  constructor(private userService: UserService,private tokenStorageService: TokenStorageService) { }
  search={
    category:"",
    title:"",
    author:"",
    price:"",
    publisher:""

  }
 
  ngOnInit(): void {
    this.isLoggedIn = !!this.tokenStorageService.getToken();

    if (this.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      this.roles = user.roles;

      this.showAuthorBoard = this.roles.includes('ROLE_AUTHOR');
      this.showReaderBoard = this.roles.includes('ROLE_READER');

      this.userid = user.id;
    }

   let data1='{"status":"Success","bookList":[{"id":1,"title":"Java Full Stack","category":"Programming Language","price":" 765","publisher":"Moon publisher","publishDate":"2022-12-10","content":"","logo":"javafull.png","authorId":10,"authorName":null,"active":true,"createDate":null,"updateDate":null},{"id":2,"title":"Java Core","category":"Programming Language","price":" 450","publisher":"Moon publisher","publishDate":"2022-12-10","content":"","logo":"javacore.png","authorId":10,"authorName":null,"active":true,"createDate":null,"updateDate":null},{"id":3,"title":"Java","category":"Programming Language","price":" 450","publisher":"Moon publisher","publishDate":"2022-12-10","content":"","logo":"java.png","authorId":10,"authorName":null,"active":true,"createDate":null,"updateDate":null},{"id":4,"title":"Angular","category":"Programming Language","price":" 450","publisher":"Moon publisher","publishDate":"2022-12-10","content":"","logo":"https://repository-images.githubusercontent.com/24195339/87018c00-694b-11e9-8b5f-c34826306d36","authorId":1,"authorName":null,"active":true,"createDate":null,"updateDate":null},{"id":5,"title":"Node JS","category":"Programming Language","price":"500","publisher":"Moon publisher","publishDate":"2022-12-10","content":"","logo":"nodejs.png","authorId":1,"authorName":null,"active":true,"createDate":null,"updateDate":null}],"message":null}';
    this.isSuccessful = true;
   this.bookList=JSON.parse(data1).bookList; 
  }

  searchBook(): void {
    this.userService.getBooks(this.search).subscribe(
      data => {
        console.log(data);
        this.isSuccessful = true;
        this.bookList=JSON.parse(data).bookList;
        if(this.bookList?.length == 0){
          this.isSuccessful = false;
          this.isSearchFailed=true;
        this.errorMessage = 'Search Result Not fFound';
        }
        console.log(this.bookList);
      },
      err => {
        this.isSearchFailed=true;
        this.errorMessage = JSON.parse(err.error).errorMessage;
      }
    );
  }
 
 subscribe(book_id:number):void{
 console.log("subscribed:"+book_id);
 this.subscribedid=book_id;
 this.userService.subcribeBook(this.userid,book_id).subscribe(
  data => {
    this.isSubscribeFailed=false;
    console.log(data);
   },
  err => {
    this.isSubscribeFailed=true; 
    this.errorMessage = err.error.errorMessage;
    console.log(this.errorMessage);
  }
);
  }

}

export interface Book{
  id:number;
  category:string;
  title:string;
  author:string;
  price:string;
  publisher:string;
  logo:string;
  authorName:string;
}