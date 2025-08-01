import { Routes } from '@angular/router';
import { LoginComponent } from "./features/login/login.component";
import { LayoutComponent } from "./shared/layout/layout.component";
import { canActivateGuard } from "./core/auth/AuthGuard";
import { ScopeDetailsComponent } from "./features/scope-details/scope-details.component";

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  {
    path: '',
    component: LayoutComponent,
    canActivate: [canActivateGuard],
    children: [
      { path: '', loadComponent: () => import('./features/scopes/scope.component').then(m => m.ScopesComponent) },
      { path: 'scopes/:id', component: ScopeDetailsComponent },
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' }
    ]
  },
  { path: '**', redirectTo: '' }
];
