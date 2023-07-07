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
  celular: 'AutomotivoX',
  tipoPessoa: TipoPessoa['TECNICO'],
};

export const sampleWithPartialData: IPessoa = {
  id: 70057,
  nome: 'incremental',
  cpf: 'mobileXXXXX',
  email: 'Vitor44@gmail.com',
  senha: 'XSSX',
  dataCadastro: dayjs('2023-07-05T15:26'),
  celular: 'XMLXXXXXXXX',
  tipoPessoa: TipoPessoa['TECNICO'],
};

export const sampleWithFullData: IPessoa = {
  id: 60480,
  nome: 'Consultant',
  cpf: 'Sapatos mar',
  email: 'Esther72@yahoo.com',
  senha: 'vermelho bypass input',
  dataCadastro: dayjs('2023-07-05T04:42'),
  celular: 'moratoriumX',
  tipoPessoa: TipoPessoa['TECNICO'],
};

export const sampleWithNewData: NewPessoa = {
  nome: 'Configuration',
  cpf: 'platforms M',
  email: 'Raul_Carvalho@bol.com.br',
  senha: 'neural',
  dataCadastro: dayjs('2023-07-04T22:10'),
  celular: 'Rodovia tim',
  tipoPessoa: TipoPessoa['TECNICO'],
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
