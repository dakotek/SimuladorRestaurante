import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './shared/header/header.component';
import { DashboardClientComponent } from './pages/dashboardClient/dashboardClient.component';
import { LoginComponent } from './auth/login/login.component';
import { NavComponent } from './shared/nav/nav.component';
import { RegisterComponent } from './auth/register/register.component';
import { DashboardCookComponent } from './pages/dashboardCook/dashboardCook.component';
import { ReactiveFormsModule } from '@angular/forms';
import { NavWithoutSearchComponent } from './shared/nav-without-search/nav-without-search.component';
import { JwtModule, JwtHelperService, JWT_OPTIONS } from '@auth0/angular-jwt';
import { CountryImgsComponent } from './shared/country-imgs/country-imgs.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { NavProfileComponent } from './shared/nav-profile/nav-profile.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    DashboardClientComponent,
    LoginComponent,
    NavComponent,
    RegisterComponent,
    DashboardCookComponent,
    NavWithoutSearchComponent,
    CountryImgsComponent,
    ProfileComponent,
    NavProfileComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    JwtModule.forRoot({})
  ],
  providers: [
    provideClientHydration(),
    JwtHelperService,
    { provide: JWT_OPTIONS, useValue: JWT_OPTIONS }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
