package br.com.davefernandes.jh.helpdesk.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.davefernandes.jh.helpdesk.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ChamadoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChamadoDTO.class);
        ChamadoDTO chamadoDTO1 = new ChamadoDTO();
        chamadoDTO1.setId(1L);
        ChamadoDTO chamadoDTO2 = new ChamadoDTO();
        assertThat(chamadoDTO1).isNotEqualTo(chamadoDTO2);
        chamadoDTO2.setId(chamadoDTO1.getId());
        assertThat(chamadoDTO1).isEqualTo(chamadoDTO2);
        chamadoDTO2.setId(2L);
        assertThat(chamadoDTO1).isNotEqualTo(chamadoDTO2);
        chamadoDTO1.setId(null);
        assertThat(chamadoDTO1).isNotEqualTo(chamadoDTO2);
    }
}
