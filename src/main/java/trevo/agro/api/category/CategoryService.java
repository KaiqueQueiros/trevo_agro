package trevo.agro.api.category;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import trevo.agro.api.repository.CategoryRepository;
import trevo.agro.api.utils.ResponseModel;
import trevo.agro.api.utils.ResponseModelEspec;
import trevo.agro.api.utils.ResponseModelEspecNoObject;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public ResponseEntity<ResponseModel> register(@RequestBody @Valid CategoryDTO dto) {
        if (categoryRepository.existsByName(dto.getName())) {
            return new ResponseEntity<>(new ResponseModelEspecNoObject("Categoria já existe!"), HttpStatus.BAD_REQUEST);
        }
        if (dto.setName() == "") {
            return new ResponseEntity<>(new ResponseModelEspecNoObject("Informe o nome da categoria!"), HttpStatus.BAD_REQUEST);
        }
        Category category = new Category(dto);
        categoryRepository.save(category);
        return new ResponseEntity<>(new ResponseModelEspec("Categoria cadastrada!", dto), HttpStatus.OK);
    }


    public ResponseEntity<ResponseModel> list() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            return new ResponseEntity<>(new ResponseModelEspecNoObject("Não existem categorias cadastradas"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ResponseModelEspec("Lista de categorias", categories), HttpStatus.OK);
    }

    public ResponseEntity<ResponseModel> delete(@PathVariable Long id) {
        try {
            Optional<Category> category = categoryRepository.findById(id);
            if (category.isEmpty()) {
                return new ResponseEntity<>(new ResponseModelEspecNoObject("Categoria não encontrada!"), HttpStatus.NOT_FOUND);
            }
            categoryRepository.deleteById(id);
            return new ResponseEntity<>(new ResponseModelEspecNoObject("Categoria excluida!"), HttpStatus.OK);
        } catch (Error error) {
            error.printStackTrace();
        }
        return ResponseEntity.internalServerError().build();
    }

    public ResponseEntity<ResponseModel> update(@Valid CategoryDTO dto, @PathVariable Long id) {
        try {
            Category categories = categoryRepository.findById(id).orElse(null);
            if (categories == null || dto.getName() == null) {
                return new ResponseEntity<>(new ResponseModelEspecNoObject("Categoria inexistente ou parametros invalidos!"), HttpStatus.NOT_FOUND);
            }
            if (categoryRepository.existsByName(dto.getName())) {
                return new ResponseEntity<>(new ResponseModelEspecNoObject("Nome já existe!"), HttpStatus.BAD_REQUEST);
            }
            categories.update(dto);
            categoryRepository.save(categories);
            return new ResponseEntity<>(new ResponseModelEspecNoObject("Categoria foi atualizada!"), HttpStatus.OK);
        } catch (Error error) {
            error.printStackTrace();
        }
        return ResponseEntity.internalServerError().build();
    }
}
