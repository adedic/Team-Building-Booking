package hr.tvz.java.teambuildingbooking.mapper;

import hr.tvz.java.teambuildingbooking.model.Feedback;
import hr.tvz.java.teambuildingbooking.model.form.NewReviewForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FeedbackMapper {
    FeedbackMapper INSTANCE = Mappers.getMapper(FeedbackMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "offer", ignore = true)
    @Mapping(target = "dateSubmitted")
    @Mapping(target = "dateLastEdited")
    Feedback newReviewFormToReview(NewReviewForm newReviewForm);

}
