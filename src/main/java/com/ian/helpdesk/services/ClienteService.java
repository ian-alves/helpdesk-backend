package com.ian.helpdesk.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ian.helpdesk.domain.Pessoa;
import com.ian.helpdesk.domain.Cliente;
import com.ian.helpdesk.domain.dtos.ClienteDTO;
import com.ian.helpdesk.repositories.PessoaRepository;
import com.ian.helpdesk.repositories.ClienteRepository;
import com.ian.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.ian.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository ClienteRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public Cliente findById(Integer id) {
        Optional<Cliente> obj = ClienteRepository.findById(id);

        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id));
    }

    public List<Cliente> findAll() {
        return ClienteRepository.findAll();
    }

    public Cliente create(ClienteDTO objDTO) {
        objDTO.setId(null);
        objDTO.setSenha(encoder.encode(objDTO.getSenha()));
        validaPorCpfEEmail(objDTO);
        Cliente newObj = new Cliente(objDTO);
        return ClienteRepository.save(newObj);
    }

    public Cliente update(Integer id, @Valid ClienteDTO objDTO) {
        objDTO.setId(id);
        Cliente oldObj = findById(id);
        validaPorCpfEEmail(objDTO);
        oldObj = new Cliente(objDTO);
        return ClienteRepository.save(oldObj);
    }

    public void delete(Integer id) {
        Cliente obj = findById(id);
        if (obj.getChamados().size() > 0) {
            throw new DataIntegrityViolationException("CLiente possui ordens de serviço e não pode ser deletado");
        }

        ClienteRepository.deleteById(id);
    }

    private void validaPorCpfEEmail(ClienteDTO objDTO) {
        Optional<Pessoa> obj = pessoaRepository.findByCpf(objDTO.getCpf());
        if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
            throw new DataIntegrityViolationException("CPF já cadastrado");
        }

        obj = pessoaRepository.findByEmail(objDTO.getEmail());
        if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
            throw new DataIntegrityViolationException("E-mail já cadastrado");
        }
    }

}
