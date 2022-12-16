import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from '../_services/token-storage.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-createbook',
  templateUrl: './createbook.component.html',
  styleUrls: ['./createbook.component.css']
})
export class CreatebookComponent implements OnInit {
  isCreateBookFailed=false;
  isLoggedIn=false;
  errorMessage="";
  message="";
  userId=0;
 

 book={
  category:"",
  title:"",
  author:"",
  price:"",
  publisher:"",
  content:""
 }
  constructor(private userService: UserService, private tokenStorageService: TokenStorageService) { }

  ngOnInit(): void {
    this.isLoggedIn = !!this.tokenStorageService.getToken();

    if (this.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      this.userId = user.id;
      this.book.author=user.username;
    }
  }

  createBook():void{
    console.log("Creating Book");
    this.userService.createBook(this.userId,this.book.title,this.book.category,this.book.author,this.book.price,this.book.publisher,this.book.content).subscribe(
     data => {
       this.isCreateBookFailed=false;
       this.message="Book Created Id:"+data;
       console.log(data);
      },
     err => {
       this.isCreateBookFailed=true; 
       this.errorMessage = err.error.errorMessage;
       console.log(this.errorMessage);
     }
   );
     }

}
