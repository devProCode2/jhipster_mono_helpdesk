import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IChamado } from '../chamado.model';

@Component({
  selector: 'jhi-chamado-detail',
  templateUrl: './chamado-detail.component.html',
})
export class ChamadoDetailComponent implements OnInit {
  chamado: IChamado | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ chamado }) => {
      this.chamado = chamado;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
