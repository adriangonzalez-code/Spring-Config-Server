import { Component } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { AuthService } from "../../core/auth/AuthService";

@Component({
  selector: 'app-layout',
  templateUrl: './layout.component.html',
  imports: [
    RouterOutlet
  ],
  standalone: true
})
export class LayoutComponent {
  username = 'User';

  constructor(private auth: AuthService, private router: Router) {
    //this.username = this.auth.getUsernameFromToken(); // si implementaste eso
  }

  logout() {
    this.auth.logout();
    this.router.navigate(['/login']);
  }
}
