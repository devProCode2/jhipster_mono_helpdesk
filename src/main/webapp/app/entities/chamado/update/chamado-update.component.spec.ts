import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ChamadoFormService } from './chamado-form.service';
import { ChamadoService } from '../service/chamado.service';
import { IChamado } from '../chamado.model';
import { IPessoa } from 'app/entities/pessoa/pessoa.model';
import { PessoaService } from 'app/entities/pessoa/service/pessoa.service';

import { ChamadoUpdateComponent } from './chamado-update.component';

describe('Chamado Management Update Component', () => {
  let comp: ChamadoUpdateComponent;
  let fixture: ComponentFixture<ChamadoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let chamadoFormService: ChamadoFormService;
  let chamadoService: ChamadoService;
  let pessoaService: PessoaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ChamadoUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ChamadoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ChamadoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    chamadoFormService = TestBed.inject(ChamadoFormService);
    chamadoService = TestBed.inject(ChamadoService);
    pessoaService = TestBed.inject(PessoaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Pessoa query and add missing value', () => {
      const chamado: IChamado = { id: 456 };
      const cliente: IPessoa = { id: 14617 };
      chamado.cliente = cliente;
      const tecnico: IPessoa = { id: 81229 };
      chamado.tecnico = tecnico;

      const pessoaCollection: IPessoa[] = [{ id: 66906 }];
      jest.spyOn(pessoaService, 'query').mockReturnValue(of(new HttpResponse({ body: pessoaCollection })));
      const additionalPessoas = [cliente, tecnico];
      const expectedCollection: IPessoa[] = [...additionalPessoas, ...pessoaCollection];
      jest.spyOn(pessoaService, 'addPessoaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ chamado });
      comp.ngOnInit();

      expect(pessoaService.query).toHaveBeenCalled();
      expect(pessoaService.addPessoaToCollectionIfMissing).toHaveBeenCalledWith(
        pessoaCollection,
        ...additionalPessoas.map(expect.objectContaining)
      );
      expect(comp.pessoasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const chamado: IChamado = { id: 456 };
      const cliente: IPessoa = { id: 32440 };
      chamado.cliente = cliente;
      const tecnico: IPessoa = { id: 19826 };
      chamado.tecnico = tecnico;

      activatedRoute.data = of({ chamado });
      comp.ngOnInit();

      expect(comp.pessoasSharedCollection).toContain(cliente);
      expect(comp.pessoasSharedCollection).toContain(tecnico);
      expect(comp.chamado).toEqual(chamado);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IChamado>>();
      const chamado = { id: 123 };
      jest.spyOn(chamadoFormService, 'getChamado').mockReturnValue(chamado);
      jest.spyOn(chamadoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ chamado });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: chamado }));
      saveSubject.complete();

      // THEN
      expect(chamadoFormService.getChamado).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(chamadoService.update).toHaveBeenCalledWith(expect.objectContaining(chamado));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IChamado>>();
      const chamado = { id: 123 };
      jest.spyOn(chamadoFormService, 'getChamado').mockReturnValue({ id: null });
      jest.spyOn(chamadoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ chamado: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: chamado }));
      saveSubject.complete();

      // THEN
      expect(chamadoFormService.getChamado).toHaveBeenCalled();
      expect(chamadoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IChamado>>();
      const chamado = { id: 123 };
      jest.spyOn(chamadoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ chamado });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(chamadoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('comparePessoa', () => {
      it('Should forward to pessoaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(pessoaService, 'comparePessoa');
        comp.comparePessoa(entity, entity2);
        expect(pessoaService.comparePessoa).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
