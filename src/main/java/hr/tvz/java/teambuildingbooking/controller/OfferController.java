package hr.tvz.java.teambuildingbooking.controller;

import hr.tvz.java.teambuildingbooking.facade.OfferFacade;
import hr.tvz.java.teambuildingbooking.model.Category;
import hr.tvz.java.teambuildingbooking.model.Offer;
import hr.tvz.java.teambuildingbooking.model.form.EditOfferForm;
import hr.tvz.java.teambuildingbooking.model.form.NewOfferForm;
import hr.tvz.java.teambuildingbooking.model.form.SearchOfferForm;
import hr.tvz.java.teambuildingbooking.service.CategoryService;
import hr.tvz.java.teambuildingbooking.service.OfferService;
import hr.tvz.java.teambuildingbooking.service.UserService;
import hr.tvz.java.teambuildingbooking.validator.EditOfferFormValidator;
import hr.tvz.java.teambuildingbooking.validator.NewOfferFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    private final OfferService offerService;

    private final CategoryService categoryService;

    private final UserService userService;

    private final OfferFacade offerFacade;

    private final NewOfferFormValidator newOfferFormValidator;

    private final EditOfferFormValidator editOfferFormValidator;

    @Autowired
    public OfferController(OfferService offerService, CategoryService categoryService, UserService userService, OfferFacade offerFacade, NewOfferFormValidator newOfferFormValidator, EditOfferFormValidator editOfferFormValidator) {
        this.offerService = offerService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.offerFacade = offerFacade;
        this.newOfferFormValidator = newOfferFormValidator;
        this.editOfferFormValidator = editOfferFormValidator;
    }

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
    public String handleNewOfferForm(@RequestParam("file") MultipartFile file, @Valid @ModelAttribute("newOfferForm") NewOfferForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes, Principal principal, Model model) throws ParseException, IOException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            return NEW_OFFER_VIEW_NAME;
        }

        Offer offer = offerService.createOffer(form, file, principal.getName());
        redirectAttributes.addFlashAttribute("createSuccess", "Dodavanje nove ponude je uspjelo!");

        return "redirect:/offer/details/" + offer.getId();
    }

    @Secured("PROVIDER")
    @RequestMapping("/edit/{id}")
    private String editOffer(Model model, @PathVariable("id") Long id, RedirectAttributes redirectAttributes, Principal principal) {
        Optional<Offer> offer = offerService.findOne(id);

        if (offer.isPresent()) {
            Offer receivedOffer = offer.get();

            boolean hasAdminRole = userService.hasRole(principal.getName(), "ROLE_ADMIN");
            if (!receivedOffer.getUser().getUsername().equals(principal.getName()) && !hasAdminRole) {
                redirectAttributes.addFlashAttribute("invalidOwner", "Ponuda s ID = " + id + " ne pripada vašem računu!");
                return "redirect:/offer/search";
            }

            EditOfferForm editOfferForm = offerFacade.mapOfferToEditOfferForm(receivedOffer);

            DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
            editOfferForm.setDateAdded(dateFormat.format(receivedOffer.getDateAdded()));
            model.addAttribute("editOfferForm", editOfferForm);

            List<Category> categories = categoryService.findAll();
            model.addAttribute("categories", categories);
        } else {
            redirectAttributes.addFlashAttribute("offerNotFound", "Ponuda s ID = " + id + " nije pronađena!");
            return "redirect:/offer/search";
        }

        return EDIT_OFFER_VIEW_NAME;
    }

    @Secured("PROVIDER")
    @PostMapping("/edit")
    public String handleEditOfferForm(@RequestParam("file") MultipartFile file, @Valid @ModelAttribute("editOfferForm") EditOfferForm editOfferForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model, Principal principal) throws ParseException, IOException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            return EDIT_OFFER_VIEW_NAME;
        }

        Offer offer = offerService.editOffer(editOfferForm, file, principal.getName());
        redirectAttributes.addFlashAttribute("editSuccess", "Uređivanje ponude je uspjelo!");

        return "redirect:/offer/details/" + offer.getId();
    }

    @GetMapping("/search")
    private String searchOffer(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("topOffers", offerService.findTopOffers());
        model.addAttribute("searchOfferForm", new SearchOfferForm());
        return SEARCH_OFFER_VIEW_NAME;
    }

    @PostMapping("/search")
    private String findSearchResults(@Valid @ModelAttribute("searchOfferForm") SearchOfferForm searchOfferForm, Model model) {
        model.addAttribute("offers", offerService.findOffers(searchOfferForm));
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

    // form validators
    @InitBinder("newOfferForm")
    public void addNewOfferFormValidator(WebDataBinder dataBinder) {
        dataBinder.addValidators(newOfferFormValidator);
    }

    // form validators
    @InitBinder("editOfferForm")
    public void addEditOfferFormValidator(WebDataBinder dataBinder) {
        dataBinder.addValidators(editOfferFormValidator);
    }

}
