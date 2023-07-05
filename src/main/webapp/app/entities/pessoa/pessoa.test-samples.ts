import dayjs from 'dayjs/esm';

import { TipoPessoa } from 'app/entities/enumerations/tipo-pessoa.model';

import { IPessoa, NewPessoa } from './pessoa.model';

export const sampleWithRequiredData: IPessoa = {
  id: 82183,
  nome: 'Rodovia',
  cpf: 'InternalXXX',
  email: 'Lara.Franco@live.com',
  senha: 'Algodão',
  dataCadastro: dayjs('2023-07-04T22:59'),
  tipoPessoa: TipoPessoa['TECNICO'],
};

export const sampleWithPartialData: IPessoa = {
  id: 2046,
  nome: 'intuitive directiona',
  cpf: 'Avenida Con',
  email: 'DaviLucca.Silva52@yahoo.com',
  senha: 'Account',
  dataCadastro: dayjs('2023-07-05T01:51'),
  tipoPessoa: TipoPessoa['CLIENTE'],
};

export const sampleWithFullData: IPessoa = {
  id: 39846,
  nome: 'override',
  cpf: 'AGPXXXXXXXX',
  email: 'AnaClara.Saraiva97@gmail.com',
  senha: 'bypass input deposit',
  dataCadastro: dayjs('2023-07-05T04:14'),
  tipoPessoa: TipoPessoa['TECNICO'],
};

export const sampleWithNewData: NewPessoa = {
  nome: 'Sapatos platforms',
  cpf: 'Re-engineer',
  email: 'Gabriel_Nogueira92@bol.com.br',
  senha: 'Rodovia time-frame B2B',
  dataCadastro: dayjs('2023-07-05T16:57'),
  tipoPessoa: TipoPessoa['TECNICO'],
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
