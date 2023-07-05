import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IChamado } from '../chamado.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../chamado.test-samples';

import { ChamadoService, RestChamado } from './chamado.service';

const requireRestSample: RestChamado = {
  ...sampleWithRequiredData,
  dataAbertura: sampleWithRequiredData.dataAbertura?.toJSON(),
  dataFechamento: sampleWithRequiredData.dataFechamento?.toJSON(),
};

describe('Chamado Service', () => {
  let service: ChamadoService;
  let httpMock: HttpTestingController;
  let expectedResult: IChamado | IChamado[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ChamadoService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Chamado', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const chamado = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(chamado).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Chamado', () => {
      const chamado = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(chamado).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Chamado', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Chamado', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Chamado', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addChamadoToCollectionIfMissing', () => {
      it('should add a Chamado to an empty array', () => {
        const chamado: IChamado = sampleWithRequiredData;
        expectedResult = service.addChamadoToCollectionIfMissing([], chamado);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(chamado);
      });

      it('should not add a Chamado to an array that contains it', () => {
        const chamado: IChamado = sampleWithRequiredData;
        const chamadoCollection: IChamado[] = [
          {
            ...chamado,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addChamadoToCollectionIfMissing(chamadoCollection, chamado);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Chamado to an array that doesn't contain it", () => {
        const chamado: IChamado = sampleWithRequiredData;
        const chamadoCollection: IChamado[] = [sampleWithPartialData];
        expectedResult = service.addChamadoToCollectionIfMissing(chamadoCollection, chamado);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(chamado);
      });

      it('should add only unique Chamado to an array', () => {
        const chamadoArray: IChamado[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const chamadoCollection: IChamado[] = [sampleWithRequiredData];
        expectedResult = service.addChamadoToCollectionIfMissing(chamadoCollection, ...chamadoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const chamado: IChamado = sampleWithRequiredData;
        const chamado2: IChamado = sampleWithPartialData;
        expectedResult = service.addChamadoToCollectionIfMissing([], chamado, chamado2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(chamado);
        expect(expectedResult).toContain(chamado2);
      });

      it('should accept null and undefined values', () => {
        const chamado: IChamado = sampleWithRequiredData;
        expectedResult = service.addChamadoToCollectionIfMissing([], null, chamado, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(chamado);
      });

      it('should return initial array if no Chamado is added', () => {
        const chamadoCollection: IChamado[] = [sampleWithRequiredData];
        expectedResult = service.addChamadoToCollectionIfMissing(chamadoCollection, undefined, null);
        expect(expectedResult).toEqual(chamadoCollection);
      });
    });

    describe('compareChamado', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareChamado(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareChamado(entity1, entity2);
        const compareResult2 = service.compareChamado(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareChamado(entity1, entity2);
        const compareResult2 = service.compareChamado(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareChamado(entity1, entity2);
        const compareResult2 = service.compareChamado(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
