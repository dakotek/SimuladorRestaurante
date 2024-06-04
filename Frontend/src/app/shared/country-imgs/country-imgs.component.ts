import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-country-imgs',
  templateUrl: './country-imgs.component.html',
  styleUrl: './country-imgs.component.css'
})
export class CountryImgsComponent {

  constructor(private router: Router){}

  ngOnInit(){

  }

  search(searchTerm: string): void {
    localStorage.setItem('searchTerm', searchTerm);
    localStorage.setItem('searchType', 'area');
    this.router.navigateByUrl("/busqueda");
  }
}
