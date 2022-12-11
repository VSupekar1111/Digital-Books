import { Component, OnInit } from '@angular/core';
import { UserService } from '../_services/user.service';
@Component({
  selector: 'app-searchbook',
  templateUrl: './searchbook.component.html',
  styleUrls: ['./searchbook.component.css']
})
export class SearchbookComponent implements OnInit {
  isSuccessful=false;
  isSearchFailed=false;
  errorMessage="";
  content?: string;
  constructor(private userService: UserService) { }
  search={
    category:"",
    title:"",
    author:"",
    price:"",
    publisher:""

  }
  ngOnInit(): void {
  }

  searchBook(): void {
    this.userService.getBooks(this.search).subscribe(
      data => {
        this.isSuccessful = true;
        this.content=data;
      },
      err => {
        this.isSearchFailed=true;
        this.errorMessage = JSON.parse(err.error).message;
      }
    );
  }
}
