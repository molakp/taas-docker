import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { saveAs } from 'file-saver'; // se non lo trova fare npm i --save-dev @types/file-saver

@Injectable()
export class ImageService {
  constructor(private http: HttpClient) {}

  downloadImage(url: string, name: string) {
    this.http.get(url, { responseType: 'blob' }).subscribe(response => {
      saveAs(response, name);
    });
  }
}
