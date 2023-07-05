package br.com.davefernandes.jh.helpdesk.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.davefernandes.jh.helpdesk.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ChamadoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Chamado.class);
        Chamado chamado1 = new Chamado();
        chamado1.setId(1L);
        Chamado chamado2 = new Chamado();
        chamado2.setId(chamado1.getId());
        assertThat(chamado1).isEqualTo(chamado2);
        chamado2.setId(2L);
        assertThat(chamado1).isNotEqualTo(chamado2);
        chamado1.setId(null);
        assertThat(chamado1).isNotEqualTo(chamado2);
    }
}
