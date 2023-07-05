import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ChamadoDetailComponent } from './chamado-detail.component';

describe('Chamado Management Detail Component', () => {
  let comp: ChamadoDetailComponent;
  let fixture: ComponentFixture<ChamadoDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ChamadoDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ chamado: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ChamadoDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ChamadoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load chamado on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.chamado).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
