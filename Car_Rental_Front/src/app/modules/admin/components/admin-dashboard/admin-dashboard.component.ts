import {Component, OnInit} from '@angular/core';
import {AdminService} from "../../services/admin.service";
import {DatePipe, NgForOf} from "@angular/common";
import {NzButtonComponent} from "ng-zorro-antd/button";
import {NzMessageService} from "ng-zorro-antd/message";
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [
    NgForOf,
    DatePipe,
    NzButtonComponent,
    RouterLink
  ],
  templateUrl: './admin-dashboard.component.html',
  styleUrl: './admin-dashboard.component.scss'
})
export class AdminDashboardComponent {
  cars: any =[];
  constructor(private adminService : AdminService, private message : NzMessageService) {
  }
  ngOnInit(){
    this.getAllCars();
  }

  getAllCars(){
    return this.adminService.getAllCars().subscribe((res)=>{

         console.log(res);
         res.forEach( element  =>{
           element.processedImg='data:image/jpeg;base64,'+ element.returnedImage;
           this.cars.push(element);
         });

    })
  }
  deleteCar(id : number){
      console.log(id);
      this.adminService.deleteCar(id).subscribe((res)=>{
        this.getAllCars();
        this.message.success("Car Deleted Successfully", {nzDuration : 5000});
      })
  }
}
