package hr.tvz.java.teambuildingbooking.controller.rest;

import hr.tvz.java.teambuildingbooking.model.Feedback;
import hr.tvz.java.teambuildingbooking.model.Offer;
import hr.tvz.java.teambuildingbooking.model.User;
import hr.tvz.java.teambuildingbooking.model.form.SearchOfferForm;
import hr.tvz.java.teambuildingbooking.service.OfferService;
import hr.tvz.java.teambuildingbooking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping(path = "rest/offer", produces = "application/json")
@CrossOrigin(origins = "*")
public class OfferRestController {

    private OfferService offerService;
    private UserService userService;

    @Autowired
    public OfferRestController(OfferService offerService, UserService userService) {
        this.offerService = offerService;
        this.userService = userService;
    }

    @GetMapping
    private ResponseEntity<List<Offer>> findAll() {

        List<Offer> offers = offerService.findAll();

        if (offers.isEmpty()) {
            return new ResponseEntity<>(offers, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(offers, HttpStatus.OK);
    }

    @PostMapping("/search")
    private ResponseEntity<List<Offer>> findSearchResults(@RequestBody SearchOfferForm searchOfferForm) {

        List<Offer> offerResults = offerService.findOffers(searchOfferForm);

        if (offerResults.isEmpty()) {
            return new ResponseEntity<>(offerResults, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(offerResults, HttpStatus.OK);
    }

    @GetMapping("/edit/{id}")
    private ResponseEntity<Offer> editOffer(@PathVariable Long id) {
        Optional<Offer> offer = offerService.findOne(id);

        if (offer.isPresent()) {
            return new ResponseEntity<>(offer.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(offer.get(), HttpStatus.NOT_FOUND);
    }

    @GetMapping("/details/{id}")
    private ResponseEntity<Offer> showDetails(@PathVariable Long id) {
        Optional<Offer> offer = offerService.findOne(id);

        if (offer.isPresent()) {
            return new ResponseEntity<>(offer.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(offer.get(), HttpStatus.NOT_FOUND);
    }

    @GetMapping("/details/{id}/reviews")
    private ResponseEntity<Set<Feedback>> showReviews(@PathVariable Long id) {
        Optional<Offer> offer = offerService.findOne(id);

        if (offer.isPresent()) {
            return new ResponseEntity<>(offer.get().getFeedbacks(), HttpStatus.OK);
        }

        return new ResponseEntity<>(offer.get().getFeedbacks(), HttpStatus.NOT_FOUND);
    }

    @GetMapping("/myOffers/{username}")
    private ResponseEntity<List<Offer>> showMyOffers(@PathVariable String username) {

        List<Offer> offers = new ArrayList<>();

        User currentUser = userService.findByUsername(username);

        if (currentUser != null) {
            offers = offerService.findOffersByUserOrderByDateAdded(currentUser);

            if (!offers.isEmpty()) {
                return new ResponseEntity<>(offers, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(offers, HttpStatus.NOT_FOUND);

    }

}
