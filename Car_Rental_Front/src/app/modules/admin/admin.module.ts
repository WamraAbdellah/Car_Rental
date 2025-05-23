import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminRoutingModule } from './admin-routing.module';
import {ReactiveFormsModule,FormsModule} from "@angular/forms";
import { PostCarComponent } from './components/post-car/post-car.component';
import {NgZorroImportsModule} from "../../NgZorroImportsModule";
import {NzDatePickerComponent} from "ng-zorro-antd/date-picker";
import { UpdateCarComponent } from './components/update-car/update-car.component';
import { GetBookingsComponent } from './components/get-bookings/get-bookings.component';
import { SearchCarComponent } from './components/search-car/search-car.component';


@NgModule({
  declarations: [
    PostCarComponent,
    UpdateCarComponent,
    GetBookingsComponent,
    SearchCarComponent
  ],
  imports: [
    CommonModule,
    AdminRoutingModule,
    NgZorroImportsModule,
    NzDatePickerComponent,
    ReactiveFormsModule,
    FormsModule
  ]
})
export class AdminModule { }
