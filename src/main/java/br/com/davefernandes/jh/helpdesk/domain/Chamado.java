package br.com.davefernandes.jh.helpdesk.domain;

import br.com.davefernandes.jh.helpdesk.domain.enumeration.Prioridade;
import br.com.davefernandes.jh.helpdesk.domain.enumeration.Status;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Chamado.
 */
@Entity
@Table(name = "chamado")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Chamado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 4, max = 50)
    @Column(name = "titulo", length = 50, nullable = false)
    private String titulo;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "prioridade", nullable = false)
    private Prioridade prioridade;

    @NotNull
    @Column(name = "data_abertura", nullable = false)
    private Instant dataAbertura;

    @Column(name = "data_fechamento")
    private Instant dataFechamento;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "valor_orcamento", precision = 21, scale = 2, nullable = false)
    private BigDecimal valorOrcamento;

    @Size(min = 4, max = 100)
    @Column(name = "descricao", length = 100)
    private String descricao;

    @ManyToOne(optional = false)
    @NotNull
    private Pessoa cliente;

    @ManyToOne(optional = false)
    @NotNull
    private Pessoa tecnico;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Chamado id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public Chamado titulo(String titulo) {
        this.setTitulo(titulo);
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Status getStatus() {
        return this.status;
    }

    public Chamado status(Status status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Prioridade getPrioridade() {
        return this.prioridade;
    }

    public Chamado prioridade(Prioridade prioridade) {
        this.setPrioridade(prioridade);
        return this;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public Instant getDataAbertura() {
        return this.dataAbertura;
    }

    public Chamado dataAbertura(Instant dataAbertura) {
        this.setDataAbertura(dataAbertura);
        return this;
    }

    public void setDataAbertura(Instant dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public Instant getDataFechamento() {
        return this.dataFechamento;
    }

    public Chamado dataFechamento(Instant dataFechamento) {
        this.setDataFechamento(dataFechamento);
        return this;
    }

    public void setDataFechamento(Instant dataFechamento) {
        this.dataFechamento = dataFechamento;
    }

    public BigDecimal getValorOrcamento() {
        return this.valorOrcamento;
    }

    public Chamado valorOrcamento(BigDecimal valorOrcamento) {
        this.setValorOrcamento(valorOrcamento);
        return this;
    }

    public void setValorOrcamento(BigDecimal valorOrcamento) {
        this.valorOrcamento = valorOrcamento;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public Chamado descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Pessoa getCliente() {
        return this.cliente;
    }

    public void setCliente(Pessoa pessoa) {
        this.cliente = pessoa;
    }

    public Chamado cliente(Pessoa pessoa) {
        this.setCliente(pessoa);
        return this;
    }

    public Pessoa getTecnico() {
        return this.tecnico;
    }

    public void setTecnico(Pessoa pessoa) {
        this.tecnico = pessoa;
    }

    public Chamado tecnico(Pessoa pessoa) {
        this.setTecnico(pessoa);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Chamado)) {
            return false;
        }
        return id != null && id.equals(((Chamado) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Chamado{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", status='" + getStatus() + "'" +
            ", prioridade='" + getPrioridade() + "'" +
            ", dataAbertura='" + getDataAbertura() + "'" +
            ", dataFechamento='" + getDataFechamento() + "'" +
            ", valorOrcamento=" + getValorOrcamento() +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
