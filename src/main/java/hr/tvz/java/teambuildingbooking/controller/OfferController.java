package hr.tvz.java.teambuildingbooking.controller;

import hr.tvz.java.teambuildingbooking.mapper.OfferMapper;
import hr.tvz.java.teambuildingbooking.model.Category;
import hr.tvz.java.teambuildingbooking.model.Offer;
import hr.tvz.java.teambuildingbooking.model.form.EditOfferForm;
import hr.tvz.java.teambuildingbooking.model.form.NewOfferForm;
import hr.tvz.java.teambuildingbooking.service.CategoryService;
import hr.tvz.java.teambuildingbooking.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/offer")
public class OfferController {

    private static final String NEW_OFFER_VIEW_NAME = "offer/new-offer";
    private static final String SEARCH_OFFER_VIEW_NAME = "offer/search-offer";
    private static final String SEARCH_RESULTS_VIEW_NAME = "offer/search-results";
    private static final String DETAILS_VIEW_NAME = "offer/details";
    private static final String REVIEWS_VIEW_NAME = "offer/reviews";
    private static final String EDIT_OFFER_VIEW_NAME = "offer/edit-offer";
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    @Autowired
    private OfferService offerService;

    @Autowired
    private CategoryService categoryService;

    @Secured("PROVIDER")
    @RequestMapping("/new")
    private String newOffer(Model model) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("newOfferForm", new NewOfferForm());
        return NEW_OFFER_VIEW_NAME;
    }

    @Secured("PROVIDER")
    @PostMapping("/new")
    public String handleNewOfferForm(@ModelAttribute("newOfferForm") NewOfferForm form, RedirectAttributes redirectAttributes, BindingResult bindingResult) throws ParseException {
        if (bindingResult.hasErrors()) {
            return NEW_OFFER_VIEW_NAME;
        }
        Offer offer = offerService.createOffer(form);
        redirectAttributes.addFlashAttribute("createSuccess", "Dodavanje nove ponude je uspjelo!");

        return "redirect:/details/" + offer.getId();
    }

    @Secured("PROVIDER")
    @RequestMapping("/edit/{id}")
    private String editOffer(Model model, @PathVariable("id") Long id) {
        Optional<Offer> offer = offerService.findOne(id);

        if (offer.isPresent()) {
            Offer receivedOffer = offer.get();
            EditOfferForm editOfferForm = OfferMapper.INSTANCE.offerToEditOfferForm(receivedOffer);

            if (receivedOffer.getAvailableFrom() != null) {
                DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
                editOfferForm.setAvailableFrom(dateFormat.format(receivedOffer.getAvailableFrom()));
            }

            if (receivedOffer.getAvailableTo() != null) {
                DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
                editOfferForm.setAvailableTo(dateFormat.format(receivedOffer.getAvailableTo()));
            }

            receivedOffer.setDateLastEdited(new Date());

            model.addAttribute("editOfferForm", editOfferForm);

            List<Category> categories = categoryService.findAll();
            model.addAttribute("categories", categories);
        }

        return EDIT_OFFER_VIEW_NAME;
    }

    @Secured("PROVIDER")
    @PostMapping("/edit")
    public String handleEditOfferForm(@Valid @ModelAttribute("editOfferForm") EditOfferForm editOfferForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) throws ParseException {
        if (bindingResult.hasErrors()) {
            return EDIT_OFFER_VIEW_NAME;
        }

        Offer offer = offerService.editOffer(editOfferForm);
        redirectAttributes.addFlashAttribute("editSuccess", "Uređivanje ponude je uspjelo!");

        return "redirect:/offer/details/" + offer.getId();
    }

    @RequestMapping("/search")
    private String searchOffer(Model model) {
        model.addAttribute("categories", categoryService.findAll());
//        model.addAttribute("topOffers", offerService.findTopOffers());
        model.addAttribute("topOffers", offerService.findAll());
        return SEARCH_OFFER_VIEW_NAME;
    }

    @PostMapping("/searchOffer")
    private String findSearchResults(Model model) {
        model.addAttribute("offers", offerService.findAll());
        return SEARCH_RESULTS_VIEW_NAME;
    }

    @RequestMapping("/results")
    private String showResults(Model model) {

        model.addAttribute("offers", offerService.findAll());
        return SEARCH_RESULTS_VIEW_NAME;
    }

    @RequestMapping("/details/{id}")
    private String showDetails(Model model, @PathVariable("id") Long id) {
        Optional<Offer> offer = offerService.findOne(id);
        if (offer.isPresent()) {
            model.addAttribute("offer", offer.get());
        }

        return DETAILS_VIEW_NAME;
    }

    @RequestMapping("/details/{id}/reviews")
    private ModelAndView showReviews(Model model, @PathVariable("id") Long id) {
        Optional<Offer> offer = offerService.findOne(id);
        if (offer.isPresent()) {
            model.addAttribute("feedbacks", offer.get().getFeedbacks());
        }

        return new ModelAndView(REVIEWS_VIEW_NAME);
    }
}
