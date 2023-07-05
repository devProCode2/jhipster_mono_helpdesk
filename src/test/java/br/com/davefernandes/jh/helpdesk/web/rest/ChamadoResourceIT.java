package br.com.davefernandes.jh.helpdesk.web.rest;

import static br.com.davefernandes.jh.helpdesk.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.davefernandes.jh.helpdesk.IntegrationTest;
import br.com.davefernandes.jh.helpdesk.domain.Chamado;
import br.com.davefernandes.jh.helpdesk.domain.enumeration.Prioridade;
import br.com.davefernandes.jh.helpdesk.domain.enumeration.Status;
import br.com.davefernandes.jh.helpdesk.repository.ChamadoRepository;
import br.com.davefernandes.jh.helpdesk.service.criteria.ChamadoCriteria;
import br.com.davefernandes.jh.helpdesk.service.dto.ChamadoDTO;
import br.com.davefernandes.jh.helpdesk.service.mapper.ChamadoMapper;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ChamadoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ChamadoResourceIT {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.ABERTO;
    private static final Status UPDATED_STATUS = Status.ANDAMENTO;

    private static final Prioridade DEFAULT_PRIORIDADE = Prioridade.BAIXA;
    private static final Prioridade UPDATED_PRIORIDADE = Prioridade.MEDIA;

    private static final Instant DEFAULT_DATA_ABERTURA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_ABERTURA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATA_FECHAMENTO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_FECHAMENTO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final BigDecimal DEFAULT_VALOR_ORCAMENTO = new BigDecimal(0);
    private static final BigDecimal UPDATED_VALOR_ORCAMENTO = new BigDecimal(1);
    private static final BigDecimal SMALLER_VALOR_ORCAMENTO = new BigDecimal(0 - 1);

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/chamados";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ChamadoRepository chamadoRepository;

    @Autowired
    private ChamadoMapper chamadoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restChamadoMockMvc;

    private Chamado chamado;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Chamado createEntity(EntityManager em) {
        Chamado chamado = new Chamado()
            .titulo(DEFAULT_TITULO)
            .status(DEFAULT_STATUS)
            .prioridade(DEFAULT_PRIORIDADE)
            .dataAbertura(DEFAULT_DATA_ABERTURA)
            .dataFechamento(DEFAULT_DATA_FECHAMENTO)
            .valorOrcamento(DEFAULT_VALOR_ORCAMENTO)
            .descricao(DEFAULT_DESCRICAO);
        return chamado;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Chamado createUpdatedEntity(EntityManager em) {
        Chamado chamado = new Chamado()
            .titulo(UPDATED_TITULO)
            .status(UPDATED_STATUS)
            .prioridade(UPDATED_PRIORIDADE)
            .dataAbertura(UPDATED_DATA_ABERTURA)
            .dataFechamento(UPDATED_DATA_FECHAMENTO)
            .valorOrcamento(UPDATED_VALOR_ORCAMENTO)
            .descricao(UPDATED_DESCRICAO);
        return chamado;
    }

    @BeforeEach
    public void initTest() {
        chamado = createEntity(em);
    }

    @Test
    @Transactional
    void createChamado() throws Exception {
        int databaseSizeBeforeCreate = chamadoRepository.findAll().size();
        // Create the Chamado
        ChamadoDTO chamadoDTO = chamadoMapper.toDto(chamado);
        restChamadoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chamadoDTO)))
            .andExpect(status().isCreated());

        // Validate the Chamado in the database
        List<Chamado> chamadoList = chamadoRepository.findAll();
        assertThat(chamadoList).hasSize(databaseSizeBeforeCreate + 1);
        Chamado testChamado = chamadoList.get(chamadoList.size() - 1);
        assertThat(testChamado.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testChamado.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testChamado.getPrioridade()).isEqualTo(DEFAULT_PRIORIDADE);
        assertThat(testChamado.getDataAbertura()).isEqualTo(DEFAULT_DATA_ABERTURA);
        assertThat(testChamado.getDataFechamento()).isEqualTo(DEFAULT_DATA_FECHAMENTO);
        assertThat(testChamado.getValorOrcamento()).isEqualByComparingTo(DEFAULT_VALOR_ORCAMENTO);
        assertThat(testChamado.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    void createChamadoWithExistingId() throws Exception {
        // Create the Chamado with an existing ID
        chamado.setId(1L);
        ChamadoDTO chamadoDTO = chamadoMapper.toDto(chamado);

        int databaseSizeBeforeCreate = chamadoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restChamadoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chamadoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Chamado in the database
        List<Chamado> chamadoList = chamadoRepository.findAll();
        assertThat(chamadoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTituloIsRequired() throws Exception {
        int databaseSizeBeforeTest = chamadoRepository.findAll().size();
        // set the field null
        chamado.setTitulo(null);

        // Create the Chamado, which fails.
        ChamadoDTO chamadoDTO = chamadoMapper.toDto(chamado);

        restChamadoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chamadoDTO)))
            .andExpect(status().isBadRequest());

        List<Chamado> chamadoList = chamadoRepository.findAll();
        assertThat(chamadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = chamadoRepository.findAll().size();
        // set the field null
        chamado.setStatus(null);

        // Create the Chamado, which fails.
        ChamadoDTO chamadoDTO = chamadoMapper.toDto(chamado);

        restChamadoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chamadoDTO)))
            .andExpect(status().isBadRequest());

        List<Chamado> chamadoList = chamadoRepository.findAll();
        assertThat(chamadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrioridadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = chamadoRepository.findAll().size();
        // set the field null
        chamado.setPrioridade(null);

        // Create the Chamado, which fails.
        ChamadoDTO chamadoDTO = chamadoMapper.toDto(chamado);

        restChamadoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chamadoDTO)))
            .andExpect(status().isBadRequest());

        List<Chamado> chamadoList = chamadoRepository.findAll();
        assertThat(chamadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDataAberturaIsRequired() throws Exception {
        int databaseSizeBeforeTest = chamadoRepository.findAll().size();
        // set the field null
        chamado.setDataAbertura(null);

        // Create the Chamado, which fails.
        ChamadoDTO chamadoDTO = chamadoMapper.toDto(chamado);

        restChamadoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chamadoDTO)))
            .andExpect(status().isBadRequest());

        List<Chamado> chamadoList = chamadoRepository.findAll();
        assertThat(chamadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValorOrcamentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = chamadoRepository.findAll().size();
        // set the field null
        chamado.setValorOrcamento(null);

        // Create the Chamado, which fails.
        ChamadoDTO chamadoDTO = chamadoMapper.toDto(chamado);

        restChamadoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chamadoDTO)))
            .andExpect(status().isBadRequest());

        List<Chamado> chamadoList = chamadoRepository.findAll();
        assertThat(chamadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllChamados() throws Exception {
        // Initialize the database
        chamadoRepository.saveAndFlush(chamado);

        // Get all the chamadoList
        restChamadoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chamado.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].prioridade").value(hasItem(DEFAULT_PRIORIDADE.toString())))
            .andExpect(jsonPath("$.[*].dataAbertura").value(hasItem(DEFAULT_DATA_ABERTURA.toString())))
            .andExpect(jsonPath("$.[*].dataFechamento").value(hasItem(DEFAULT_DATA_FECHAMENTO.toString())))
            .andExpect(jsonPath("$.[*].valorOrcamento").value(hasItem(sameNumber(DEFAULT_VALOR_ORCAMENTO))))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @Test
    @Transactional
    void getChamado() throws Exception {
        // Initialize the database
        chamadoRepository.saveAndFlush(chamado);

        // Get the chamado
        restChamadoMockMvc
            .perform(get(ENTITY_API_URL_ID, chamado.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(chamado.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.prioridade").value(DEFAULT_PRIORIDADE.toString()))
            .andExpect(jsonPath("$.dataAbertura").value(DEFAULT_DATA_ABERTURA.toString()))
            .andExpect(jsonPath("$.dataFechamento").value(DEFAULT_DATA_FECHAMENTO.toString()))
            .andExpect(jsonPath("$.valorOrcamento").value(sameNumber(DEFAULT_VALOR_ORCAMENTO)))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }

    @Test
    @Transactional
    void getChamadosByIdFiltering() throws Exception {
        // Initialize the database
        chamadoRepository.saveAndFlush(chamado);

        Long id = chamado.getId();

        defaultChamadoShouldBeFound("id.equals=" + id);
        defaultChamadoShouldNotBeFound("id.notEquals=" + id);

        defaultChamadoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultChamadoShouldNotBeFound("id.greaterThan=" + id);

        defaultChamadoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultChamadoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllChamadosByTituloIsEqualToSomething() throws Exception {
        // Initialize the database
        chamadoRepository.saveAndFlush(chamado);

        // Get all the chamadoList where titulo equals to DEFAULT_TITULO
        defaultChamadoShouldBeFound("titulo.equals=" + DEFAULT_TITULO);

        // Get all the chamadoList where titulo equals to UPDATED_TITULO
        defaultChamadoShouldNotBeFound("titulo.equals=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    void getAllChamadosByTituloIsInShouldWork() throws Exception {
        // Initialize the database
        chamadoRepository.saveAndFlush(chamado);

        // Get all the chamadoList where titulo in DEFAULT_TITULO or UPDATED_TITULO
        defaultChamadoShouldBeFound("titulo.in=" + DEFAULT_TITULO + "," + UPDATED_TITULO);

        // Get all the chamadoList where titulo equals to UPDATED_TITULO
        defaultChamadoShouldNotBeFound("titulo.in=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    void getAllChamadosByTituloIsNullOrNotNull() throws Exception {
        // Initialize the database
        chamadoRepository.saveAndFlush(chamado);

        // Get all the chamadoList where titulo is not null
        defaultChamadoShouldBeFound("titulo.specified=true");

        // Get all the chamadoList where titulo is null
        defaultChamadoShouldNotBeFound("titulo.specified=false");
    }

    @Test
    @Transactional
    void getAllChamadosByTituloContainsSomething() throws Exception {
        // Initialize the database
        chamadoRepository.saveAndFlush(chamado);

        // Get all the chamadoList where titulo contains DEFAULT_TITULO
        defaultChamadoShouldBeFound("titulo.contains=" + DEFAULT_TITULO);

        // Get all the chamadoList where titulo contains UPDATED_TITULO
        defaultChamadoShouldNotBeFound("titulo.contains=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    void getAllChamadosByTituloNotContainsSomething() throws Exception {
        // Initialize the database
        chamadoRepository.saveAndFlush(chamado);

        // Get all the chamadoList where titulo does not contain DEFAULT_TITULO
        defaultChamadoShouldNotBeFound("titulo.doesNotContain=" + DEFAULT_TITULO);

        // Get all the chamadoList where titulo does not contain UPDATED_TITULO
        defaultChamadoShouldBeFound("titulo.doesNotContain=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    void getAllChamadosByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        chamadoRepository.saveAndFlush(chamado);

        // Get all the chamadoList where status equals to DEFAULT_STATUS
        defaultChamadoShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the chamadoList where status equals to UPDATED_STATUS
        defaultChamadoShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllChamadosByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        chamadoRepository.saveAndFlush(chamado);

        // Get all the chamadoList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultChamadoShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the chamadoList where status equals to UPDATED_STATUS
        defaultChamadoShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllChamadosByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        chamadoRepository.saveAndFlush(chamado);

        // Get all the chamadoList where status is not null
        defaultChamadoShouldBeFound("status.specified=true");

        // Get all the chamadoList where status is null
        defaultChamadoShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllChamadosByPrioridadeIsEqualToSomething() throws Exception {
        // Initialize the database
        chamadoRepository.saveAndFlush(chamado);

        // Get all the chamadoList where prioridade equals to DEFAULT_PRIORIDADE
        defaultChamadoShouldBeFound("prioridade.equals=" + DEFAULT_PRIORIDADE);

        // Get all the chamadoList where prioridade equals to UPDATED_PRIORIDADE
        defaultChamadoShouldNotBeFound("prioridade.equals=" + UPDATED_PRIORIDADE);
    }

    @Test
    @Transactional
    void getAllChamadosByPrioridadeIsInShouldWork() throws Exception {
        // Initialize the database
        chamadoRepository.saveAndFlush(chamado);

        // Get all the chamadoList where prioridade in DEFAULT_PRIORIDADE or UPDATED_PRIORIDADE
        defaultChamadoShouldBeFound("prioridade.in=" + DEFAULT_PRIORIDADE + "," + UPDATED_PRIORIDADE);

        // Get all the chamadoList where prioridade equals to UPDATED_PRIORIDADE
        defaultChamadoShouldNotBeFound("prioridade.in=" + UPDATED_PRIORIDADE);
    }

    @Test
    @Transactional
    void getAllChamadosByPrioridadeIsNullOrNotNull() throws Exception {
        // Initialize the database
        chamadoRepository.saveAndFlush(chamado);

        // Get all the chamadoList where prioridade is not null
        defaultChamadoShouldBeFound("prioridade.specified=true");

        // Get all the chamadoList where prioridade is null
        defaultChamadoShouldNotBeFound("prioridade.specified=false");
    }

    @Test
    @Transactional
    void getAllChamadosByDataAberturaIsEqualToSomething() throws Exception {
        // Initialize the database
        chamadoRepository.saveAndFlush(chamado);

        // Get all the chamadoList where dataAbertura equals to DEFAULT_DATA_ABERTURA
        defaultChamadoShouldBeFound("dataAbertura.equals=" + DEFAULT_DATA_ABERTURA);

        // Get all the chamadoList where dataAbertura equals to UPDATED_DATA_ABERTURA
        defaultChamadoShouldNotBeFound("dataAbertura.equals=" + UPDATED_DATA_ABERTURA);
    }

    @Test
    @Transactional
    void getAllChamadosByDataAberturaIsInShouldWork() throws Exception {
        // Initialize the database
        chamadoRepository.saveAndFlush(chamado);

        // Get all the chamadoList where dataAbertura in DEFAULT_DATA_ABERTURA or UPDATED_DATA_ABERTURA
        defaultChamadoShouldBeFound("dataAbertura.in=" + DEFAULT_DATA_ABERTURA + "," + UPDATED_DATA_ABERTURA);

        // Get all the chamadoList where dataAbertura equals to UPDATED_DATA_ABERTURA
        defaultChamadoShouldNotBeFound("dataAbertura.in=" + UPDATED_DATA_ABERTURA);
    }

    @Test
    @Transactional
    void getAllChamadosByDataAberturaIsNullOrNotNull() throws Exception {
        // Initialize the database
        chamadoRepository.saveAndFlush(chamado);

        // Get all the chamadoList where dataAbertura is not null
        defaultChamadoShouldBeFound("dataAbertura.specified=true");

        // Get all the chamadoList where dataAbertura is null
        defaultChamadoShouldNotBeFound("dataAbertura.specified=false");
    }

    @Test
    @Transactional
    void getAllChamadosByDataFechamentoIsEqualToSomething() throws Exception {
        // Initialize the database
        chamadoRepository.saveAndFlush(chamado);

        // Get all the chamadoList where dataFechamento equals to DEFAULT_DATA_FECHAMENTO
        defaultChamadoShouldBeFound("dataFechamento.equals=" + DEFAULT_DATA_FECHAMENTO);

        // Get all the chamadoList where dataFechamento equals to UPDATED_DATA_FECHAMENTO
        defaultChamadoShouldNotBeFound("dataFechamento.equals=" + UPDATED_DATA_FECHAMENTO);
    }

    @Test
    @Transactional
    void getAllChamadosByDataFechamentoIsInShouldWork() throws Exception {
        // Initialize the database
        chamadoRepository.saveAndFlush(chamado);

        // Get all the chamadoList where dataFechamento in DEFAULT_DATA_FECHAMENTO or UPDATED_DATA_FECHAMENTO
        defaultChamadoShouldBeFound("dataFechamento.in=" + DEFAULT_DATA_FECHAMENTO + "," + UPDATED_DATA_FECHAMENTO);

        // Get all the chamadoList where dataFechamento equals to UPDATED_DATA_FECHAMENTO
        defaultChamadoShouldNotBeFound("dataFechamento.in=" + UPDATED_DATA_FECHAMENTO);
    }

    @Test
    @Transactional
    void getAllChamadosByDataFechamentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        chamadoRepository.saveAndFlush(chamado);

        // Get all the chamadoList where dataFechamento is not null
        defaultChamadoShouldBeFound("dataFechamento.specified=true");

        // Get all the chamadoList where dataFechamento is null
        defaultChamadoShouldNotBeFound("dataFechamento.specified=false");
    }

    @Test
    @Transactional
    void getAllChamadosByValorOrcamentoIsEqualToSomething() throws Exception {
        // Initialize the database
        chamadoRepository.saveAndFlush(chamado);

        // Get all the chamadoList where valorOrcamento equals to DEFAULT_VALOR_ORCAMENTO
        defaultChamadoShouldBeFound("valorOrcamento.equals=" + DEFAULT_VALOR_ORCAMENTO);

        // Get all the chamadoList where valorOrcamento equals to UPDATED_VALOR_ORCAMENTO
        defaultChamadoShouldNotBeFound("valorOrcamento.equals=" + UPDATED_VALOR_ORCAMENTO);
    }

    @Test
    @Transactional
    void getAllChamadosByValorOrcamentoIsInShouldWork() throws Exception {
        // Initialize the database
        chamadoRepository.saveAndFlush(chamado);

        // Get all the chamadoList where valorOrcamento in DEFAULT_VALOR_ORCAMENTO or UPDATED_VALOR_ORCAMENTO
        defaultChamadoShouldBeFound("valorOrcamento.in=" + DEFAULT_VALOR_ORCAMENTO + "," + UPDATED_VALOR_ORCAMENTO);

        // Get all the chamadoList where valorOrcamento equals to UPDATED_VALOR_ORCAMENTO
        defaultChamadoShouldNotBeFound("valorOrcamento.in=" + UPDATED_VALOR_ORCAMENTO);
    }

    @Test
    @Transactional
    void getAllChamadosByValorOrcamentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        chamadoRepository.saveAndFlush(chamado);

        // Get all the chamadoList where valorOrcamento is not null
        defaultChamadoShouldBeFound("valorOrcamento.specified=true");

        // Get all the chamadoList where valorOrcamento is null
        defaultChamadoShouldNotBeFound("valorOrcamento.specified=false");
    }

    @Test
    @Transactional
    void getAllChamadosByValorOrcamentoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        chamadoRepository.saveAndFlush(chamado);

        // Get all the chamadoList where valorOrcamento is greater than or equal to DEFAULT_VALOR_ORCAMENTO
        defaultChamadoShouldBeFound("valorOrcamento.greaterThanOrEqual=" + DEFAULT_VALOR_ORCAMENTO);

        // Get all the chamadoList where valorOrcamento is greater than or equal to UPDATED_VALOR_ORCAMENTO
        defaultChamadoShouldNotBeFound("valorOrcamento.greaterThanOrEqual=" + UPDATED_VALOR_ORCAMENTO);
    }

    @Test
    @Transactional
    void getAllChamadosByValorOrcamentoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        chamadoRepository.saveAndFlush(chamado);

        // Get all the chamadoList where valorOrcamento is less than or equal to DEFAULT_VALOR_ORCAMENTO
        defaultChamadoShouldBeFound("valorOrcamento.lessThanOrEqual=" + DEFAULT_VALOR_ORCAMENTO);

        // Get all the chamadoList where valorOrcamento is less than or equal to SMALLER_VALOR_ORCAMENTO
        defaultChamadoShouldNotBeFound("valorOrcamento.lessThanOrEqual=" + SMALLER_VALOR_ORCAMENTO);
    }

    @Test
    @Transactional
    void getAllChamadosByValorOrcamentoIsLessThanSomething() throws Exception {
        // Initialize the database
        chamadoRepository.saveAndFlush(chamado);

        // Get all the chamadoList where valorOrcamento is less than DEFAULT_VALOR_ORCAMENTO
        defaultChamadoShouldNotBeFound("valorOrcamento.lessThan=" + DEFAULT_VALOR_ORCAMENTO);

        // Get all the chamadoList where valorOrcamento is less than UPDATED_VALOR_ORCAMENTO
        defaultChamadoShouldBeFound("valorOrcamento.lessThan=" + UPDATED_VALOR_ORCAMENTO);
    }

    @Test
    @Transactional
    void getAllChamadosByValorOrcamentoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        chamadoRepository.saveAndFlush(chamado);

        // Get all the chamadoList where valorOrcamento is greater than DEFAULT_VALOR_ORCAMENTO
        defaultChamadoShouldNotBeFound("valorOrcamento.greaterThan=" + DEFAULT_VALOR_ORCAMENTO);

        // Get all the chamadoList where valorOrcamento is greater than SMALLER_VALOR_ORCAMENTO
        defaultChamadoShouldBeFound("valorOrcamento.greaterThan=" + SMALLER_VALOR_ORCAMENTO);
    }

    @Test
    @Transactional
    void getAllChamadosByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        chamadoRepository.saveAndFlush(chamado);

        // Get all the chamadoList where descricao equals to DEFAULT_DESCRICAO
        defaultChamadoShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the chamadoList where descricao equals to UPDATED_DESCRICAO
        defaultChamadoShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllChamadosByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        chamadoRepository.saveAndFlush(chamado);

        // Get all the chamadoList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultChamadoShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the chamadoList where descricao equals to UPDATED_DESCRICAO
        defaultChamadoShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllChamadosByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        chamadoRepository.saveAndFlush(chamado);

        // Get all the chamadoList where descricao is not null
        defaultChamadoShouldBeFound("descricao.specified=true");

        // Get all the chamadoList where descricao is null
        defaultChamadoShouldNotBeFound("descricao.specified=false");
    }

    @Test
    @Transactional
    void getAllChamadosByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        chamadoRepository.saveAndFlush(chamado);

        // Get all the chamadoList where descricao contains DEFAULT_DESCRICAO
        defaultChamadoShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the chamadoList where descricao contains UPDATED_DESCRICAO
        defaultChamadoShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllChamadosByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        chamadoRepository.saveAndFlush(chamado);

        // Get all the chamadoList where descricao does not contain DEFAULT_DESCRICAO
        defaultChamadoShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the chamadoList where descricao does not contain UPDATED_DESCRICAO
        defaultChamadoShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultChamadoShouldBeFound(String filter) throws Exception {
        restChamadoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chamado.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].prioridade").value(hasItem(DEFAULT_PRIORIDADE.toString())))
            .andExpect(jsonPath("$.[*].dataAbertura").value(hasItem(DEFAULT_DATA_ABERTURA.toString())))
            .andExpect(jsonPath("$.[*].dataFechamento").value(hasItem(DEFAULT_DATA_FECHAMENTO.toString())))
            .andExpect(jsonPath("$.[*].valorOrcamento").value(hasItem(sameNumber(DEFAULT_VALOR_ORCAMENTO))))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));

        // Check, that the count call also returns 1
        restChamadoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultChamadoShouldNotBeFound(String filter) throws Exception {
        restChamadoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restChamadoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingChamado() throws Exception {
        // Get the chamado
        restChamadoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingChamado() throws Exception {
        // Initialize the database
        chamadoRepository.saveAndFlush(chamado);

        int databaseSizeBeforeUpdate = chamadoRepository.findAll().size();

        // Update the chamado
        Chamado updatedChamado = chamadoRepository.findById(chamado.getId()).get();
        // Disconnect from session so that the updates on updatedChamado are not directly saved in db
        em.detach(updatedChamado);
        updatedChamado
            .titulo(UPDATED_TITULO)
            .status(UPDATED_STATUS)
            .prioridade(UPDATED_PRIORIDADE)
            .dataAbertura(UPDATED_DATA_ABERTURA)
            .dataFechamento(UPDATED_DATA_FECHAMENTO)
            .valorOrcamento(UPDATED_VALOR_ORCAMENTO)
            .descricao(UPDATED_DESCRICAO);
        ChamadoDTO chamadoDTO = chamadoMapper.toDto(updatedChamado);

        restChamadoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, chamadoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chamadoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Chamado in the database
        List<Chamado> chamadoList = chamadoRepository.findAll();
        assertThat(chamadoList).hasSize(databaseSizeBeforeUpdate);
        Chamado testChamado = chamadoList.get(chamadoList.size() - 1);
        assertThat(testChamado.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testChamado.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testChamado.getPrioridade()).isEqualTo(UPDATED_PRIORIDADE);
        assertThat(testChamado.getDataAbertura()).isEqualTo(UPDATED_DATA_ABERTURA);
        assertThat(testChamado.getDataFechamento()).isEqualTo(UPDATED_DATA_FECHAMENTO);
        assertThat(testChamado.getValorOrcamento()).isEqualByComparingTo(UPDATED_VALOR_ORCAMENTO);
        assertThat(testChamado.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void putNonExistingChamado() throws Exception {
        int databaseSizeBeforeUpdate = chamadoRepository.findAll().size();
        chamado.setId(count.incrementAndGet());

        // Create the Chamado
        ChamadoDTO chamadoDTO = chamadoMapper.toDto(chamado);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChamadoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, chamadoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chamadoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Chamado in the database
        List<Chamado> chamadoList = chamadoRepository.findAll();
        assertThat(chamadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchChamado() throws Exception {
        int databaseSizeBeforeUpdate = chamadoRepository.findAll().size();
        chamado.setId(count.incrementAndGet());

        // Create the Chamado
        ChamadoDTO chamadoDTO = chamadoMapper.toDto(chamado);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChamadoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chamadoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Chamado in the database
        List<Chamado> chamadoList = chamadoRepository.findAll();
        assertThat(chamadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamChamado() throws Exception {
        int databaseSizeBeforeUpdate = chamadoRepository.findAll().size();
        chamado.setId(count.incrementAndGet());

        // Create the Chamado
        ChamadoDTO chamadoDTO = chamadoMapper.toDto(chamado);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChamadoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chamadoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Chamado in the database
        List<Chamado> chamadoList = chamadoRepository.findAll();
        assertThat(chamadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateChamadoWithPatch() throws Exception {
        // Initialize the database
        chamadoRepository.saveAndFlush(chamado);

        int databaseSizeBeforeUpdate = chamadoRepository.findAll().size();

        // Update the chamado using partial update
        Chamado partialUpdatedChamado = new Chamado();
        partialUpdatedChamado.setId(chamado.getId());

        partialUpdatedChamado
            .titulo(UPDATED_TITULO)
            .status(UPDATED_STATUS)
            .prioridade(UPDATED_PRIORIDADE)
            .dataAbertura(UPDATED_DATA_ABERTURA)
            .dataFechamento(UPDATED_DATA_FECHAMENTO)
            .valorOrcamento(UPDATED_VALOR_ORCAMENTO);

        restChamadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChamado.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChamado))
            )
            .andExpect(status().isOk());

        // Validate the Chamado in the database
        List<Chamado> chamadoList = chamadoRepository.findAll();
        assertThat(chamadoList).hasSize(databaseSizeBeforeUpdate);
        Chamado testChamado = chamadoList.get(chamadoList.size() - 1);
        assertThat(testChamado.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testChamado.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testChamado.getPrioridade()).isEqualTo(UPDATED_PRIORIDADE);
        assertThat(testChamado.getDataAbertura()).isEqualTo(UPDATED_DATA_ABERTURA);
        assertThat(testChamado.getDataFechamento()).isEqualTo(UPDATED_DATA_FECHAMENTO);
        assertThat(testChamado.getValorOrcamento()).isEqualByComparingTo(UPDATED_VALOR_ORCAMENTO);
        assertThat(testChamado.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    void fullUpdateChamadoWithPatch() throws Exception {
        // Initialize the database
        chamadoRepository.saveAndFlush(chamado);

        int databaseSizeBeforeUpdate = chamadoRepository.findAll().size();

        // Update the chamado using partial update
        Chamado partialUpdatedChamado = new Chamado();
        partialUpdatedChamado.setId(chamado.getId());

        partialUpdatedChamado
            .titulo(UPDATED_TITULO)
            .status(UPDATED_STATUS)
            .prioridade(UPDATED_PRIORIDADE)
            .dataAbertura(UPDATED_DATA_ABERTURA)
            .dataFechamento(UPDATED_DATA_FECHAMENTO)
            .valorOrcamento(UPDATED_VALOR_ORCAMENTO)
            .descricao(UPDATED_DESCRICAO);

        restChamadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChamado.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChamado))
            )
            .andExpect(status().isOk());

        // Validate the Chamado in the database
        List<Chamado> chamadoList = chamadoRepository.findAll();
        assertThat(chamadoList).hasSize(databaseSizeBeforeUpdate);
        Chamado testChamado = chamadoList.get(chamadoList.size() - 1);
        assertThat(testChamado.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testChamado.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testChamado.getPrioridade()).isEqualTo(UPDATED_PRIORIDADE);
        assertThat(testChamado.getDataAbertura()).isEqualTo(UPDATED_DATA_ABERTURA);
        assertThat(testChamado.getDataFechamento()).isEqualTo(UPDATED_DATA_FECHAMENTO);
        assertThat(testChamado.getValorOrcamento()).isEqualByComparingTo(UPDATED_VALOR_ORCAMENTO);
        assertThat(testChamado.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void patchNonExistingChamado() throws Exception {
        int databaseSizeBeforeUpdate = chamadoRepository.findAll().size();
        chamado.setId(count.incrementAndGet());

        // Create the Chamado
        ChamadoDTO chamadoDTO = chamadoMapper.toDto(chamado);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChamadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, chamadoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(chamadoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Chamado in the database
        List<Chamado> chamadoList = chamadoRepository.findAll();
        assertThat(chamadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchChamado() throws Exception {
        int databaseSizeBeforeUpdate = chamadoRepository.findAll().size();
        chamado.setId(count.incrementAndGet());

        // Create the Chamado
        ChamadoDTO chamadoDTO = chamadoMapper.toDto(chamado);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChamadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(chamadoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Chamado in the database
        List<Chamado> chamadoList = chamadoRepository.findAll();
        assertThat(chamadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamChamado() throws Exception {
        int databaseSizeBeforeUpdate = chamadoRepository.findAll().size();
        chamado.setId(count.incrementAndGet());

        // Create the Chamado
        ChamadoDTO chamadoDTO = chamadoMapper.toDto(chamado);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChamadoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(chamadoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Chamado in the database
        List<Chamado> chamadoList = chamadoRepository.findAll();
        assertThat(chamadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteChamado() throws Exception {
        // Initialize the database
        chamadoRepository.saveAndFlush(chamado);

        int databaseSizeBeforeDelete = chamadoRepository.findAll().size();

        // Delete the chamado
        restChamadoMockMvc
            .perform(delete(ENTITY_API_URL_ID, chamado.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Chamado> chamadoList = chamadoRepository.findAll();
        assertThat(chamadoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
