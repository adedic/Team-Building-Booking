package hr.tvz.java.teambuildingbooking.mapper;

import hr.tvz.java.teambuildingbooking.model.Offer;
import hr.tvz.java.teambuildingbooking.model.form.NewOfferForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OfferMapper {

    OfferMapper INSTANCE = Mappers.getMapper(OfferMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "dateAdded", ignore = true)
    @Mapping(target = "availableFrom", ignore = true)
    @Mapping(target = "availableTo", ignore = true)
    Offer newOfferFormToUser(NewOfferForm newOfferForm);

}