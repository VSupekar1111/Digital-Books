import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from '../_services/token-storage.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-subscribedbooks',
  templateUrl: './subscribedbooks.component.html',
  styleUrls: ['./subscribedbooks.component.css']
})
export class SubscribedbooksComponent implements OnInit {

  isUnSubscribedFailed=false;
  isSearchSubscribedFailed=false; 
  private roles: string[] = [];
  showReaderBoard = false;
  unsubscribedid=0;
  userid?: string;
  errorMessage="";
  subscribedBookList?:SubscribedBook[];
  content?: string;
  constructor(private userService: UserService,private tokenStorageService: TokenStorageService) { }
  

  ngOnInit(): void {
    const user = this.tokenStorageService.getUser();
    this.userid = user.id;
    this.userService.getSubscribedBooks(this.userid).subscribe(
      data => {
        console.log(data);
        this.subscribedBookList=JSON.parse(data).bookSubscribeResponseList;
        if(this.subscribedBookList?.length == 0){
          this.isSearchSubscribedFailed=true;
        this.errorMessage = 'Subscribed books not Found';
        }
        console.log(this.subscribedBookList);
      },
      err => {
        this.isSearchSubscribedFailed=true;
        this.errorMessage = JSON.parse(err.error).errorMessage;
      }
    );
  }

  unsubscribe(subscribeId: number):void{
console.log("UnSubscribed Id:"+subscribeId);
this.unsubscribedid=subscribeId;
 this.userService.UnsubcribeBook(this.userid,subscribeId).subscribe(
  data => {
    this.isUnSubscribedFailed=false;
    console.log(data);
    window.location.reload();
   },
  err => {
    this.isUnSubscribedFailed=true; 
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
  active:string;
}

export interface SubscribedBook{
  subscribeId:number;
  book:Book;
  title:string;
  userId:number;
  subscribeDate:string;
  publisher:string;
  logo:string;
  authorName:string;
}