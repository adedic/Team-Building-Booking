package hr.tvz.java.teambuildingbooking.controller;

import hr.tvz.java.teambuildingbooking.facade.OfferFacade;
import hr.tvz.java.teambuildingbooking.model.Category;
import hr.tvz.java.teambuildingbooking.model.Offer;
import hr.tvz.java.teambuildingbooking.model.User;
import hr.tvz.java.teambuildingbooking.model.form.*;
import hr.tvz.java.teambuildingbooking.service.CategoryService;
import hr.tvz.java.teambuildingbooking.service.OfferPictureService;
import hr.tvz.java.teambuildingbooking.service.OfferService;
import hr.tvz.java.teambuildingbooking.service.UserService;
import hr.tvz.java.teambuildingbooking.utils.ReservationUtility;
import hr.tvz.java.teambuildingbooking.utils.UtilityClass;
import hr.tvz.java.teambuildingbooking.validator.EditOfferFormValidator;
import hr.tvz.java.teambuildingbooking.validator.NewOfferFormValidator;
import hr.tvz.java.teambuildingbooking.validator.SearchOfferFormValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/offer")
@SessionAttributes({"offers", "searchOfferForm", "categories", "topOffers"})
public class OfferController {

    // --- view names ---------------------------------------------------------

    private static final String NEW_OFFER_VIEW_NAME = "offer/new-offer";
    private static final String SEARCH_OFFER_VIEW_NAME = "offer/search-offer";
    private static final String DETAILS_VIEW_NAME = "offer/details";
    private static final String EDIT_OFFER_VIEW_NAME = "offer/edit-offer";
    private static final String MY_OFFERS_VIEW_NAME = "offer/myOffers";

    // --- view redirects -----------------------------------------------------

    private static final String OFFER_SEARCH_REDIRECT_NAME = "redirect:/offer/search";
    private static final String OFFER_DETAILS_REDIRECT_NAME = "redirect:/offer/details/";

    // --- model attribute names ----------------------------------------------

    private static final String CATEGORIES_MODEL_ATTRIBUTE_NAME = "categories";
    private static final String OFFER_NOT_FOUND_REDIRECT_ATTRIBUTE = "offerNotFound";
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    // --- autowired components -------------------------------------------------

    private final OfferService offerService;
    private final CategoryService categoryService;
    private final UserService userService;
    private final OfferFacade offerFacade;
    private final NewOfferFormValidator newOfferFormValidator;
    private final EditOfferFormValidator editOfferFormValidator;
    private SearchOfferFormValidator searchOfferFormValidator;

    @Autowired
    ReservationUtility reservationUtility;


    @Autowired
    public OfferController(OfferService offerService, CategoryService categoryService, UserService userService, OfferFacade offerFacade, NewOfferFormValidator newOfferFormValidator, EditOfferFormValidator editOfferFormValidator, OfferPictureService offerPictureService, SearchOfferFormValidator searchOfferFormValidator) {
        this.offerService = offerService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.offerFacade = offerFacade;
        this.newOfferFormValidator = newOfferFormValidator;
        this.editOfferFormValidator = editOfferFormValidator;
        this.searchOfferFormValidator = searchOfferFormValidator;
    }
    @Secured({"PROVIDER, ADMIN"})
    @RequestMapping("/new")
    private String newOffer(Model model) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute(CATEGORIES_MODEL_ATTRIBUTE_NAME, categories);
        model.addAttribute("newOfferForm", new NewOfferForm());
        return NEW_OFFER_VIEW_NAME;
    }

    @Secured({"PROVIDER, ADMIN"})
    @PostMapping("/new")
    public String handleNewOfferForm(@RequestParam("file") MultipartFile file, @Valid @ModelAttribute("newOfferForm") NewOfferForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes, Principal principal, Model model) throws ParseException, IOException {
        if (bindingResult.hasErrors()) {
            model.addAttribute(CATEGORIES_MODEL_ATTRIBUTE_NAME, categoryService.findAll());
            return NEW_OFFER_VIEW_NAME;
        }

        Offer offer = offerService.createOffer(form, UtilityClass.convertByteArrayToBase64String(file.getBytes(), file.getContentType()), file.getName(), (int) file.getSize(), principal.getName());
        redirectAttributes.addFlashAttribute("createSuccess", "Dodavanje nove ponude je uspjelo!");

        createOfferLogMessage(offer.getId());
        return OFFER_DETAILS_REDIRECT_NAME + offer.getId();
    }

    @Secured({"PROVIDER, ADMIN"})
    @RequestMapping("/edit/{id}")
    private String editOffer(Model model, @PathVariable Long id, RedirectAttributes redirectAttributes, Principal principal) {
        Optional<Offer> offer = offerService.findOne(id);

        if (offer.isPresent()) {
            Offer receivedOffer = offer.get();

            boolean hasAdminRole = userService.hasRole(principal.getName(), "ROLE_ADMIN");
            if (!receivedOffer.getUser().getUsername().equals(principal.getName()) && !hasAdminRole) {
                redirectAttributes.addFlashAttribute("invalidOwner", "Ponuda s ID = " + id + " ne pripada vašem računu!");
                return OFFER_SEARCH_REDIRECT_NAME;
            }

            EditOfferForm editOfferForm = offerFacade.mapOfferToEditOfferForm(receivedOffer);
            DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
            editOfferForm.setDateAdded(dateFormat.format(receivedOffer.getDateAdded()));

            model.addAttribute("editOfferForm", editOfferForm);

            List<Category> categories = categoryService.findAll();
            model.addAttribute(CATEGORIES_MODEL_ATTRIBUTE_NAME, categories);
        } else {
            redirectAttributes.addFlashAttribute(OFFER_NOT_FOUND_REDIRECT_ATTRIBUTE, getOfferNotFoundRedirectAttribute(id));
            return OFFER_SEARCH_REDIRECT_NAME;
        }

        return EDIT_OFFER_VIEW_NAME;
    }

    @Secured({"PROVIDER, ADMIN"})
    @PostMapping("/edit")
    public String handleEditOfferForm(@RequestParam("file") MultipartFile file, @Valid @ModelAttribute("editOfferForm") EditOfferForm editOfferForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model, Principal principal) throws ParseException, IOException {
        if (bindingResult.hasErrors()) {
            model.addAttribute(CATEGORIES_MODEL_ATTRIBUTE_NAME, categoryService.findAll());
            return EDIT_OFFER_VIEW_NAME;
        }

        Offer offer = offerService.editOffer(editOfferForm, UtilityClass.convertByteArrayToBase64String(file.getBytes(), file.getContentType()), file.getName(), (int) file.getSize(), principal.getName());
        redirectAttributes.addFlashAttribute("editSuccess", "Uređivanje ponude je uspjelo!");

        log.info("---> Successfully edited offer entity with ID = " + offer.getId() + " ...");

        return OFFER_DETAILS_REDIRECT_NAME + offer.getId();
    }

    @GetMapping("/search")
    private String searchOffer(Model model) {
        model.addAttribute(CATEGORIES_MODEL_ATTRIBUTE_NAME, categoryService.findAll());
        model.addAttribute("topOffers", offerService.findAll());

        model.addAttribute("offers", new ArrayList<Offer>());
        model.addAttribute("searchOfferForm", new SearchOfferForm());
        return SEARCH_OFFER_VIEW_NAME;
    }

    @PostMapping("/search")
    private String findSearchResults(@Valid @ModelAttribute("searchOfferForm") SearchOfferForm
                                             searchOfferForm, Model model, BindingResult bindingResult, SessionStatus status) {

        if (bindingResult.hasErrors()) {
            return SEARCH_OFFER_VIEW_NAME;
        }
        log.info("---> Fetching offers ...");
        List<Offer> offerResults = offerService.findOffers(searchOfferForm);
        model.addAttribute("offers", offerResults);
        if (offerResults.isEmpty()) {
            model.addAttribute("noResults", true);
            log.info("---> No results!");
        }
        status.setComplete();
        model.addAttribute("titleResults", "Rezultati pretrage:");
        return SEARCH_OFFER_VIEW_NAME;
    }



    @RequestMapping("/details/{id}")
    private String showDetails(Principal principal, Model model, @PathVariable Long id, RedirectAttributes redirectAttributes) {
        Optional<Offer> offer = offerService.findOne(id);

        if (offer.isPresent()) {
            //double average = feedbackService.average(id); prosjecna ocjena
            ReservationForm reservationForm = new ReservationForm(offer.get().getId(), null, null);
            model.addAttribute("offer", offer.get());
            model.addAttribute("reservationForm", reservationForm);
            fetchOfferLogMessage(id);
        } else {
            redirectAttributes.addFlashAttribute(OFFER_NOT_FOUND_REDIRECT_ATTRIBUTE, getOfferNotFoundRedirectAttribute(id));
            return OFFER_SEARCH_REDIRECT_NAME;
        }

        return DETAILS_VIEW_NAME;
    }


    @Secured({"PROVIDER, ADMIN"})
    @GetMapping("/updateActivity/{id}")
    private String updateActivity(Model model, @PathVariable Long id, RedirectAttributes redirectAttributes) {
        Optional<Offer> offer = offerService.findOne(id);
        if (offer.isPresent()) {
            model.addAttribute("feedbacks", offer.get().getFeedbacks());
            fetchOfferLogMessage(id);

            Offer receivedOffer = offer.get();

            if (receivedOffer.isActive()) {
                receivedOffer.setActive(false);
            } else {
                receivedOffer.setActive(true);
            }

            offerService.save(receivedOffer);

            log.info("---> Updating activity (setting to {}) for offer with id = {}", receivedOffer.isActive(), receivedOffer.getId());

            ReservationForm reservationForm = new ReservationForm(offer.get().getId(), null, null);
            model.addAttribute("reservationForm", reservationForm);
            model.addAttribute(receivedOffer);

        } else {
            redirectAttributes.addFlashAttribute(OFFER_NOT_FOUND_REDIRECT_ATTRIBUTE, getOfferNotFoundRedirectAttribute(id));
            return OFFER_SEARCH_REDIRECT_NAME;
        }

        return DETAILS_VIEW_NAME;
    }

    @Secured({"PROVIDER, ADMIN"})
    @RequestMapping("/myOffers")
    private ModelAndView showMyOffers(Model model, Principal principal) {
        User currentUser = userService.findByUsername(principal.getName());
        List<Offer> offers = offerService.findOffersByUserOrderByDateAdded(currentUser);
        model.addAttribute("myOffers", offers);
        return new ModelAndView(MY_OFFERS_VIEW_NAME);
    }

    //logger messages
    private void fetchOfferLogMessage(Long id) {
        log.info("---> Fetching offer entity with ID = " + id + " and all its children from the database ...");
    }

    private void createOfferLogMessage(Long id){
        log.info("---> Successfully created offer entity with ID = " + id + " ...");
    }

    private String getOfferNotFoundRedirectAttribute(Long id) {
        return "Ponuda s ID = " + id + " nije pronađena!";
    }

    // form validators
    @InitBinder("newOfferForm")
    public void addNewOfferFormValidator(WebDataBinder dataBinder) {
        dataBinder.addValidators(newOfferFormValidator);
    }

    @InitBinder("searchOfferForm")
    public void searchOfferFormValidator(WebDataBinder dataBinder) {
        dataBinder.addValidators(searchOfferFormValidator);
    }

    @InitBinder("editOfferForm")
    public void addEditOfferFormValidator(WebDataBinder dataBinder) {
        dataBinder.addValidators(editOfferFormValidator);
    }
}
