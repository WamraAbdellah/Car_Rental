import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { StorageService } from '../../../auth/services/storage/storage.service';

const BASIC_URL = ["http://localhost:8080"];
@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  constructor(private http: HttpClient) { }

  getAllCars(): Observable<any[]> {
      return this.http.get<any>(BASIC_URL + "/api/customer/cars", {
        headers: this.createAuthorizationHeader()
      });
    }

  getCarById(carId : number): Observable<any> {
    return this.http.get<any>(BASIC_URL + "/api/customer/car/"+ carId, {
      headers: this.createAuthorizationHeader()
    });
  }

  bookACar(bookACarDto: any): Observable<any> {
    return this.http.post<any>(BASIC_URL + "/api/customer/car/book", bookACarDto, {
      headers: this.createAuthorizationHeader()
    });
  }

  getBookingsByUserId(): Observable<any> {
    return this.http.get<any>(BASIC_URL + "/api/customer/car/booking/"+ StorageService.getUserId(), {
      headers: this.createAuthorizationHeader()
    });
  }

  searchCar(searchCarDto: any): Observable<any> {
      return this.http.post(BASIC_URL + "/api/customer/car/search", searchCarDto, {
        headers: this.createAuthorizationHeader()
      });
    }
  
  

    createAuthorizationHeader(): HttpHeaders {
        let authHeaders: HttpHeaders = new HttpHeaders();
        return authHeaders.set(
          'Authorization',
          'Bearer ' + StorageService.getToken()
        );
      }
}
