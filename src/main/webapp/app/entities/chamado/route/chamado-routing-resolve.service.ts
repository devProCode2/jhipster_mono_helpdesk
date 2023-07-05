import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IChamado } from '../chamado.model';
import { ChamadoService } from '../service/chamado.service';

@Injectable({ providedIn: 'root' })
export class ChamadoRoutingResolveService implements Resolve<IChamado | null> {
  constructor(protected service: ChamadoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IChamado | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((chamado: HttpResponse<IChamado>) => {
          if (chamado.body) {
            return of(chamado.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
