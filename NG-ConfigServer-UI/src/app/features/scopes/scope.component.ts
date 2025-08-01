import { Component, OnInit } from '@angular/core';
import Swal from 'sweetalert2';
import { ScopesService } from "../../core/services/ScopeService";
import { Scope } from "./models/scope.model";
import { FormsModule } from "@angular/forms";
import { DatePipe, NgForOf, NgIf } from "@angular/common";
import { trigger, transition, style, animate, keyframes } from '@angular/animations';
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-scopes',
  standalone: true,
  imports: [
    FormsModule,
    NgForOf,
    NgIf,
    DatePipe,
    RouterLink
  ],
  animations: [
    trigger('fadeIn', [
      transition(':enter', [
        style({ opacity: 0 }),
        animate('{{timing}}', style({ opacity: 1 }))
      ], { params: { timing: '0.3s' } }),
      transition(':leave', [
        animate('{{timing}}', style({ opacity: 0 }))
      ], { params: { timing: '0.3s' } })
    ]),
    trigger('slideZoomIn', [
      transition(':enter', [
        animate('{{timing}} ease-out', keyframes([
          style({ opacity: 0, transform: 'translateY({{translate}}) scale(0.95)', offset: 0 }),
          style({ opacity: 1, transform: 'translateY(0) scale(1)', offset: 1 })
        ]))
      ], { params: { timing: '0.4s', translate: '-30px' } }),

      transition(':leave', [
        animate('{{timing}} ease-in', keyframes([
          style({ opacity: 1, transform: 'translateY(0) scale(1)', offset: 0 }),
          style({ opacity: 0, transform: 'translateY({{translate}}) scale(0.95)', offset: 1 })
        ]))
      ], { params: { timing: '0.4s', translate: '-30px' } })
    ])
  ],
  templateUrl: './scope.component.html'
})
export class ScopesComponent implements OnInit {
  scopes: Scope[] = [];
  searchTerm = '';
  showModal = false;

  newScope: Partial<Scope> = {
    scopeName: '',
    description: ''
  };

  constructor(private scopesService: ScopesService) {}

  ngOnInit(): void {
    this.loadScopes();
  }

  loadScopes(): void {
    this.scopesService.getScopes(this.searchTerm).subscribe({
      next: scopes => this.scopes = scopes,
      error: err => console.error('Error loading scopes', err)
    });
  }

  searchScopes(): void {
    this.loadScopes();
  }

  openModal(): void {
    this.newScope = { scopeName: '', description: '' };
    this.showModal = true;
  }

  closeModal(): void {
    this.showModal = false;
  }

  createScope(): void {
    if (!this.newScope.scopeName || !this.newScope.description) return;

    this.scopesService.createScope(this.newScope).subscribe({
      next: () => {
        Swal.fire({
          icon: 'success',
          title: 'Scope created!',
          showConfirmButton: false,
          timer: 1500
        });
        this.closeModal();
        this.loadScopes();
      },
      error: err => {
        Swal.fire({
          icon: 'error',
          title: 'Error creating scope',
          text: err.message
        });
      }
    });
  }
}
