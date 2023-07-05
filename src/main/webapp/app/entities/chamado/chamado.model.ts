import dayjs from 'dayjs/esm';
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
}

export type NewChamado = Omit<IChamado, 'id'> & { id: null };
