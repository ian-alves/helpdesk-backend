package com.ian.helpdesk.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ian.helpdesk.domain.Pessoa;
import com.ian.helpdesk.repositories.PessoaRepository;
import com.ian.helpdesk.security.UserSS;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    PessoaRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Pessoa> user = repository.findByEmail(email);
        if (user.isPresent()) {
            return new UserSS(user.get().getId(), user.get().getSenha(), user.get().getEmail(), user.get().getPerfis());
        }

        throw new UsernameNotFoundException(email);
    }

}
