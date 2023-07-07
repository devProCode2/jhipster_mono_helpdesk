import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IPessoa, NewPessoa } from '../pessoa.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPessoa for edit and NewPessoaFormGroupInput for create.
 */
type PessoaFormGroupInput = IPessoa | PartialWithRequiredKeyOf<NewPessoa>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IPessoa | NewPessoa> = Omit<T, 'dataCadastro'> & {
  dataCadastro?: string | null;
};

type PessoaFormRawValue = FormValueOf<IPessoa>;

type NewPessoaFormRawValue = FormValueOf<NewPessoa>;

type PessoaFormDefaults = Pick<NewPessoa, 'id' | 'dataCadastro'>;

type PessoaFormGroupContent = {
  id: FormControl<PessoaFormRawValue['id'] | NewPessoa['id']>;
  nome: FormControl<PessoaFormRawValue['nome']>;
  cpf: FormControl<PessoaFormRawValue['cpf']>;
  email: FormControl<PessoaFormRawValue['email']>;
  senha: FormControl<PessoaFormRawValue['senha']>;
  dataCadastro: FormControl<PessoaFormRawValue['dataCadastro']>;
  celular: FormControl<PessoaFormRawValue['celular']>;
  tipoPessoa: FormControl<PessoaFormRawValue['tipoPessoa']>;
};

export type PessoaFormGroup = FormGroup<PessoaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PessoaFormService {
  createPessoaFormGroup(pessoa: PessoaFormGroupInput = { id: null }): PessoaFormGroup {
    const pessoaRawValue = this.convertPessoaToPessoaRawValue({
      ...this.getFormDefaults(),
      ...pessoa,
    });
    return new FormGroup<PessoaFormGroupContent>({
      id: new FormControl(
        { value: pessoaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      nome: new FormControl(pessoaRawValue.nome, {
        validators: [Validators.required, Validators.minLength(2), Validators.maxLength(20)],
      }),
      cpf: new FormControl(pessoaRawValue.cpf, {
        validators: [Validators.required, Validators.minLength(11), Validators.maxLength(11)],
      }),
      email: new FormControl(pessoaRawValue.email, {
        validators: [Validators.required, Validators.minLength(4), Validators.maxLength(30)],
      }),
      senha: new FormControl(pessoaRawValue.senha, {
        validators: [Validators.required, Validators.minLength(4), Validators.maxLength(50)],
      }),
      dataCadastro: new FormControl(pessoaRawValue.dataCadastro, {
        validators: [Validators.required],
      }),
      celular: new FormControl(pessoaRawValue.celular, {
        validators: [Validators.required, Validators.minLength(11), Validators.maxLength(11)],
      }),
      tipoPessoa: new FormControl(pessoaRawValue.tipoPessoa, {
        validators: [Validators.required],
      }),
    });
  }

  getPessoa(form: PessoaFormGroup): IPessoa | NewPessoa {
    return this.convertPessoaRawValueToPessoa(form.getRawValue() as PessoaFormRawValue | NewPessoaFormRawValue);
  }

  resetForm(form: PessoaFormGroup, pessoa: PessoaFormGroupInput): void {
    const pessoaRawValue = this.convertPessoaToPessoaRawValue({ ...this.getFormDefaults(), ...pessoa });
    form.reset(
      {
        ...pessoaRawValue,
        id: { value: pessoaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PessoaFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      dataCadastro: currentTime,
    };
  }

  private convertPessoaRawValueToPessoa(rawPessoa: PessoaFormRawValue | NewPessoaFormRawValue): IPessoa | NewPessoa {
    return {
      ...rawPessoa,
      dataCadastro: dayjs(rawPessoa.dataCadastro, DATE_TIME_FORMAT),
    };
  }

  private convertPessoaToPessoaRawValue(
    pessoa: IPessoa | (Partial<NewPessoa> & PessoaFormDefaults)
  ): PessoaFormRawValue | PartialWithRequiredKeyOf<NewPessoaFormRawValue> {
    return {
      ...pessoa,
      dataCadastro: pessoa.dataCadastro ? pessoa.dataCadastro.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
