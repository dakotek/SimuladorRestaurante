import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-nav-without-search',
  templateUrl: './nav-without-search.component.html',
  styleUrl: './nav-without-search.component.css'
})
export class NavWithoutSearchComponent {

  constructor(private router : Router){}

  ngOnInit(){

  }

  goToProfile() : void{
    this.router.navigateByUrl("/perfil")
  }

}
