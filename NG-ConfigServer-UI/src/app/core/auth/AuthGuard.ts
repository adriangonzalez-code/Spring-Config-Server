import { inject } from "@angular/core";
import { AuthService } from "./AuthService";
import { CanActivateFn, Router } from "@angular/router";

export const canActivateGuard: CanActivateFn = () => {
  return inject(AuthService).isAuthenticated() ? true : inject(Router).createUrlTree(['/login']);
};
