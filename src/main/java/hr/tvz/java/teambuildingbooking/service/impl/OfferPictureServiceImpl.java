package hr.tvz.java.teambuildingbooking.service.impl;

import hr.tvz.java.teambuildingbooking.model.OfferPicture;
import hr.tvz.java.teambuildingbooking.repository.OfferPictureRepository;
import hr.tvz.java.teambuildingbooking.service.OfferPictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class OfferPictureServiceImpl implements OfferPictureService {

    private OfferPictureRepository offerPictureRepository;

    @Autowired
    public OfferPictureServiceImpl(OfferPictureRepository offerPictureRepository) {
        this.offerPictureRepository = offerPictureRepository;
    }

    @Override
    public OfferPicture save(OfferPicture offerPicture) {
        return offerPictureRepository.save(offerPicture);
    }

    @Override
    public OfferPicture findById(Long id) {
        return offerPictureRepository.getOne(id);
    }

    @Transactional
    @Modifying
    @Override
    public void deleteById(Long id) {
        offerPictureRepository.deleteById(id);
    }
}
