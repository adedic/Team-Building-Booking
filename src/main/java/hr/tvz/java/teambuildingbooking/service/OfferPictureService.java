package hr.tvz.java.teambuildingbooking.service;

import hr.tvz.java.teambuildingbooking.model.OfferPicture;

public interface OfferPictureService {

    OfferPicture save(OfferPicture offerPicture);

    OfferPicture findById(Long id);

    void deleteById(Long id);
}
