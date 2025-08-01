import { ApplicationConfig, importProvidersFrom } from '@angular/core';
import { provideRouter } from '@angular/router';
import { routes } from './app.routes';
import { provideHttpClient, withInterceptors } from "@angular/common/http";
import { BrowserModule } from "@angular/platform-browser";
import {BrowserAnimationsModule, provideAnimations} from "@angular/platform-browser/animations";
import { authInterceptorFn } from "./core/interceptors/AuthInterceptor";
import {FormsModule} from "@angular/forms";

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideHttpClient(withInterceptors([authInterceptorFn])),
    importProvidersFrom(BrowserModule, BrowserAnimationsModule, FormsModule),
    provideAnimations()
  ]
};
