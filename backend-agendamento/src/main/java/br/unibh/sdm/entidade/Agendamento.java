package br.unibh.sdm.entidade;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "tb_agendamento")
public class Agendamento {

    @Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    @NotBlank
	@Column(length = 100)
	@Size(min = 3, max = 100)
    private String nomePaciente;

    @NotBlank
	@Column(length = 100)
	@Size(min = 3, max = 100)
    private String nomeMedico;

    @NotNull
    private String dataConsulta;


    @NotBlank
	@Column(length = 20)
	@Size(min = 8, max = 20)
    private String especialidadeMedica;


    public Agendamento(){
        super();
    }


    public Agendamento(long id, String nomePaciente, String nomeMedico, String dataConsulta, String especialidadeMedica){
        super();
        this.id = id;
        this.nomePaciente = nomePaciente;
        this.nomeMedico = nomeMedico;
        this.dataConsulta = dataConsulta;
        this.especialidadeMedica = especialidadeMedica;
    }
    

    public Long getId(){
        return this.id;
    }

    public void setId(Long id) {
		this.id = id;
	}


    public String getNomePaciente(){
        return this.nomePaciente;
    }

    public void setNomePaciente(String nomePaciente){
        this.nomePaciente = nomePaciente;
    }

    public String getNomeMedico(){
        return this.nomeMedico;
    }

    public void setNomeMedico(String nomeMedico){
        this.nomeMedico = nomeMedico;
    }

    public String getDataConsulta(){
        return this.dataConsulta;
    }

    public void setDataConsulta(String dataConsulta){
        this.dataConsulta = dataConsulta;
    }

    public String getEspecialidadeMedica(){
        return this.especialidadeMedica;
    }

    public void setEspecialidadeMedica(String especialidadeMedica){
        this.especialidadeMedica = especialidadeMedica;
    }
}
