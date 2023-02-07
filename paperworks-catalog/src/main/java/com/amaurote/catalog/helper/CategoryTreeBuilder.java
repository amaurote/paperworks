package com.amaurote.catalog.helper;

import com.amaurote.domain.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryTreeBuilder {

    private String spacer = "\t";
    private Category rootParent = null;
    private List<Category> allCategories = Collections.emptyList();

    public CategoryTreeBuilder(List<Category> allCategories) {
        this.allCategories = allCategories;
    }

    public String build() {
        var sb = new StringBuffer();
        var rootCategories = allCategories.stream().filter(category -> category.getParent() == rootParent).toList();
        for (var root : rootCategories) {
            growBranches(sb, root, 0);
            sb.append("\n");
        }

        return sb.toString();
    }

    private void growBranches(StringBuffer sb, Category parent, int depth) {
        sb.append(spacer.repeat(Math.max(0, depth)))
                .append(parent.getName())
                .append("\n");

        var children = allCategories.stream().filter(category -> category.getParent() == parent).toList();
        if (children.size() != 0) {
            for (Category child : children) {
                growBranches(sb, child, depth + 1);
            }
        }
    }
}
