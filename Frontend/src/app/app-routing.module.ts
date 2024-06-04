import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { DashboardClientComponent } from './pages/dashboardClient/dashboardClient.component';
import { RegisterComponent } from './auth/register/register.component';
import { DashboardCookComponent } from './pages/dashboardCook/dashboardCook.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { RecipeComponent } from './pages/recipe/recipe.component';
import { SearchedRecipesComponent } from './pages/searched-recipes/searched-recipes.component';

const routes: Routes = [
  {path: '', redirectTo: '/inicio-sesion', pathMatch: 'full'},
  {path: 'cliente', component:DashboardClientComponent},
  {path: 'cocinero', component:DashboardCookComponent},
  {path: 'inicio-sesion', component:LoginComponent},
  {path: 'registro', component:RegisterComponent},
  {path: 'perfil', component:ProfileComponent},
  {path: 'receta', component:RecipeComponent},
  {path: 'busqueda', component:SearchedRecipesComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
