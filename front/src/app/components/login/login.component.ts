import {Component, OnInit} from '@angular/core';
import UserService from '../../services/user.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username: string;
  password: string;

  constructor(private service: UserService, private router: Router) {
    this.username = '';
    this.password = '';
  }

  ngOnInit(): void {
  }

  login() {
    this.service.authenticate('user', 'password').subscribe(
      () => this.router.navigate(['lists']
      )
    );
  }

  onUsernameChange(event: Event) {
    const target = event.target as HTMLInputElement;
    this.username = target.value;
  }

  onPasswordChange(event: Event) {
    const target = event.target as HTMLInputElement;
    this.password = target.value;
  }
}
