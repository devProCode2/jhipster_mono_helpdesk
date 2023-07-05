import dayjs from 'dayjs/esm';

import { Status } from 'app/entities/enumerations/status.model';
import { Prioridade } from 'app/entities/enumerations/prioridade.model';

import { IChamado, NewChamado } from './chamado.model';

export const sampleWithRequiredData: IChamado = {
  id: 9857,
  titulo: 'azul',
  status: Status['ABERTO'],
  prioridade: Prioridade['MEDIA'],
  dataAbertura: dayjs('2023-07-05T11:17'),
  valorOrcamento: 10462,
};

export const sampleWithPartialData: IChamado = {
  id: 85757,
  titulo: 'Analyst circuit Jersey',
  status: Status['ABERTO'],
  prioridade: Prioridade['BAIXA'],
  dataAbertura: dayjs('2023-07-05T21:35'),
  valorOrcamento: 98233,
};

export const sampleWithFullData: IChamado = {
  id: 57465,
  titulo: 'Senior navigating web',
  status: Status['ENCERRADO'],
  prioridade: Prioridade['ALTA'],
  dataAbertura: dayjs('2023-07-05T04:49'),
  dataFechamento: dayjs('2023-07-05T10:47'),
  valorOrcamento: 9526,
  descricao: 'Cadeira incubate',
};

export const sampleWithNewData: NewChamado = {
  titulo: 'bypass COM',
  status: Status['ANDAMENTO'],
  prioridade: Prioridade['MEDIA'],
  dataAbertura: dayjs('2023-07-05T21:31'),
  valorOrcamento: 12159,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
