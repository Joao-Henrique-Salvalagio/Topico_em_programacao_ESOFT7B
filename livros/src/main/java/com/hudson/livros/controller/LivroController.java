package com.alison.livros.controller;

        import com.alison.livros.dto.LivroDTO;
        import com.alison.livros.enums.StatusLivro;
        import com.alison.livros.service.LivroService;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.http.ResponseEntity;
        import org.springframework.web.bind.annotation.*;

        import java.util.List;
        import java.util.Optional;

        @RestController
        @RequestMapping("/api/livros")
        public class LivroController {

            @Autowired
            private LivroService livroService;

            @PostMapping
            public ResponseEntity<LivroDTO> createLivro(@RequestBody LivroDTO livroDTO) {
                LivroDTO createdLivro = livroService.createLivro(livroDTO);
                return ResponseEntity.ok(createdLivro);
            }

            @GetMapping
            public ResponseEntity<List<LivroDTO>> getAllLivros() {
                List<LivroDTO> livros = livroService.getAllLivros();
                return ResponseEntity.ok(livros);
            }

            @GetMapping("/status/{status}")
            public ResponseEntity<List<LivroDTO>> getLivrosByStatus(@PathVariable StatusLivro status) {
                List<LivroDTO> livros = livroService.getLivrosByStatus(status);
                return ResponseEntity.ok(livros);
            }

            @GetMapping("/{id}")
            public ResponseEntity<Optional<LivroDTO>> getLivroById(@PathVariable Long id) {
                Optional<LivroDTO> livro = livroService.getLivroById(id);
                return ResponseEntity.ok(livro);
            }

            @PutMapping("/{id}/status")
            public ResponseEntity<LivroDTO> updateLivroStatus(@PathVariable Long id, @RequestBody StatusLivro status) {
                try {
                    LivroDTO updatedLivro = livroService.updateLivroStatus(id, status);
                    return ResponseEntity.ok(updatedLivro);
                } catch (RuntimeException e) {
                    return ResponseEntity.notFound().build();
                }
            }

            @PutMapping("/{id}")
            public ResponseEntity<LivroDTO> updateLivro(@PathVariable Long id, @RequestBody LivroDTO livroDTO) {
                try {
                    LivroDTO updatedLivro = livroService.updateLivro(id, livroDTO);
                    return ResponseEntity.ok(updatedLivro);
                } catch (RuntimeException e) {
                    return ResponseEntity.notFound().build();
                }
            }

            @DeleteMapping("/{id}")
            public ResponseEntity<Void> deleteLivro(@PathVariable Long id) {
                livroService.deleteLivro(id);
                return ResponseEntity.noContent().build();
            }
        }