package hr.tvz.java.teambuildingbooking.controller;

import hr.tvz.java.teambuildingbooking.model.Offer;
import hr.tvz.java.teambuildingbooking.model.form.SearchOfferForm;
import hr.tvz.java.teambuildingbooking.service.CategoryService;
import hr.tvz.java.teambuildingbooking.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/offer")
@SessionAttributes({"offers", "searchOfferForm"})
public class OfferController {

    private static final String NEW_OFFER_VIEW_NAME = "offer/new-offer";
    private static final String SEARCH_OFFER_VIEW_NAME = "offer/search-offer";
    private static final String SEARCH_RESULTS_VIEW_NAME = "offer/search-results";
    private static final String DETAILS_VIEW_NAME = "offer/details";
    private static final String REVIEWS_VIEW_NAME = "offer/reviews";
    private static final String EDIT_OFFER_VIEW_NAME = "offer/edit-offer";
    private static final String MY_OFFERS_VIEW_NAME = "offer/myOffers";

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    private final OfferService offerService;

    private final CategoryService categoryService;

    private OfferService offerService;
    private final UserService userService;

    private final OfferFacade offerFacade;

    private final NewOfferFormValidator newOfferFormValidator;

    private final EditOfferFormValidator editOfferFormValidator;

    private final OfferPictureService offerPictureService;

    private CategoryService categoryService;
    @Autowired
    public OfferController(OfferService offerService, CategoryService categoryService, UserService userService, OfferFacade offerFacade, NewOfferFormValidator newOfferFormValidator, EditOfferFormValidator editOfferFormValidator, OfferPictureService offerPictureService) {
        this.offerService = offerService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.offerFacade = offerFacade;
        this.newOfferFormValidator = newOfferFormValidator;
        this.editOfferFormValidator = editOfferFormValidator;
        this.offerPictureService = offerPictureService;
    }

    private SearchOfferFormValidator searchOfferFormValidator;

    @Autowired
    public OfferController(OfferService offerService, CategoryService categoryService, SearchOfferFormValidator searchOfferFormValidator) {
        this.offerService = offerService;
        this.categoryService = categoryService;
        this.searchOfferFormValidator = searchOfferFormValidator;
    }

    @Secured({"PROVIDER, ADMIN"})
    @RequestMapping("/new")
    private String newOffer(Model model) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("newOfferForm", new NewOfferForm());
        return NEW_OFFER_VIEW_NAME;
    }

    @Secured({"PROVIDER, ADMIN"})
    @PostMapping("/new")
    public String handleNewOfferForm(@RequestParam("file") MultipartFile file, @Valid @ModelAttribute("newOfferForm") NewOfferForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes, Principal principal, Model model) throws ParseException, IOException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            return NEW_OFFER_VIEW_NAME;
        }

        Offer offer = offerService.createOffer(form, file, principal.getName());
        redirectAttributes.addFlashAttribute("createSuccess", "Dodavanje nove ponude je uspjelo!");

        log.info("---> Successfully created offer entity with ID = " + offer.getId() + " ...");

        return "redirect:/offer/details/" + offer.getId();
    }

    @Secured({"PROVIDER, ADMIN"})
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

    @Secured({"PROVIDER, ADMIN"})
    @PostMapping("/edit")
    public String handleEditOfferForm(@RequestParam("file") MultipartFile file, @Valid @ModelAttribute("editOfferForm") EditOfferForm editOfferForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model, Principal principal) throws ParseException, IOException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            return EDIT_OFFER_VIEW_NAME;
        }

        Offer offer = offerService.editOffer(editOfferForm, file, principal.getName());
        redirectAttributes.addFlashAttribute("editSuccess", "Uređivanje ponude je uspjelo!");

        log.info("---> Successfully edited offer entity with ID = " + offer.getId() + " ...");

        return "redirect:/offer/details/" + offer.getId();
    }

    @Secured({"PROVIDER, ADMIN"})
    @GetMapping("/delete/{id}")
    public String deleteOffer(@PathVariable("id") Long id, Principal principal, RedirectAttributes redirectAttributes) {
        Optional<Offer> offer = offerService.findOne(id);

        if (offer.isPresent()) {
            Offer receivedOffer = offer.get();

            boolean hasAdminRole = userService.hasRole(principal.getName(), "ROLE_ADMIN");
            if (!receivedOffer.getUser().getUsername().equals(principal.getName()) && !hasAdminRole) {
                redirectAttributes.addFlashAttribute("invalidOwner", "Ponuda s ID = " + id + " ne pripada vašem računu!");
                return "redirect:/offer/search";
            }

            offerService.deleteOfferById(receivedOffer.getId());

            if (receivedOffer.getOfferPicture() != null) {
                offerPictureService.deleteById(receivedOffer.getOfferPicture().getId());
            }

            log.info("---> Deleting offer entity with ID = " + receivedOffer.getId() + " and all its children from the database ...");

            redirectAttributes.addFlashAttribute("offerDeleted", "Ponuda s ID = " + id + " uspješno izbrisana!");
            return "redirect:/offer/search";
        } else {
            redirectAttributes.addFlashAttribute("offerNotFound", "Ponuda s ID = " + id + " nije pronađena!");
            return "redirect:/offer/search";
        }
    }

    @GetMapping("/search")
    private String searchOffer(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("topOffers", offerService.findAll());
        model.addAttribute("searchOfferForm", new SearchOfferForm());
        return SEARCH_OFFER_VIEW_NAME;
    }

    @PostMapping("/search")
    private String findSearchResults(@Valid @ModelAttribute("searchOfferForm") SearchOfferForm
                                             searchOfferForm, Model model, BindingResult bindingResult) throws ParseException {

        if(bindingResult.hasErrors()) {
            return SEARCH_OFFER_VIEW_NAME;
        }
        model.addAttribute("offers", offerService.findOffers(searchOfferForm));
        return SEARCH_RESULTS_VIEW_NAME;
    }

    @RequestMapping("/results")
    private String showResults(Model model, HttpSession session) {

        model.addAttribute("offers", session.getAttribute("offers"));

        return SEARCH_RESULTS_VIEW_NAME;
    }

    @RequestMapping("/details/{id}")
    private String showDetails(Model model, @PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        Optional<Offer> offer = offerService.findOne(id);
        if (offer.isPresent()) {
            model.addAttribute("offer", offer.get());
            log.info("---> Fetching offer entity with ID = " + id + " and all its children from the database ...");
        } else {
            redirectAttributes.addFlashAttribute("offerNotFound", "Ponuda s ID = " + id + " nije pronađena!");
            return "redirect:/offer/search";
        }

        return DETAILS_VIEW_NAME;
    }

    @RequestMapping("/details/{id}/reviews")
    private String showReviews(Model model, @PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        Optional<Offer> offer = offerService.findOne(id);
        if (offer.isPresent()) {
            model.addAttribute("feedbacks", offer.get().getFeedbacks());
            log.info("---> Fetching offer entity with ID = " + id + " and all its children from the database ...");
        } else {
            redirectAttributes.addFlashAttribute("offerNotFound", "Ponuda s ID = " + id + " nije pronađena!");
            return "redirect:/offer/search";
        }

        return REVIEWS_VIEW_NAME;
    }


    @Secured({"PROVIDER, ADMIN"})
    @RequestMapping("/myOffers")
    private ModelAndView showMyOffers(Model model, Principal principal) {
        User currentUser = userService.findByUsername(principal.getName());
        List<Offer> offers = offerService.findOffersByUserOrderByDateAdded(currentUser);
        model.addAttribute("myOffers", offers);
        return new ModelAndView(MY_OFFERS_VIEW_NAME);
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
