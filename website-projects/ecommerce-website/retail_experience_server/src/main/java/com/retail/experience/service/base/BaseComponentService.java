package com.retail.experience.service.base;

import com.retail.experience.exception.RepositoryManipulationException;
import com.retail.experience.model.Component;
import com.retail.experience.repositories.BaseJpaRepository;

import java.util.List;
import java.util.Optional;

public abstract class BaseComponentService<T extends Component> implements BaseService {

    private BaseJpaRepository<T> repository;

    protected void setRepository(BaseJpaRepository<T> repository) {
        this.repository = repository;
    }

    protected long countComponents() throws RepositoryManipulationException {
        try {
            return repository.count();
        } catch (Exception exception) {
            throw new RepositoryManipulationException(exception.getMessage());
        }
    }

    protected T getComponentById(long id) throws RepositoryManipulationException {
        try {
            return repository.getById(id);
        } catch (Exception exception) {
            throw new RepositoryManipulationException(exception.getMessage());
        }
    }

    protected T searchComponentByName(String name) throws RepositoryManipulationException {
        try {
            return repository.findByName(name);
        } catch (Exception exception) {
            throw new RepositoryManipulationException(exception.getMessage());
        }
    }

    public List<T> getAllComponents() throws RepositoryManipulationException {
        try {
            return repository.findAll();
        } catch (Exception exception) {
            throw new RepositoryManipulationException(exception.getMessage());
        }
    }

    public T loadComponent(T newComponent) throws RepositoryManipulationException {
        try {
            T existingComponent = repository.findByComponentId(newComponent.getComponentId());
            if (existingComponent != null) {
                repository.restoreStock(existingComponent.getId(), newComponent.getQuantity());
                existingComponent.setQuantity(existingComponent.getQuantity() + newComponent.getQuantity());
                return existingComponent;
            } else {
                return repository.saveAndFlush(newComponent);
            }
        } catch (Exception exception) {
            throw new RepositoryManipulationException(exception.getMessage());
        }
    }

    @Override
    public void reduceStockById(long id, int quantity) throws RepositoryManipulationException {
        try {
            Optional<T> component = repository.findById(id);
            if (component.isPresent() && component.get().getQuantity() >= quantity) {
                repository.reduceStock(id, quantity);
            } else {
                throw new RepositoryManipulationException(generateExceptionMessage(id));
            }
        } catch (Exception exception) {
            throw new RepositoryManipulationException(exception.getMessage());
        }
    }

    @Override
    public void restoreStockById(long id, int quantity) throws RepositoryManipulationException {
        try {
            Optional<T> component = repository.findById(id);
            if (component.isPresent()) {
                repository.restoreStock(id, quantity);
            } else {
                throw new RepositoryManipulationException(generateExceptionMessage(id));
            }
        } catch (Exception exception) {
            throw new RepositoryManipulationException(exception.getMessage());
        }
    }

    private String generateExceptionMessage(long id) {
        String className = repository.getClass().getName();
        return className + " manipulates stock failed for table id: " + id;
    }
}
