import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {FixMessage} from '../model/fix-message.model';
import {map} from 'rxjs/operators';


const httpOptions = {
  headers: new HttpHeaders(
    {
      'Content-Type': 'application/json',
    }
  )
};

export const PARSER_SERVICE_URL = 'http://localhost:9065/messages';

@Injectable()
export class ParserService {

  constructor(private http: HttpClient) {
  }

  parse(input: any): Observable<FixMessage[]> {
    return this.http.post(PARSER_SERVICE_URL, input, httpOptions)
      .pipe(map(response => response as FixMessage[]));
  }

}
