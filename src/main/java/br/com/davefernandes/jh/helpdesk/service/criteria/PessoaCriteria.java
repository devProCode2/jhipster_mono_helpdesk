package br.com.davefernandes.jh.helpdesk.service.criteria;

import br.com.davefernandes.jh.helpdesk.domain.enumeration.TipoPessoa;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link br.com.davefernandes.jh.helpdesk.domain.Pessoa} entity. This class is used
 * in {@link br.com.davefernandes.jh.helpdesk.web.rest.PessoaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /pessoas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PessoaCriteria implements Serializable, Criteria {

    /**
     * Class for filtering TipoPessoa
     */
    public static class TipoPessoaFilter extends Filter<TipoPessoa> {

        public TipoPessoaFilter() {}

        public TipoPessoaFilter(TipoPessoaFilter filter) {
            super(filter);
        }

        @Override
        public TipoPessoaFilter copy() {
            return new TipoPessoaFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private StringFilter cpf;

    private StringFilter email;

    private StringFilter senha;

    private InstantFilter dataCadastro;

    private TipoPessoaFilter tipoPessoa;

    private Boolean distinct;

    public PessoaCriteria() {}

    public PessoaCriteria(PessoaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.cpf = other.cpf == null ? null : other.cpf.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.senha = other.senha == null ? null : other.senha.copy();
        this.dataCadastro = other.dataCadastro == null ? null : other.dataCadastro.copy();
        this.tipoPessoa = other.tipoPessoa == null ? null : other.tipoPessoa.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PessoaCriteria copy() {
        return new PessoaCriteria(this);
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

    public StringFilter getNome() {
        return nome;
    }

    public StringFilter nome() {
        if (nome == null) {
            nome = new StringFilter();
        }
        return nome;
    }

    public void setNome(StringFilter nome) {
        this.nome = nome;
    }

    public StringFilter getCpf() {
        return cpf;
    }

    public StringFilter cpf() {
        if (cpf == null) {
            cpf = new StringFilter();
        }
        return cpf;
    }

    public void setCpf(StringFilter cpf) {
        this.cpf = cpf;
    }

    public StringFilter getEmail() {
        return email;
    }

    public StringFilter email() {
        if (email == null) {
            email = new StringFilter();
        }
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getSenha() {
        return senha;
    }

    public StringFilter senha() {
        if (senha == null) {
            senha = new StringFilter();
        }
        return senha;
    }

    public void setSenha(StringFilter senha) {
        this.senha = senha;
    }

    public InstantFilter getDataCadastro() {
        return dataCadastro;
    }

    public InstantFilter dataCadastro() {
        if (dataCadastro == null) {
            dataCadastro = new InstantFilter();
        }
        return dataCadastro;
    }

    public void setDataCadastro(InstantFilter dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public TipoPessoaFilter getTipoPessoa() {
        return tipoPessoa;
    }

    public TipoPessoaFilter tipoPessoa() {
        if (tipoPessoa == null) {
            tipoPessoa = new TipoPessoaFilter();
        }
        return tipoPessoa;
    }

    public void setTipoPessoa(TipoPessoaFilter tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
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
        final PessoaCriteria that = (PessoaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(cpf, that.cpf) &&
            Objects.equals(email, that.email) &&
            Objects.equals(senha, that.senha) &&
            Objects.equals(dataCadastro, that.dataCadastro) &&
            Objects.equals(tipoPessoa, that.tipoPessoa) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, cpf, email, senha, dataCadastro, tipoPessoa, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PessoaCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (nome != null ? "nome=" + nome + ", " : "") +
            (cpf != null ? "cpf=" + cpf + ", " : "") +
            (email != null ? "email=" + email + ", " : "") +
            (senha != null ? "senha=" + senha + ", " : "") +
            (dataCadastro != null ? "dataCadastro=" + dataCadastro + ", " : "") +
            (tipoPessoa != null ? "tipoPessoa=" + tipoPessoa + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
