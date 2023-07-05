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

import { ChamadoUpdateComponent } from './chamado-update.component';

describe('Chamado Management Update Component', () => {
  let comp: ChamadoUpdateComponent;
  let fixture: ComponentFixture<ChamadoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let chamadoFormService: ChamadoFormService;
  let chamadoService: ChamadoService;

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

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const chamado: IChamado = { id: 456 };

      activatedRoute.data = of({ chamado });
      comp.ngOnInit();

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
});
