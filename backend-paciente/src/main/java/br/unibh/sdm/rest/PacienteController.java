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

import br.unibh.sdm.entidade.Paciente;
import br.unibh.sdm.negocio.PacienteService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.NotNull;

/**
 * Classe contendo as definicoes de servicos REST/JSON para Cliente
 *
 * @author ana clara
 *
 */

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "paciente")
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @GetMapping(value = "")
    public List<Paciente> getPaciente() {
        return pacienteService.getPaciente();
    }

    @GetMapping(value = "{id}")
    public Object getPacienteById(@PathVariable @NotNull String id) throws Exception {
        if (pacienteService.pacienteExists(id)) {
            return pacienteService.getPacienteById(id);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Collections.singletonMap("errorMessage", "Objeto não encontrado"));
        }
    }

    @SuppressWarnings("null")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object createPaciente(@RequestBody @NotNull Paciente paciente) throws Exception {
        try {
            return pacienteService.savePaciente(paciente);
        } catch (TransactionSystemException e) {
            if (e.getRootCause() instanceof ConstraintViolationException) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        Collections.singletonMap("errorMessage", e.getRootCause().getMessage()));
            } else {
                throw e;
            }
        }
    }

    @SuppressWarnings("null")
    @PutMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object updatePaciente(@PathVariable String id,
            @RequestBody @NotNull Paciente pacienteNovo) throws Exception {
        
        if (pacienteService.pacienteExists(id)) {
            try {
                Paciente paciente =  pacienteService.getPacienteById(id);
                
                paciente.setNome(pacienteNovo.getNome());
                paciente.setEmail(pacienteNovo.getEmail());
                paciente.setTelefone(pacienteNovo.getTelefone());
                paciente.setDataAtualizacao(pacienteNovo.getDataAtualizacao());
                paciente.setSenha(pacienteNovo.getSenha());

                return pacienteService.savePaciente(paciente);
            } catch (TransactionSystemException e) {
                if (e.getRootCause() instanceof ConstraintViolationException) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                            Collections.singletonMap("errorMessage", e.getRootCause().getMessage()));
                } else {
                    throw e;
                }
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Collections.singletonMap("errorMessage", "Objeto não encontrado para alteração"));
        }
    }

    @DeleteMapping(value = "{id}")
    public Object deletePaciente(@PathVariable String id) throws Exception {

        if (pacienteService.pacienteExists(id)) {
            pacienteService.deletePaciente(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                    Collections.singletonMap("message", "Objeto excluído com sucesso"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Collections.singletonMap("errorMessage", "Objeto não encontrado para exclusão"));
        }
    }

}
