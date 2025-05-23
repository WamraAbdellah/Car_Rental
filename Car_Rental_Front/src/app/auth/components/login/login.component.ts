import { Component, TemplateRef } from '@angular/core';
import { NzSpinModule } from 'ng-zorro-antd/spin';
import { NzFormModule } from 'ng-zorro-antd/form';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzLayoutModule } from 'ng-zorro-antd/layout' ;
import { CommonModule } from '@angular/common';
import { AbstractControl, FormBuilder, FormControl, FormGroup, NgModel, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth/auth.service';
import { NzMessageService } from 'ng-zorro-antd/message';
import { StorageService } from '../../services/storage/storage.service';


@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule,RouterLink ,ReactiveFormsModule,NzSpinModule,
    NzFormModule,
    NzButtonModule,
    NzInputModule,
    NzLayoutModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  isSpinning:boolean =false;

  loginForm!: FormGroup;
  constructor(private fb : FormBuilder,private authService : AuthService,private router :Router,private message: NzMessageService){}

  ngOnInit(){
    this.loginForm = this.fb.group({
      email: [null, [Validators.required, Validators.email]],
      password: [null, Validators.required]
    });
  }

  login(){
    this.authService.login(this.loginForm.value).subscribe((res) => {
      console.log(res);
      if(res.userId!=null){
        const user = {
          id : res.userId,
          role: res.userRole
        }
        StorageService.saveUser(user);
        StorageService.saveToken(res.jwt);
        if(StorageService.isAdminLoggedIn()){
        this.router.navigateByUrl("/admin/dashboard")}
        else if(StorageService.isCustomerLoggedIn()){
        this.router.navigateByUrl("/customer/dashboard")}
        else{
        this.message.error("Bad credentials",{nzDuration: 50000})}
      }
    })

  }


}
