package idealab.api.repositories;

import org.springframework.data.repository.CrudRepository;

import idealab.api.model.ColorType;

public interface ColorTypeRepo extends CrudRepository<ColorType, Integer> {
    ColorType getColorTypeById(Integerid);
    ColorType save(ColorType colorType);
}