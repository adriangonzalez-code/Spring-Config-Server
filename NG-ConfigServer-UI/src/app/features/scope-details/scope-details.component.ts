
import { ActivatedRoute } from '@angular/router';
import { Component, TrackByFunction } from "@angular/core";
import { Scope } from "../scopes/models/scope.model";
import { ScopesService } from "../../core/services/ScopeService";
import {NgClass, NgForOf, NgIf} from "@angular/common";
import { PropertyService } from "../../core/services/PropertyService";
import {FormsModule} from "@angular/forms";
import {Property} from "./models/property.model";
import {AccessKey} from "./models/access-key.model";
import swal from 'sweetalert2';
import {UserService} from "../../core/services/UserService";
import {User} from "./models/user-model";

@Component({
  selector: 'app-scope-details',
  standalone: true,
  imports: [
    NgForOf,
    FormsModule,
    NgClass,
    NgIf
  ],
  templateUrl: './scope-details.component.html'
})
export class ScopeDetailsComponent {

  activeTab: 'plain' | 'secret' | 'users' | 'information' = 'plain';
  editMode: boolean = false;
  scopeId: number = 0;
  properties: Property[] = [];
  accessKey: string = '';
  users: User[] = [];
  scopeDetails: Scope = {
    id: 0,
    scopeName: '',
    description: '',
    accessKey: '',
    createdBy: '',
    createdAt: '',
    users: []
  };

  constructor(private route: ActivatedRoute, private scopesService: ScopesService, private propertyService: PropertyService, private userService: UserService) {}

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.scopeId = +id;
        this.loadScopeDetails(this.scopeId);
        this.getUsers();
      }
    });
  }

  loadScopeDetails(id: number) {
    this.scopesService.getScopeById(id).subscribe({
      next: (scope: Scope) => {
        this.scopeDetails = scope;

        this.loadProperties(id);
        this.getAccessKey(id);
      },
      error: (err) => {
        console.error('Error loading scope details:', err);
      }
    });
  }

  loadProperties(scopeId: number) {
    this.propertyService.getProperties(scopeId).subscribe({
      next: (properties) => {
        this.properties = properties;
      },
      error: (err) => {
        console.error('Error loading properties:', err);
      }
    });
  }

  getFilteredProperties(tab: 'plain' | 'secret') {
    // Verificación de null/undefined para evitar errores
    if (!this.properties || !Array.isArray(this.properties)) {
      return [];
    }
    return this.properties.filter(p => p.secret === (tab === 'secret'));
  }

  addProperty(tab: 'plain' | 'secret') {
    // Asegurar que properties existe antes de hacer push
    if (!this.properties || !Array.isArray(this.properties)) {
      this.properties = [];
    }

    // Generar un ID único temporal para el nuevo elemento
    const tempId = Math.max(0, ...this.properties.map(p => p.id)) + 1;

    this.properties.push({
      id: tempId,
      key: '',
      value: '',
      secret: tab === 'secret'
    });
  }

  toggleEditMode() {
    this.editMode = !this.editMode;
  }

  removeProperty(propToRemove: Property) {
    this.properties = this.properties.filter(p => p !== propToRemove);
  }

  addUser() {
    this.scopeDetails.users.push('');
  }

  removeUser(index: number) {
    this.scopeDetails.users.splice(index, 1);
  }

  getAccessKey(id: number) {
    this.scopesService.getAccessKeyById(id).subscribe({
      next: (res: AccessKey) => {
        if (res) {
          this.accessKey = res.accessKey;
        } else {
          this.accessKey = '';
        }
      },
      error: (err) => {
        this.accessKey = '';
      }
    });
  }

  saveProperties(scopeId: number) {
    this.propertyService.createProperty(scopeId, this.properties).subscribe({
      next: (properties) => {
        console.log('Properties saved successfully:', properties);
        this.loadProperties(scopeId);
        this.toggleEditMode();

        swal.fire({
          title: 'Success',
          text: 'Properties saved successfully!',
          icon: 'success',
          confirmButtonText: 'OK'
        });
      },
      error: (err) => {
        console.error('Error saving properties:', err);
      }
    });
  }

  trackByPropertyId(index: number, item: Property): any {
    return item.id;
  }

  // Función trackBy para los usuarios - corregida
  trackByUserIndex: TrackByFunction<string> = (index: number, item: string) => {
    return index;
  }

  getUsers() {
    this.userService.getUsers().subscribe({
      next: (users) => {
        this.users = users;
        console.log('Users fetched successfully:', this.users);
      },
      error: (err) => {
        console.error('Error fetching users:', err);
      }
    });
  }

  setUsersToScope(scopeId: number) {
    const emails = this.scopeDetails.users.filter(email => email.trim() !== '');
    if (emails.length === 0) {
      swal.fire({
        title: 'Error',
        text: 'Please add at least one user email.',
        icon: 'error',
        confirmButtonText: 'OK'
      });
      return;
    }

    this.scopesService.setUsersToScope(scopeId, emails).subscribe({
      next: () => {
        swal.fire({
          title: 'Success',
          text: 'Users added to scope successfully!',
          icon: 'success',
          confirmButtonText: 'OK'
        });
        this.loadScopeDetails(scopeId);
      },
      error: (err) => {
        console.error('Error setting users to scope:', err);
        swal.fire({
          title: 'Error',
          text: 'Failed to add users to scope.',
          icon: 'error',
          confirmButtonText: 'OK'
        });
      }
    });
  }
}
