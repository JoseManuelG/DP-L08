package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import services.CommentService;
import services.CustomerService;
import services.LessorService;
import domain.Customer;
import domain.Lessor;
import forms.ActorForm;

@Controller
@RequestMapping("/lessor")
public class LessorController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private LessorService lessorService;
	
	@Autowired
	private CustomerService customerService;
	@Autowired
	private CommentService commentService;


	// Constructors -----------------------------------------------------------

	public LessorController() {
		super();
	}



	// View ---------------------------------------------------------------

	@RequestMapping(value = "/myProfile", method = RequestMethod.GET)
	public ModelAndView view() {
		ModelAndView result;
		result = new ModelAndView("lessor/view");
		
		
		
		Lessor lessor  =  lessorService.findByPrincipal();
		
	
		result.addObject("lessor", lessor);
		result.addObject("properties", lessor.getLessorProperties());
		result.addObject("comments", commentService.findAllCommentsOfACustomer((Customer)lessor));
		result.addObject("socialIdentities",lessor.getSocialIdentities());
		result.addObject("requestURI","lessor/myProfile.do");
		result.addObject("esMiPerfil",true);
		return result;
	}
	
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(int lessorId) {
		ModelAndView result;
		result = new ModelAndView("lessor/view");
		
		
		
		Lessor lessor  =  lessorService.findOne(lessorId);

		
		result.addObject("lessor", lessor);
		result.addObject("properties", lessor.getLessorProperties());
		result.addObject("comments", lessor.getComments());
		result.addObject("requestURI","lessor/view.do");
		result.addObject("socialIdentities",lessor.getSocialIdentities());
		result.addObject("esMiPerfil",false);
		return result;
	}

	// Edit ---------------------------------------------------------------
/*
	@RequestMapping(value="/edit", method=RequestMethod.GET)
	public ModelAndView edit(){
		ModelAndView result;
		Lessor lessor  = lessorService.findByPrincipal();
		ActorForm actorForm;
		result = createEditModelAndView(lessor);
		return result;
	}
	
	// Save -------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public @ResponseBody ModelAndView save( ActorForm lessor, BindingResult binding) {
		ModelAndView result;
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		if (binding.hasErrors()) {
			result = createEditModelAndView(lessor);
		} else {
			try {
				lessorService.save(lessor);
				result = new ModelAndView("../");
			
			} catch (Throwable oops) {
				result = createEditModelAndView(lessor, "lessor.commit.error");
			}
		}

		return result;
	}
	
	// Delete -------------------------------------------------------------
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Lessor lessor, BindingResult binding) {
		ModelAndView result;

		try {			
			lessorService.delete(lessor);
			result = new ModelAndView("../");
		} catch (Throwable oops) {
			result = createEditModelAndView(lessor, "lessor.commit.error");
		}

		return result;
	}
*/
	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(Lessor lessor) {
		ModelAndView result;

		result = createEditModelAndView(lessor, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Lessor lessor, String message) {
		ModelAndView result;
		result = new ModelAndView("lessor/edit");
		result.addObject("lessor", lessor);
		result.addObject("message", message);

		return result;
	}

}