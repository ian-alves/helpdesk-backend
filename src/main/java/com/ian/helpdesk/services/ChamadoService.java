package com.ian.helpdesk.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ian.helpdesk.domain.Chamado;
import com.ian.helpdesk.domain.Cliente;
import com.ian.helpdesk.domain.Tecnico;
import com.ian.helpdesk.domain.dtos.ChamadoDTO;
import com.ian.helpdesk.domain.enums.Prioridade;
import com.ian.helpdesk.domain.enums.Status;
import com.ian.helpdesk.repositories.ChamadoRepository;
import com.ian.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class ChamadoService {

    @Autowired
    private ChamadoRepository chamadoRepository;

    @Autowired
    private TecnicoService tecnicoService;

    @Autowired
    private ClienteService clienteService;

    public Chamado findById(Integer id) {
        Optional<Chamado> obj = chamadoRepository.findById(id);

        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id));
    }

    public List<Chamado> findAll() {
        return chamadoRepository.findAll();
    }

    public Chamado create(ChamadoDTO objDTO) {
        return chamadoRepository.save(newChamado(objDTO));
    }

    public Chamado update(Integer id, @Valid ChamadoDTO objDTO) {
        objDTO.setId(id);
        Chamado oldObj = findById(id);
        oldObj = newChamado(objDTO);

        return chamadoRepository.save(oldObj);
    }

    private Chamado newChamado(ChamadoDTO objDTO) {
        Tecnico tecnico = tecnicoService.findById(objDTO.getTecnico());
        Cliente cliente = clienteService.findById(objDTO.getCliente());

        Chamado chamado = new Chamado();
        if (objDTO.getId() != null) {
            chamado.setId(objDTO.getId());
        }

        if (objDTO.getStatus().equals(2)) {
            chamado.setDataFechamento(LocalDate.now());
        }

        chamado.setTecnico(tecnico);
        chamado.setCliente(cliente);
        chamado.setPrioridade(Prioridade.toEnum(objDTO.getPrioridade()));
        chamado.setStatus(Status.toEnum(objDTO.getStatus()));
        chamado.setTitulo(objDTO.getTitulo());
        chamado.setObservacoes(objDTO.getObservacoes());

        return chamado;
    }

}
