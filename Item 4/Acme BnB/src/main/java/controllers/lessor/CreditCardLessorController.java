package controllers.lessor;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import services.CreditCardService;
import services.CustomerService;
import services.LessorService;
import controllers.AbstractController;
import domain.CreditCard;
import domain.Lessor;

@Controller
@RequestMapping("/creditCard/lessor")
public class CreditCardLessorController extends AbstractController {
	
	// Services -------------------------------------------------------------
	
	@Autowired
	private CreditCardService creditCardService;
	
	@Autowired
	private CustomerService customerService;
	
	// Constructors -----------------------------------------------------------
	
	public CreditCardLessorController(){
		super();
	}
	
	// Create --------------------------------------------------------------------
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		CreditCard creditCard = creditCardService.create();
		Lessor lessor= (Lessor) customerService.findActorByPrincial();
		lessor.setCreditCard(creditCard);
		result = createEditModelAndView(creditCard);
		return result;
		
	}
	// View ---------------------------------------------------------------

	@RequestMapping(value = "/myCreditCard", method = RequestMethod.GET)
	public ModelAndView view() {
		ModelAndView result;
		result = new ModelAndView("creditCard/lessor/view");
		Lessor lessor= (Lessor) customerService.findActorByPrincial();
		result.addObject("creditCard", lessor.getCreditCard());

		return result;
	}
	// Edit ---------------------------------------------------------------
	
	@RequestMapping(value="/edit", method=RequestMethod.GET)
	public ModelAndView edit(@RequestParam int creditCardId){
		ModelAndView result;
		CreditCard creditCard= creditCardService.findOne(creditCardId);
		result = createEditModelAndView(creditCard);
		return result;
	}
	
	// Save ----------------------------------------------------------------
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public @ResponseBody ModelAndView save(@Valid CreditCard creditCard, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors());
			result = createEditModelAndView(creditCard);
		} else {
			try {
				creditCardService.saveForLessor(creditCard);		
				result = new ModelAndView("redirect:../view.do?creditCardId="+creditCard.getId());
			} catch (Throwable oops) {
				result = createEditModelAndView(creditCard, "creditCard.commit.error");				
			}
		}

		return result;
	}
	
	
	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(CreditCard creditCard) {
		ModelAndView result;

		result = createEditModelAndView(creditCard, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(CreditCard creditCard, String message) {
		ModelAndView result;
		result = new ModelAndView("creditCard/lessor/edit");
		result.addObject("creditCard",creditCard);
		result.addObject("message", message);
		return result;
	}

}
