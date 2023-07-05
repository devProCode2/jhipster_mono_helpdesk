import dayjs from 'dayjs/esm';
import { TipoPessoa } from 'app/entities/enumerations/tipo-pessoa.model';

export interface IPessoa {
  id: number;
  nome?: string | null;
  cpf?: string | null;
  email?: string | null;
  senha?: string | null;
  dataCadastro?: dayjs.Dayjs | null;
  tipoPessoa?: TipoPessoa | null;
}

export type NewPessoa = Omit<IPessoa, 'id'> & { id: null };
