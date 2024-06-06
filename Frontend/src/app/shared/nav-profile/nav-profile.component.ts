import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-nav-profile',
  templateUrl: './nav-profile.component.html',
  styleUrl: './nav-profile.component.css'
})
export class NavProfileComponent {

  constructor(private router : Router){}

  ngOnInit(){

  }

  goBack() : void{
    if (localStorage.getItem('role') === 'CLIENT') {
      this.router.navigateByUrl('/cliente')
    }else{
      this.router.navigateByUrl('/cocinero')
    }
    
  }

}
