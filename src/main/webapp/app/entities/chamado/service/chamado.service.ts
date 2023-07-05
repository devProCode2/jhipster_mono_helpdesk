import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IChamado, NewChamado } from '../chamado.model';

export type PartialUpdateChamado = Partial<IChamado> & Pick<IChamado, 'id'>;

type RestOf<T extends IChamado | NewChamado> = Omit<T, 'dataAbertura' | 'dataFechamento'> & {
  dataAbertura?: string | null;
  dataFechamento?: string | null;
};

export type RestChamado = RestOf<IChamado>;

export type NewRestChamado = RestOf<NewChamado>;

export type PartialUpdateRestChamado = RestOf<PartialUpdateChamado>;

export type EntityResponseType = HttpResponse<IChamado>;
export type EntityArrayResponseType = HttpResponse<IChamado[]>;

@Injectable({ providedIn: 'root' })
export class ChamadoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/chamados');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(chamado: NewChamado): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(chamado);
    return this.http
      .post<RestChamado>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(chamado: IChamado): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(chamado);
    return this.http
      .put<RestChamado>(`${this.resourceUrl}/${this.getChamadoIdentifier(chamado)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(chamado: PartialUpdateChamado): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(chamado);
    return this.http
      .patch<RestChamado>(`${this.resourceUrl}/${this.getChamadoIdentifier(chamado)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestChamado>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestChamado[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getChamadoIdentifier(chamado: Pick<IChamado, 'id'>): number {
    return chamado.id;
  }

  compareChamado(o1: Pick<IChamado, 'id'> | null, o2: Pick<IChamado, 'id'> | null): boolean {
    return o1 && o2 ? this.getChamadoIdentifier(o1) === this.getChamadoIdentifier(o2) : o1 === o2;
  }

  addChamadoToCollectionIfMissing<Type extends Pick<IChamado, 'id'>>(
    chamadoCollection: Type[],
    ...chamadosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const chamados: Type[] = chamadosToCheck.filter(isPresent);
    if (chamados.length > 0) {
      const chamadoCollectionIdentifiers = chamadoCollection.map(chamadoItem => this.getChamadoIdentifier(chamadoItem)!);
      const chamadosToAdd = chamados.filter(chamadoItem => {
        const chamadoIdentifier = this.getChamadoIdentifier(chamadoItem);
        if (chamadoCollectionIdentifiers.includes(chamadoIdentifier)) {
          return false;
        }
        chamadoCollectionIdentifiers.push(chamadoIdentifier);
        return true;
      });
      return [...chamadosToAdd, ...chamadoCollection];
    }
    return chamadoCollection;
  }

  protected convertDateFromClient<T extends IChamado | NewChamado | PartialUpdateChamado>(chamado: T): RestOf<T> {
    return {
      ...chamado,
      dataAbertura: chamado.dataAbertura?.toJSON() ?? null,
      dataFechamento: chamado.dataFechamento?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restChamado: RestChamado): IChamado {
    return {
      ...restChamado,
      dataAbertura: restChamado.dataAbertura ? dayjs(restChamado.dataAbertura) : undefined,
      dataFechamento: restChamado.dataFechamento ? dayjs(restChamado.dataFechamento) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestChamado>): HttpResponse<IChamado> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestChamado[]>): HttpResponse<IChamado[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
