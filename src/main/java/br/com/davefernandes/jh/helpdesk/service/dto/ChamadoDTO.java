package br.com.davefernandes.jh.helpdesk.service.dto;

import br.com.davefernandes.jh.helpdesk.domain.enumeration.Prioridade;
import br.com.davefernandes.jh.helpdesk.domain.enumeration.Status;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link br.com.davefernandes.jh.helpdesk.domain.Chamado} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ChamadoDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 4, max = 50)
    private String titulo;

    @NotNull
    private Status status;

    @NotNull
    private Prioridade prioridade;

    @NotNull
    private Instant dataAbertura;

    private Instant dataFechamento;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal valorOrcamento;

    @Size(min = 4, max = 100)
    private String descricao;

    private PessoaDTO cliente;

    private PessoaDTO tecnico;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public Instant getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(Instant dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public Instant getDataFechamento() {
        return dataFechamento;
    }

    public void setDataFechamento(Instant dataFechamento) {
        this.dataFechamento = dataFechamento;
    }

    public BigDecimal getValorOrcamento() {
        return valorOrcamento;
    }

    public void setValorOrcamento(BigDecimal valorOrcamento) {
        this.valorOrcamento = valorOrcamento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public PessoaDTO getCliente() {
        return cliente;
    }

    public void setCliente(PessoaDTO cliente) {
        this.cliente = cliente;
    }

    public PessoaDTO getTecnico() {
        return tecnico;
    }

    public void setTecnico(PessoaDTO tecnico) {
        this.tecnico = tecnico;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChamadoDTO)) {
            return false;
        }

        ChamadoDTO chamadoDTO = (ChamadoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, chamadoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChamadoDTO{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", status='" + getStatus() + "'" +
            ", prioridade='" + getPrioridade() + "'" +
            ", dataAbertura='" + getDataAbertura() + "'" +
            ", dataFechamento='" + getDataFechamento() + "'" +
            ", valorOrcamento=" + getValorOrcamento() +
            ", descricao='" + getDescricao() + "'" +
            ", cliente=" + getCliente() +
            ", tecnico=" + getTecnico() +
            "}";
    }
}
