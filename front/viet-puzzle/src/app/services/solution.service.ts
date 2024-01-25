import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Solution } from '../Solution';

@Injectable({
  providedIn: 'root',
})
export class SolutionService {
  BASE_URL = 'http://localhost:8080/solutions';  // Préférer un proxy

  constructor(protected httpClient: HttpClient) {}

  get baseUrl(): string {
    return this.BASE_URL;
  }

  generateSolutions(): Observable<number> {
    return this.httpClient.get<number>(
      this.baseUrl + '/generate'
    );
  }

  search(): Observable<Solution[]> {
    // TODO : Utiliser un la méthode onLazyLoad du tableau PrimeNg pour
    // appeller la fonction search() en lui passant un Pageable à chage NewPagerEvent (je ne suis plus sur du nom).
    // Utiliser ce pageable dans le back pour requêter via JPA uniquement le bon nombre de solutions.
    // => On souhaite éviter de charger les N solutions trouvées par le back d'un seul block.
    return this.httpClient.get<Solution[]>(this.baseUrl + '/search');
  }

  save(data: Solution): Observable<Solution> {
    return this.httpClient.post<Solution>(this.baseUrl, data);
  }

  read(id: string | null): Observable<Solution> {
    return this.httpClient.get<Solution>(this.baseUrl + '/' + id);
  }

  update(id: number, data: any): Observable<Solution> {
    return this.httpClient.put<Solution>(this.baseUrl + '/' + id, data);
  }

  delete(id: number): Observable<void> {
    return this.httpClient.delete<void>(this.baseUrl + '/' + id);
  }

  deleteAll(): Observable<void> {
    return this.httpClient.delete<void>(this.baseUrl);
  }
}
