import { Component } from '@angular/core';
import { CustomerService } from '../../services/customer.service';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-customer-dashboard',
  standalone: true,
  imports: [CommonModule,
            RouterLink
           ],
  templateUrl: './customer-dashboard.component.html',
  styleUrl: './customer-dashboard.component.scss'
})
export class CustomerDashboardComponent {

  cars : any = [];
  constructor(private service : CustomerService){}
  ngOnInit(){
    this.getAllCars();
  }

  getAllCars(){
    return this.service.getAllCars().subscribe((res)=>{

         console.log(res);
         res.forEach( element  =>{
           element.processedImg='data:image/jpeg;base64,'+ element.returnedImage;
           this.cars.push(element);
         })

    })
  }
}
