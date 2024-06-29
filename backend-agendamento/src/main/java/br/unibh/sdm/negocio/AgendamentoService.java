package br.unibh.sdm.negocio;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.IteratorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.unibh.sdm.entidade.Agendamento;
import br.unibh.sdm.persistencia.AgendamentoRepository;

/**
 * Classe contendo a logica de negocio para Agendamento
 * @author ana clara
 *
 */
@Service
public class AgendamentoService {


   private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final AgendamentoRepository agendamentoRepository;

    public AgendamentoService(AgendamentoRepository agendamentoRepository) {
        this.agendamentoRepository = agendamentoRepository;
    }

    @SuppressWarnings("unused")
    public List<Agendamento> getAgendamentos() {
        if (logger.isInfoEnabled()) {
            logger.info("Buscando todos os objetos");
        }
        Iterable<Agendamento> lista = this.agendamentoRepository.findAll();
        if (lista == null) {
            return new ArrayList<Agendamento>();
        }
        return IteratorUtils.toList(lista.iterator());
    }

    public Agendamento getAgendamentoById(long id) {
        Agendamento agendamento = this.agendamentoRepository.findById(id);
        return agendamento;
    }

    public List<Agendamento> getAgendamentoByNome(String nome) {
        if (logger.isInfoEnabled()) {
            logger.info("Buscando todos os objetos");
        }
        Iterable<Agendamento> lista = this.agendamentoRepository.findByNomePaciente(nome);
        if (lista == null) {
            return new ArrayList<Agendamento>();
        }
        return IteratorUtils.toList(lista.iterator());
    }

    public Agendamento saveAgendamento(Agendamento paciente) {
        if (logger.isInfoEnabled()) {
            logger.info("Salvando Paciente com os detalhes {}", paciente.toString());
        }
        return this.agendamentoRepository.save(paciente);
    }

    public void deleteAgedamento(Agendamento agendamento) {
        if (logger.isInfoEnabled()) {
            logger.info("Excluindo Paciente com id {}", agendamento.getId());
        }
        this.agendamentoRepository.delete(agendamento);
    }
}
