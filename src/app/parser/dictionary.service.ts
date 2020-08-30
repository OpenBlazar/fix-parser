import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {DictionaryDescriptor} from '../model/dictionary-descriptor.model';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';

const httpOptions = {
  headers: new HttpHeaders(
    {
      'Content-Type': 'application/json',
    }
  )
};

export const GET_ALL_DICTIONARIES = 'http://localhost:9065/dictionary/all';

@Injectable()
export class DictionaryService {

  constructor(private http: HttpClient) {
  }

  getDictionaries(): Observable<DictionaryDescriptor[]> {
    return this.http.get(GET_ALL_DICTIONARIES, httpOptions)
      .pipe(map(response => response as DictionaryDescriptor[]));
  }

}
