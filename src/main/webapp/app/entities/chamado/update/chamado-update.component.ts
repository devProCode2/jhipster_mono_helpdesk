import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ChamadoFormService, ChamadoFormGroup } from './chamado-form.service';
import { IChamado } from '../chamado.model';
import { ChamadoService } from '../service/chamado.service';
import { Status } from 'app/entities/enumerations/status.model';
import { Prioridade } from 'app/entities/enumerations/prioridade.model';

@Component({
  selector: 'jhi-chamado-update',
  templateUrl: './chamado-update.component.html',
})
export class ChamadoUpdateComponent implements OnInit {
  isSaving = false;
  chamado: IChamado | null = null;
  statusValues = Object.keys(Status);
  prioridadeValues = Object.keys(Prioridade);

  editForm: ChamadoFormGroup = this.chamadoFormService.createChamadoFormGroup();

  constructor(
    protected chamadoService: ChamadoService,
    protected chamadoFormService: ChamadoFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ chamado }) => {
      this.chamado = chamado;
      if (chamado) {
        this.updateForm(chamado);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const chamado = this.chamadoFormService.getChamado(this.editForm);
    if (chamado.id !== null) {
      this.subscribeToSaveResponse(this.chamadoService.update(chamado));
    } else {
      this.subscribeToSaveResponse(this.chamadoService.create(chamado));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IChamado>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(chamado: IChamado): void {
    this.chamado = chamado;
    this.chamadoFormService.resetForm(this.editForm, chamado);
  }
}
