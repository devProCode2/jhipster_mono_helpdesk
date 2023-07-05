package br.com.davefernandes.jh.helpdesk.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ChamadoMapperTest {

    private ChamadoMapper chamadoMapper;

    @BeforeEach
    public void setUp() {
        chamadoMapper = new ChamadoMapperImpl();
    }
}
