package br.unibh.sdm.negocio;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.IteratorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.unibh.sdm.entidade.Paciente;
import br.unibh.sdm.persistencia.PacienteRepository;

/**
 * Classe contendo a logica de negocio para Cliente
 *
 * @author jhcru
 *
 */
@Service
public class PacienteService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final PacienteRepository pacienteRepo;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepo = pacienteRepository;
    }

    @SuppressWarnings("unused")
    public List<Paciente> getPaciente() {
        if (logger.isInfoEnabled()) {
            logger.info("Buscando todos os objetos");
        }
        Iterable<Paciente> lista = this.pacienteRepo.findAll();
        if (lista == null) {
            return new ArrayList<Paciente>();
        }
        return IteratorUtils.toList(lista.iterator());
    }

    public Paciente getPacienteById(String id) {
        Paciente paciente = pacienteRepo.findById(id);
        return paciente;
    }

    public List<Paciente> getPacienteByCpf(String cpf) {
        if (logger.isInfoEnabled()) {
            logger.info("Buscando todos os objetos");
        }
        Iterable<Paciente> lista = this.pacienteRepo.findByCpf(cpf);
        if (lista == null) {
            return new ArrayList<Paciente>();
        }
        return IteratorUtils.toList(lista.iterator());
    }

    public List<Paciente> getPacienteByNome(String nome) {
        if (logger.isInfoEnabled()) {
            logger.info("Buscando todos os objetos");
        }
        Iterable<Paciente> lista = this.pacienteRepo.findByCpf(nome);
        if (lista == null) {
            return new ArrayList<Paciente>();
        }
        return IteratorUtils.toList(lista.iterator());
    }

    public Paciente savePaciente(Paciente paciente) {
        if (logger.isInfoEnabled()) {
            logger.info("Salvando Paciente com os detalhes {}", paciente.toString());
        }
        return this.pacienteRepo.save(paciente);
    }

    public void deletePaciente(String id) {
        if (logger.isInfoEnabled()) {
            logger.info("Excluindo Paciente com id {}", id);
        }
        pacienteRepo.deleteById(id);
    }

    public boolean pacienteExists(String id) {
        if (id.length() != 0) {
            return true;
        } else {
            return false;
        }
    }
}
