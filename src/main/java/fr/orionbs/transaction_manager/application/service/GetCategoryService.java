package fr.orionbs.transaction_manager.application.service;

import fr.orionbs.transaction_manager.adapter.output.persistence.category.exception.UnknownCategoryPersistenceException;
import fr.orionbs.transaction_manager.application.input.GetCategoryUseCase;
import fr.orionbs.transaction_manager.application.output.SelectCategoryPort;
import fr.orionbs.transaction_manager.domain.exception.UnknownCategoryException;
import fr.orionbs.transaction_manager.domain.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetCategoryService implements GetCategoryUseCase {

    private final SelectCategoryPort selectCategoryPort;

    @Override
    public List<Category> getCategories() {
        return selectCategoryPort
                .selectCategories()
                .stream()
                .sorted(Comparator.comparing(Category::getCategoryEnum))
                .collect(Collectors.toList());
    }

    @Override
    public Category getCategoryById(Integer categoryId) throws UnknownCategoryException {
        try {
            return selectCategoryPort.selectCategoryById(categoryId);
        } catch (UnknownCategoryPersistenceException e) {
            throw new UnknownCategoryException();
        }
    }

}
