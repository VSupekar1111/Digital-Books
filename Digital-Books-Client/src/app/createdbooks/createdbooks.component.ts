import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from '../_services/token-storage.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-createdbooks',
  templateUrl: './createdbooks.component.html',
  styleUrls: ['./createdbooks.component.css']
})
export class CreatedbooksComponent implements OnInit {

  isFailed=false;
  isSearchSubscribedFailed=false; 
  private roles: string[] = [];
  showReaderBoard = false;
  id=0;
  userid?: string;
  errorMessage="";
  bookList?:Book[];
  content?: string;
  constructor(private userService: UserService,private tokenStorageService: TokenStorageService) { }
  
  ngOnInit(): void {
    const user = this.tokenStorageService.getUser();
    this.userid = user.id;
    console.log("Fetching created book list");
    this.userService.getAuthorBooks(this.userid).subscribe(
      data => {
        console.log(data);
        this.bookList=JSON.parse(data).bookList;
        if(this.bookList?.length == 0){
          this.isSearchSubscribedFailed=true;
        this.errorMessage = 'Books not Found';
        }
        console.log(this.bookList);
      },
      err => {
        this.isSearchSubscribedFailed=true;
        this.errorMessage = JSON.parse(err.error).errorMessage;
      }
    );
  }

  blocUnblock(){

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
  active:string;
}
