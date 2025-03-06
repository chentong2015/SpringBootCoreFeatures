package swagger.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

public class Product {

    @Schema(name = "Product ID", example = "1", requiredMode = REQUIRED)
    private long id;

    @NotBlank
    @Size(min = 0, max = 10)
    private String name;

    @NotBlank
    @Size(min = 0, max = 30)
    private String detail;

    public Product(long id, String name, String detail) {
        this.id = id;
        this.name = name;
        this.detail = detail;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}