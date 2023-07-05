import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IChamado, NewChamado } from '../chamado.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IChamado for edit and NewChamadoFormGroupInput for create.
 */
type ChamadoFormGroupInput = IChamado | PartialWithRequiredKeyOf<NewChamado>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IChamado | NewChamado> = Omit<T, 'dataAbertura' | 'dataFechamento'> & {
  dataAbertura?: string | null;
  dataFechamento?: string | null;
};

type ChamadoFormRawValue = FormValueOf<IChamado>;

type NewChamadoFormRawValue = FormValueOf<NewChamado>;

type ChamadoFormDefaults = Pick<NewChamado, 'id' | 'dataAbertura' | 'dataFechamento'>;

type ChamadoFormGroupContent = {
  id: FormControl<ChamadoFormRawValue['id'] | NewChamado['id']>;
  titulo: FormControl<ChamadoFormRawValue['titulo']>;
  status: FormControl<ChamadoFormRawValue['status']>;
  prioridade: FormControl<ChamadoFormRawValue['prioridade']>;
  dataAbertura: FormControl<ChamadoFormRawValue['dataAbertura']>;
  dataFechamento: FormControl<ChamadoFormRawValue['dataFechamento']>;
  valorOrcamento: FormControl<ChamadoFormRawValue['valorOrcamento']>;
  descricao: FormControl<ChamadoFormRawValue['descricao']>;
  cliente: FormControl<ChamadoFormRawValue['cliente']>;
  tecnico: FormControl<ChamadoFormRawValue['tecnico']>;
};

export type ChamadoFormGroup = FormGroup<ChamadoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ChamadoFormService {
  createChamadoFormGroup(chamado: ChamadoFormGroupInput = { id: null }): ChamadoFormGroup {
    const chamadoRawValue = this.convertChamadoToChamadoRawValue({
      ...this.getFormDefaults(),
      ...chamado,
    });
    return new FormGroup<ChamadoFormGroupContent>({
      id: new FormControl(
        { value: chamadoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      titulo: new FormControl(chamadoRawValue.titulo, {
        validators: [Validators.required, Validators.minLength(4), Validators.maxLength(50)],
      }),
      status: new FormControl(chamadoRawValue.status, {
        validators: [Validators.required],
      }),
      prioridade: new FormControl(chamadoRawValue.prioridade, {
        validators: [Validators.required],
      }),
      dataAbertura: new FormControl(chamadoRawValue.dataAbertura, {
        validators: [Validators.required],
      }),
      dataFechamento: new FormControl(chamadoRawValue.dataFechamento),
      valorOrcamento: new FormControl(chamadoRawValue.valorOrcamento, {
        validators: [Validators.required, Validators.min(0)],
      }),
      descricao: new FormControl(chamadoRawValue.descricao, {
        validators: [Validators.minLength(4), Validators.maxLength(100)],
      }),
      cliente: new FormControl(chamadoRawValue.cliente, {
        validators: [Validators.required],
      }),
      tecnico: new FormControl(chamadoRawValue.tecnico, {
        validators: [Validators.required],
      }),
    });
  }

  getChamado(form: ChamadoFormGroup): IChamado | NewChamado {
    return this.convertChamadoRawValueToChamado(form.getRawValue() as ChamadoFormRawValue | NewChamadoFormRawValue);
  }

  resetForm(form: ChamadoFormGroup, chamado: ChamadoFormGroupInput): void {
    const chamadoRawValue = this.convertChamadoToChamadoRawValue({ ...this.getFormDefaults(), ...chamado });
    form.reset(
      {
        ...chamadoRawValue,
        id: { value: chamadoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ChamadoFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      dataAbertura: currentTime,
      dataFechamento: currentTime,
    };
  }

  private convertChamadoRawValueToChamado(rawChamado: ChamadoFormRawValue | NewChamadoFormRawValue): IChamado | NewChamado {
    return {
      ...rawChamado,
      dataAbertura: dayjs(rawChamado.dataAbertura, DATE_TIME_FORMAT),
      dataFechamento: dayjs(rawChamado.dataFechamento, DATE_TIME_FORMAT),
    };
  }

  private convertChamadoToChamadoRawValue(
    chamado: IChamado | (Partial<NewChamado> & ChamadoFormDefaults)
  ): ChamadoFormRawValue | PartialWithRequiredKeyOf<NewChamadoFormRawValue> {
    return {
      ...chamado,
      dataAbertura: chamado.dataAbertura ? chamado.dataAbertura.format(DATE_TIME_FORMAT) : undefined,
      dataFechamento: chamado.dataFechamento ? chamado.dataFechamento.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
