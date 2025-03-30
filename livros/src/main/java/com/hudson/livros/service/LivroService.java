package com.alison.livros.service;

import com.alison.livros.domain.Livro;
import com.alison.livros.dto.LivroDTO;
import com.alison.livros.enums.StatusLivro;
import com.alison.livros.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    public List<LivroDTO> getLivrosByStatus(StatusLivro status) {
        return livroRepository.findByStatus(status).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public LivroDTO updateLivroStatus(Long id, StatusLivro status) {
        Optional<Livro> optionalLivro = livroRepository.findById(id);
        if (optionalLivro.isPresent()) {
            Livro livro = optionalLivro.get();
            livro.setStatus(status);
            Livro updatedLivro = livroRepository.save(livro);
            return convertToDTO(updatedLivro);
        } else {
            throw new RuntimeException("Livro não encontrado " + id);
        }
    }

    public LivroDTO createLivro(LivroDTO livroDTO) {
        Livro livro = convertToEntity(livroDTO);
        Livro createdLivro = livroRepository.save(livro);
        return convertToDTO(createdLivro);
    }

    public List<LivroDTO> getAllLivros() {
        return livroRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<LivroDTO> getLivroById(Long id) {
        return livroRepository.findById(id)
                .map(this::convertToDTO);
    }

    public LivroDTO updateLivro(Long id, LivroDTO livroDTO) {
        Optional<Livro> optionalLivro = livroRepository.findById(id);
        if (optionalLivro.isPresent()) {
            Livro livro = optionalLivro.get();
            livro.setNome(livroDTO.getNome());
            livro.setAutor(livroDTO.getAutor());
            Livro updatedLivro = livroRepository.save(livro);
            return convertToDTO(updatedLivro);
        } else {
            throw new RuntimeException("Livro não encontrado " + id);
        }
    }

    public void deleteLivro(Long id) {
        livroRepository.deleteById(id);
    }

    private LivroDTO convertToDTO(Livro livro) {
        LivroDTO livroDTO = new LivroDTO();
        livroDTO.setId(livro.getId());
        livroDTO.setNome(livro.getNome());
        livroDTO.setAutor(livro.getAutor());
        livroDTO.setStatus(livro.getStatus());
        return livroDTO;
    }

    private Livro convertToEntity(LivroDTO livroDTO) {
        Livro livro = new Livro();
        livro.setId(livroDTO.getId());
        livro.setNome(livroDTO.getNome());
        livro.setAutor(livroDTO.getAutor());
        livro.setStatus(livroDTO.getStatus());
        return livro;
    }
}