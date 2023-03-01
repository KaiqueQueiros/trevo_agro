package trevo.agro.api.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;


public record ProductDTO(
        @NotNull(message = "Nome obrigatorio")
        String name,
        @NotBlank(message = "Insira a descrição do produto")
        String description,
        @NotBlank(message = "É necessario informar neste campo o tamanho da area suportada pelo produto!")
        String area_size,
        String img,
        @JsonProperty("categories")
        List<Long> categoryIds,
        @JsonProperty("cultures")
        List<Long> cultureIds
) {


    public String getName() {
        return name;
    }

}


