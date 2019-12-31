package idealab.api.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import idealab.api.model.ColorType;

public interface ColorTypeRepo extends CrudRepository<ColorType, Integer> {
   ColorType findByColor(String color);
   List<ColorType> findByAvailable(Boolean available);
   ColorType findColorTypeById(Integer id);
   List<ColorType> findAllByOrderByIdAsc();
}
