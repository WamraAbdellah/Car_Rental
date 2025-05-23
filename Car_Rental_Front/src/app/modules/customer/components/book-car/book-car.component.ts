import { Component } from '@angular/core';
import { CustomerService } from '../../services/customer.service';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { StorageService } from '../../../../auth/services/storage/storage.service';
import { NzMessageService } from 'ng-zorro-antd/message';

@Component({
  selector: 'app-book-car',
  templateUrl: './book-car.component.html',
  styleUrl: './book-car.component.scss'
})
export class BookCarComponent {

  carId! : number;
  car : any;
  processedImage : any;
  validateForm!: FormGroup;
  isSpinning = false;
  dateFormat = 'dd/MM/yyyy';

  constructor(private service : CustomerService,
    private activatedRoute: ActivatedRoute,
    private fb : FormBuilder,
    private message : NzMessageService,
    private router : Router){}

  ngOnInit(){
    this.validateForm = this.fb.group({
      toDate: [null, Validators.required],
      fromDate: [null, Validators.required],
    })
    this.carId  = this.activatedRoute.snapshot.params["id"];
    this.getCarById();
  }

  getCarById(){
    this.service.getCarById(this.carId).subscribe((res : any) => {
      console.log(res)
      this.processedImage = 'data:image/jpeg;base64,'+ res.returnedImage;
      this.car = res;
    })
  }

  bookCar(data : any){
    console.log(data);
    this.isSpinning=true;

    // Convertir les dates en format ISO (standard)
    const fromDateISO = data.fromDate ? new Date(data.fromDate).toISOString() : null;
    const toDateISO = data.toDate ? new Date(data.toDate).toISOString() : null;

    let bookACarDto = {
      toDate : toDateISO,
      fromDate : fromDateISO,
      userId : StorageService.getUserId(),
      carId : this.carId
    }
    console.log(bookACarDto);

    this.service.bookACar(bookACarDto).subscribe((res) => {
      console.log(res);
      this.message.success("Booking request submitted successfully", {nzDuration: 5000});
      this.router.navigateByUrl("/customer/dashboard");
    }, error => {

      this.message.error("Something went wrong", {nzDuration: 5000})
    });
  }
}
