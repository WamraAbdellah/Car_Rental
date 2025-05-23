import { Component } from '@angular/core';
import {AdminService} from "../../services/admin.service";
import {ActivatedRoute, Router} from "@angular/router";
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NzMessageService } from 'ng-zorro-antd/message';

@Component({
  selector: 'app-update-car',
  templateUrl: './update-car.component.html',
  styleUrl: './update-car.component.scss'
})
export class UpdateCarComponent {


  constructor(private adminService: AdminService,
              private activatedRoute : ActivatedRoute,
              private fb : FormBuilder,
              private message : NzMessageService,
              private router : Router) {}


  isSpinning =false;
  carId!: number ;
  imgChanged : boolean =false;
  selectedFile: any;
  imagePreview : string | ArrayBuffer | null = null;
  existingImage: string | null= null;
  updateForm! : FormGroup;
  listOfOption: Array<{ label: string; value: string }> = [];
  listOfBrands = ["BMW", "AUDI", "FERRARI", "TESLA", "VOLVO", "TOYOTA","HONDA","FORD","NISSAN","HYUNDAI","LEXUS","KIA"];
  listOfType = ["Petrol", "Hybrid", "Diesel", "Electric", "CNG"];
  listOfColor= ["Red", "White", "Blue", "Black", "Orange", "Grey", "Silver"];
  listOfTransmission = ["Manual", "Automatic"];

  ngOnInit(){
    this.carId= this.activatedRoute.snapshot.params["id"];
    this.updateForm=this.fb.group({
      name: [null, Validators.required],
      brand: [null, Validators.required],
      type: [null, Validators.required],
      color: [null, Validators.required],
      transmission: [null, Validators.required],
      price: [null, Validators.required],
      description: [null, Validators.required],
      year: [null, Validators.required]
    });
    this.getCarById();
  }

  updateCar(){
    this.isSpinning=true;
    const formData: FormData = new FormData();
    if(this.imgChanged && this.selectedFile){
      formData.append('image', this.selectedFile);
    }
    
    formData.append('brand', this.updateForm.get('brand')?.value);
    formData.append('name', this.updateForm.get('name')?.value);
    formData.append('type', this.updateForm.get('type')?.value);
    formData.append('color', this.updateForm.get('color')?.value);
    const yearString = this.updateForm.get('year')?.value; 
    const formattedYear = new Date(yearString).toISOString().split('T')[0]; 
    formData.append('year', formattedYear);
    formData.append('transmission', this.updateForm.get('transmission')?.value);
    formData.append('description', this.updateForm.get('description')?.value);
    formData.append('price', this.updateForm.get('price')?.value);

    console.log(formData);
    this.adminService.updateCar(this.carId, formData).subscribe((res)=>{
      this.isSpinning=false;
      this.message.success("car updated successfully",{nzDuration : 5000});
      this.router.navigateByUrl("/dashboard");
      console.log(res);
    },error => {
      console.error('Error while updating car:', error);
      this.message.error("Error while updating car",{nzDuration : 5000})
      }
    )
  }

  getCarById(){
    this.isSpinning = true;
    this.adminService.getCarById(this.carId).subscribe((res)=>{
      //console.log(res);
      this.isSpinning = false;
      const carDto = res;
      this.existingImage = 'data:image/jpg;base64,' + res.returnedImage;
      console.log(carDto);
      console.log(this.existingImage);
      this.updateForm.patchValue(carDto);
    })
  }

  onFileSelected(event : any){
    this.selectedFile= event.target.files[0];
    this.imgChanged = true;
    this.existingImage = null;
    this.previewImage();

  }
  previewImage(){
    const reader = new FileReader();
    reader.onload = () => {
      this.imagePreview= reader.result;
    }
    reader.readAsDataURL(this.selectedFile);
  }
}
