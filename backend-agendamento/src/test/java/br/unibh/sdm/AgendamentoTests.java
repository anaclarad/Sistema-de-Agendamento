package br.unibh.sdm;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.unibh.sdm.entidade.Agendamento;
import br.unibh.sdm.persistencia.AgendamentoRepository;



/**
 * Classe de testes para a entidade Agendamento.
 *  <br>
 * Para rodar, antes sete a seguinte variavel de ambiente: -Dspring.config.location=C:/Users/jhcru/sdm/backend-Agendamento/
 *  <br>
 * Neste diretoio, criar um arquivo application.properties contendo as seguitnes variaveis:
 * <br>
 * # Connection parameters<br>
 * spring.datasource.url= jdbc:postgresql://HOST:5432/DBNAME<br>
 * spring.datasource.username=USER<br>
 * spring.datasource.password=PASSWORD<br>
 * <br>
 * spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true<br>
 * spring.jpa.properties.hibernate.dialect= org.hibernate.dialect.PostgreSQLDialect<br>
 * <br>
 * # Hibernate ddl auto (create, create-drop, validate, update)<br>
 * spring.jpa.hibernate.ddl-auto=create<br>
 * @author jhcru
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AgendamentoTests {

    private static Logger LOGGER = LoggerFactory.getLogger(AgendamentoTests.class);
    
	@Autowired
	private AgendamentoRepository repository;

    @Test
    public void teste1Criacao() throws ParseException {
        LOGGER.info("Criando objetos...");
        Agendamento c1 = new Agendamento(1, "João Silva", "Dra. Maria", "02/02/2020", "Cardiologia");
        repository.save(c1);

        Agendamento c2 = new Agendamento(2, "Carlos Souza", "Dr. Pedro", "03/03/2020", "Dermatologia");
        repository.save(c2);

        Agendamento c3 = new Agendamento(3, "Fernanda Lima", "Dra. Ana", "04/04/2020", "Pediatria");
        repository.save(c3);

        LOGGER.info("Pesquisado todos");
        Iterable<Agendamento> lista = repository.findAll();
        assertTrue(lista.iterator().hasNext());
        for (Agendamento agendamento : lista) {
            LOGGER.info(agendamento.toString());
        }

        LOGGER.info("Pesquisando um objeto");
        List<Agendamento> result = repository.findByNomePaciente("Carlos Souza");
        assertEquals(1, result.size());
        LOGGER.info("Encontrado: {}", result.size());
    }

    @Test
    public void teste2Exclusao() throws ParseException {
        LOGGER.info("Excluindo objetos...");
        List<Agendamento> result = repository.findByNomePaciente("Carlos Souza");
        for (Agendamento agendamento : result) {
            LOGGER.info("Excluindo paciente id = " + agendamento.getId());
            repository.delete(agendamento);
        }
        result = repository.findByNomePaciente("Carlos Souza");
        assertEquals(0, result.size());
        LOGGER.info("Exclusão feita com sucesso");
    }
}
