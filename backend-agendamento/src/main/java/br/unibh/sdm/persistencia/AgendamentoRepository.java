package br.unibh.sdm.persistencia;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.unibh.sdm.entidade.Agendamento;

public interface AgendamentoRepository extends CrudRepository<Agendamento, Long> {
	
	Agendamento findById(long id);
	
	List<Agendamento> findByNomePaciente(String nomePaciente);

	List<Agendamento> findByNomeMedico(String nomeMedico);
}
