package br.unibh.sdm.rest;

import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.unibh.sdm.entidade.Agendamento;
import br.unibh.sdm.negocio.AgendamentoService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.NotNull;

/**
 * Classe contendo as definicoes de servicos REST/JSON para Cliente
 *
 * @author ana clara
 *
 */
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "agendamento")
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    public AgendamentoController(AgendamentoService agendamentoService) {
        this.agendamentoService = agendamentoService;
    }

    @GetMapping(value = "")
    public List<Agendamento> obterAgendamentos() {
        return this.agendamentoService.getAgendamentos();
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<Agendamento> obterAgendamentoPorId(@PathVariable @NotNull long id) throws Exception {
        Agendamento agendamento = this.agendamentoService.getAgendamentoById(id);
        if (agendamento != null) {
            return ResponseEntity.ok(agendamento);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @SuppressWarnings("null")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object criarAgendamento(@RequestBody @NotNull Agendamento agendamento) throws Exception {
        try {
            agendamentoService.saveAgendamento(agendamento);
            return ResponseEntity.status(HttpStatus.CREATED).body(agendamento);
        } catch (TransactionSystemException e) {
            if (e.getRootCause() instanceof ConstraintViolationException) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        Collections.singletonMap("errorMessage", e.getRootCause().getMessage()));
            } else {
                throw e;
            }
        }
    }

    @PutMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Agendamento> atualizarAgendamento(@PathVariable long id,
            @RequestBody @NotNull Agendamento agendamentoNovo) throws Exception {

        Agendamento agendamento = this.agendamentoService.getAgendamentoById(id);
        if (agendamento != null) {
                agendamento.setNomePaciente(agendamentoNovo.getNomePaciente());
                agendamento.setNomeMedico(agendamentoNovo.getNomeMedico());
                agendamento.setDataConsulta(agendamentoNovo.getDataConsulta());
                agendamento.setEspecialidadeMedica(agendamentoNovo.getEspecialidadeMedica());

                agendamentoService.saveAgendamento(agendamento);

                return ResponseEntity.noContent().build();
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "{id}")
    public Object deletarAgendamento(@PathVariable long id) throws Exception {

        Agendamento agendamento = this.agendamentoService.getAgendamentoById(id);
        if (agendamento != null) {
            agendamentoService.deleteAgedamento(agendamento);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                    Collections.singletonMap("message", "Objeto excluído com sucesso"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Collections.singletonMap("errorMessage", "Objeto não encontrado para exclusão"));
        }
    }
}
