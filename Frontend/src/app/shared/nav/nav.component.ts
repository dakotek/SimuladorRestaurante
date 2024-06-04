import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrl: './nav.component.css'
})
export class NavComponent {

  constructor(private router: Router){}

  ngOnInit(){

  }

  onSubmit(event: Event) {
    event.preventDefault();
  }

  goToProfile() : void{
    this.router.navigateByUrl("/perfil")
  }

  search(searchTerm: string): void {
    localStorage.setItem('searchTerm', searchTerm);
    this.router.navigateByUrl("/busqueda");
  }
}
