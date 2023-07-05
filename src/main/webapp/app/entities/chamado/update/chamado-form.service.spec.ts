import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../chamado.test-samples';

import { ChamadoFormService } from './chamado-form.service';

describe('Chamado Form Service', () => {
  let service: ChamadoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ChamadoFormService);
  });

  describe('Service methods', () => {
    describe('createChamadoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createChamadoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            titulo: expect.any(Object),
            status: expect.any(Object),
            prioridade: expect.any(Object),
            dataAbertura: expect.any(Object),
            dataFechamento: expect.any(Object),
            valorOrcamento: expect.any(Object),
            descricao: expect.any(Object),
            cliente: expect.any(Object),
            tecnico: expect.any(Object),
          })
        );
      });

      it('passing IChamado should create a new form with FormGroup', () => {
        const formGroup = service.createChamadoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            titulo: expect.any(Object),
            status: expect.any(Object),
            prioridade: expect.any(Object),
            dataAbertura: expect.any(Object),
            dataFechamento: expect.any(Object),
            valorOrcamento: expect.any(Object),
            descricao: expect.any(Object),
            cliente: expect.any(Object),
            tecnico: expect.any(Object),
          })
        );
      });
    });

    describe('getChamado', () => {
      it('should return NewChamado for default Chamado initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createChamadoFormGroup(sampleWithNewData);

        const chamado = service.getChamado(formGroup) as any;

        expect(chamado).toMatchObject(sampleWithNewData);
      });

      it('should return NewChamado for empty Chamado initial value', () => {
        const formGroup = service.createChamadoFormGroup();

        const chamado = service.getChamado(formGroup) as any;

        expect(chamado).toMatchObject({});
      });

      it('should return IChamado', () => {
        const formGroup = service.createChamadoFormGroup(sampleWithRequiredData);

        const chamado = service.getChamado(formGroup) as any;

        expect(chamado).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IChamado should not enable id FormControl', () => {
        const formGroup = service.createChamadoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewChamado should disable id FormControl', () => {
        const formGroup = service.createChamadoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
