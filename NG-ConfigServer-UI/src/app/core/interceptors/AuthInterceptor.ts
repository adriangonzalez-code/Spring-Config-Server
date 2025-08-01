import { HttpInterceptorFn } from '@angular/common/http';
import { HttpRequest, HttpHandlerFn, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';

export const authInterceptorFn: HttpInterceptorFn = (
  req: HttpRequest<unknown>,
  next: HttpHandlerFn
): Observable<HttpEvent<unknown>> => {
  const token = localStorage.getItem('auth_token');

  // Solo agrega el header si hay un token
  if (token) {
    const clonedRequest = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });

    // Para debug: imprime los headers antes de enviarlo
    console.log('🔐 Request con header Authorization:', clonedRequest.headers.get('Authorization'));

    return next(clonedRequest);
  }

  return next(req);
};
