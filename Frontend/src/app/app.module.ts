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

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    DashboardClientComponent,
    LoginComponent,
    NavComponent,
    RegisterComponent,
    DashboardCookComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [
    provideClientHydration()
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
