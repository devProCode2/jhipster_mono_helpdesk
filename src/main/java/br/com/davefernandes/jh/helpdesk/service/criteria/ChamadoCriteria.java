package br.com.davefernandes.jh.helpdesk.service.criteria;

import br.com.davefernandes.jh.helpdesk.domain.enumeration.Prioridade;
import br.com.davefernandes.jh.helpdesk.domain.enumeration.Status;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link br.com.davefernandes.jh.helpdesk.domain.Chamado} entity. This class is used
 * in {@link br.com.davefernandes.jh.helpdesk.web.rest.ChamadoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /chamados?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ChamadoCriteria implements Serializable, Criteria {

    /**
     * Class for filtering Status
     */
    public static class StatusFilter extends Filter<Status> {

        public StatusFilter() {}

        public StatusFilter(StatusFilter filter) {
            super(filter);
        }

        @Override
        public StatusFilter copy() {
            return new StatusFilter(this);
        }
    }

    /**
     * Class for filtering Prioridade
     */
    public static class PrioridadeFilter extends Filter<Prioridade> {

        public PrioridadeFilter() {}

        public PrioridadeFilter(PrioridadeFilter filter) {
            super(filter);
        }

        @Override
        public PrioridadeFilter copy() {
            return new PrioridadeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter titulo;

    private StatusFilter status;

    private PrioridadeFilter prioridade;

    private InstantFilter dataAbertura;

    private InstantFilter dataFechamento;

    private BigDecimalFilter valorOrcamento;

    private StringFilter descricao;

    private Boolean distinct;

    public ChamadoCriteria() {}

    public ChamadoCriteria(ChamadoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.titulo = other.titulo == null ? null : other.titulo.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.prioridade = other.prioridade == null ? null : other.prioridade.copy();
        this.dataAbertura = other.dataAbertura == null ? null : other.dataAbertura.copy();
        this.dataFechamento = other.dataFechamento == null ? null : other.dataFechamento.copy();
        this.valorOrcamento = other.valorOrcamento == null ? null : other.valorOrcamento.copy();
        this.descricao = other.descricao == null ? null : other.descricao.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ChamadoCriteria copy() {
        return new ChamadoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTitulo() {
        return titulo;
    }

    public StringFilter titulo() {
        if (titulo == null) {
            titulo = new StringFilter();
        }
        return titulo;
    }

    public void setTitulo(StringFilter titulo) {
        this.titulo = titulo;
    }

    public StatusFilter getStatus() {
        return status;
    }

    public StatusFilter status() {
        if (status == null) {
            status = new StatusFilter();
        }
        return status;
    }

    public void setStatus(StatusFilter status) {
        this.status = status;
    }

    public PrioridadeFilter getPrioridade() {
        return prioridade;
    }

    public PrioridadeFilter prioridade() {
        if (prioridade == null) {
            prioridade = new PrioridadeFilter();
        }
        return prioridade;
    }

    public void setPrioridade(PrioridadeFilter prioridade) {
        this.prioridade = prioridade;
    }

    public InstantFilter getDataAbertura() {
        return dataAbertura;
    }

    public InstantFilter dataAbertura() {
        if (dataAbertura == null) {
            dataAbertura = new InstantFilter();
        }
        return dataAbertura;
    }

    public void setDataAbertura(InstantFilter dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public InstantFilter getDataFechamento() {
        return dataFechamento;
    }

    public InstantFilter dataFechamento() {
        if (dataFechamento == null) {
            dataFechamento = new InstantFilter();
        }
        return dataFechamento;
    }

    public void setDataFechamento(InstantFilter dataFechamento) {
        this.dataFechamento = dataFechamento;
    }

    public BigDecimalFilter getValorOrcamento() {
        return valorOrcamento;
    }

    public BigDecimalFilter valorOrcamento() {
        if (valorOrcamento == null) {
            valorOrcamento = new BigDecimalFilter();
        }
        return valorOrcamento;
    }

    public void setValorOrcamento(BigDecimalFilter valorOrcamento) {
        this.valorOrcamento = valorOrcamento;
    }

    public StringFilter getDescricao() {
        return descricao;
    }

    public StringFilter descricao() {
        if (descricao == null) {
            descricao = new StringFilter();
        }
        return descricao;
    }

    public void setDescricao(StringFilter descricao) {
        this.descricao = descricao;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ChamadoCriteria that = (ChamadoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(titulo, that.titulo) &&
            Objects.equals(status, that.status) &&
            Objects.equals(prioridade, that.prioridade) &&
            Objects.equals(dataAbertura, that.dataAbertura) &&
            Objects.equals(dataFechamento, that.dataFechamento) &&
            Objects.equals(valorOrcamento, that.valorOrcamento) &&
            Objects.equals(descricao, that.descricao) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, titulo, status, prioridade, dataAbertura, dataFechamento, valorOrcamento, descricao, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChamadoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (titulo != null ? "titulo=" + titulo + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (prioridade != null ? "prioridade=" + prioridade + ", " : "") +
            (dataAbertura != null ? "dataAbertura=" + dataAbertura + ", " : "") +
            (dataFechamento != null ? "dataFechamento=" + dataFechamento + ", " : "") +
            (valorOrcamento != null ? "valorOrcamento=" + valorOrcamento + ", " : "") +
            (descricao != null ? "descricao=" + descricao + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
