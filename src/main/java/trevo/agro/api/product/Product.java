package trevo.agro.api.product;

import jakarta.persistence.*;
import lombok.*;
import trevo.agro.api.category.Category;
import trevo.agro.api.culture.Culture;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_product")
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name",unique = true)
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "area_size")
    private String area_size;
    @Column(name = "date")
    LocalDate date;
    @Column(name = "img")
    private URL img;
    @ManyToMany
    @JoinTable
            (
                    name = "TB_PRODUCT_CATEGORY",
                    joinColumns = {@JoinColumn(name = "product_id", referencedColumnName = "id")},
                    inverseJoinColumns = {@JoinColumn(name = "category_id", referencedColumnName = "id")}
            )
    private List<Category> categories;
    @ManyToMany
    @JoinTable(
                    name = "TB_PRODUCT_CULTURE",
                    joinColumns = {@JoinColumn(name = "product_id", referencedColumnName = "id")},
                    inverseJoinColumns = {@JoinColumn(name = "culture_id", referencedColumnName = "id")}

    )
    private List<Culture> cultures;

    public Product(ProductDTO dto, List<Category> categories, List<Culture> cultures) {
        this.name = dto.getName();
        this.area_size = dto.area_size();
        this.description = dto.description();
        this.date = LocalDate.now();
        this.img = dto.img();
        this.categories = categories;
        this.cultures = cultures;

    }
    public void update(ProductDTO dto,List<Category> categories,List<Culture> cultures) {
        if(dto.name() != null) {
            this.name = dto.name();
        }
        if(dto.area_size() != null) {
            this.area_size = dto.area_size();
        }
        if(dto.description() != null) {
            this.description = dto.description();
        }
        if (dto.img() != null){
            this.img = dto.img();
        }
        if (categories != null){
            this.categories = categories;
        }
        if (cultures != null){
            this.cultures = cultures;
        }
    }
}

