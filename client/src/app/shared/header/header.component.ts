import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { DOCUMENT } from '@angular/common';
import { UserService } from 'src/app/services/user.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  isUser: boolean;
  isAdmin: boolean;
  adminRole: string = "ROLE_ADMIN";
  userRole: string = "ROLE_CUSTOMER";
  user: any[];
  gateway_url: string;

  imgSrc = 'assets/images/menu2.png';
  //@Inject(DOCUMENT) private document: Document
  constructor(
    private router: Router,
    private userSvc: UserService
    ) {
    this.user = [];
    this.isAdmin = false;
    this.isUser = false;
    this.gateway_url = `${environment.GATEWAY_BASE_URL}/oauth2/authorize?response_type=code&client_id=writer&redirect_uri=${environment.APP_BASE_URL}&scope=product:write%20product:read`;
    //this.gateway_url = "https://localhost:8443/oauth2/authorize?response_type=code&client_id=writer&redirect_uri=http://localhost:4000&scope=product:write product:read";
  }

  ngOnInit(): void {
    this.userSvc.getCurrentUser().subscribe((user) => {
      this.user = user;
    });
  }

  handleLogout() {
    this.userSvc.logout();
    this.router.navigate(['login']);
  }
  handleLoginButton(event: Event): void {
    console.log(this.gateway_url);
    window.location.href = `${this.gateway_url}`;
    //event.preventDefault();

  }
}
