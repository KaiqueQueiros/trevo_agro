package trevo.agro.api.category;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import trevo.agro.api.product.Product;
import trevo.agro.api.product.ProductSaveDTO;
import trevo.agro.api.repository.CategoryRepository;
import trevo.agro.api.repository.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import static org.aspectj.bridge.MessageUtil.fail;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CategoryServiceTest {
    @MockBean
    CategoryService categoryService;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ProductRepository productRepository;

    @Test
    public void whenCreateNewCategoryDate() {
        CategoryDTO categoryDTO = new CategoryDTO("Pulverizadores turbos");
        this.categoryService.register(categoryDTO);
        assertThat(categoryDTO.name()).isEqualTo("Pulverizadores turbos");
    }

    @Test
    public void whenDeleteCategoryDate() {
        Category category = new Category(new CategoryDTO("Pulverizadores turbos"));
        this.categoryRepository.save(category);
        this.categoryService.delete(1L);
        assertFalse(categoryRepository.findAll().isEmpty());
    }

    @Test
    public void whenFindNameCategory() {
        Category category = new Category(new CategoryDTO("Pulverizadores turbos"));
        categoryRepository.save(category);
        categoryService.list();
//        assertThat();
    }

    @Test
    public void whenTestDeleteCategoryThatNotExist() {
        categoryService.delete(999L);
        assertTrue(true);
    }

    @Test
    public void testDeleteCategoryWithAssociatedProducts() {
        Category category = new Category(new CategoryDTO("Teste"));
        categoryRepository.save(category);
        List<Long> categoryId = new ArrayList<>();
        categoryId.add(category.getId());
        Product product = new Product(new ProductSaveDTO("Produto 1", "Teste", new ArrayList<>(), categoryId, new ArrayList<>(), new ArrayList<>()));
        productRepository.save(product);
        try {
            categoryRepository.deleteById(category.getId());
            fail("A exce????o de viola????o de restri????o n??o foi lan??ada.");
        } catch (DataIntegrityViolationException e) {
            assertTrue(true);
        }
    }
}
