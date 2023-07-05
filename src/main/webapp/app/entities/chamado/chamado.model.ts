import dayjs from 'dayjs/esm';
import { IPessoa } from 'app/entities/pessoa/pessoa.model';
import { Status } from 'app/entities/enumerations/status.model';
import { Prioridade } from 'app/entities/enumerations/prioridade.model';

export interface IChamado {
  id: number;
  titulo?: string | null;
  status?: Status | null;
  prioridade?: Prioridade | null;
  dataAbertura?: dayjs.Dayjs | null;
  dataFechamento?: dayjs.Dayjs | null;
  valorOrcamento?: number | null;
  descricao?: string | null;
  cliente?: Pick<IPessoa, 'id' | 'nome'> | null;
  tecnico?: Pick<IPessoa, 'id' | 'nome'> | null;
}

export type NewChamado = Omit<IChamado, 'id'> & { id: null };
