import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import { DeleteService } from '../../services/deleteUser';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent {
  username : string | null = null
  email : string | null = null
  token : string | null = null
  pedidos : string | null = null

  constructor(private jwtHelper : JwtHelperService, private router: Router, private deleteUser : DeleteService){}

  ngOnInit() : void{
    this.token = localStorage.getItem('token')
    

    if (this.jwtHelper.isTokenExpired(this.token)) {
      this.router.navigateByUrl('/inicio-sesion');
    }
    this.username = localStorage.getItem("username")
    this.email = localStorage.getItem("email")
    const role = localStorage.getItem('role')
    if (role === "CLIENT") {
      this.pedidos = 'Pedidos realizados:'
    } else {
      this.pedidos = 'Pedidos completados:'
    }
  }

  ngOnChange() : void{

  }

  closeCurrentSession() : void{
      localStorage.clear()
      this.router.navigateByUrl("/inicio-sesion")
  }

  deleteProfile() : void{
    this.deleteUser.delete()
    .then( () => {
      console.log('Si'),
      localStorage.clear(),
      this.router.navigateByUrl('/inicio-sesion');
    })
    .catch( (error) => {
      console.log(error)
    })
  }

  fetchPrueba(){
    
  }

}
