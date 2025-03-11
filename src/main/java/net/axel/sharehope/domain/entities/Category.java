package net.axel.sharehope.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.axel.sharehope.domain.dtos.category.CategoryRequestDTO;

import java.util.ArrayList;
import java.util.List;

import static net.axel.sharehope.util.UpdateUtils.updateField;

@Entity
@Table(name = "categories")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String categoryName;

    private String description;

    @Transient
    private String icon;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Action> actions = new ArrayList<>();

    public static Category createCategory(String categoryName, String description) {
        Category category = new Category();
        category.categoryName = categoryName;
        category.description  = description;
        return category;
    }

    public void upadteCategory(CategoryRequestDTO requestDTO) {
        updateField(requestDTO.categoryName(), this.categoryName, newValue -> this.categoryName = newValue);
        updateField(requestDTO.description(), this.description, newValue -> this.description = newValue);
    }
}
