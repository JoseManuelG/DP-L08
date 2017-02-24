package controllers.customer;

import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AuditService;
import services.AuditorService;
import services.CommentService;
import services.CustomerService;
import services.PropertyService;
import controllers.AbstractController;
import domain.Audit;
import domain.Auditor;
import domain.Comment;
import domain.Customer;
import domain.Property;

@Controller
@RequestMapping("/comment")
public class CommentCustomerController extends AbstractController {

	@Autowired
	private CommentService commentService ;

	@Autowired
	private CustomerService customerService ;
	
	@Autowired
	private PropertyService propertyService ;
	
	@Autowired
	private ActorService actorService ;
	

	// List --------------------------------------------------------------------
	
	
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int customerId) {
		ModelAndView result;
		Collection<Comment> comments;
		Customer customer= (Customer) actorService.findByPrincipal();
		comments = commentService.findAllCommentsOfACustomer(customer);
		result = new ModelAndView("comment/list");
		result.addObject("comments",comments);
		result.addObject("requestURI","comment/list.do");
		
		return result;
	}
	
	// Create --------------------------------------------------------------------
		@RequestMapping(value = "/customer/create", method = RequestMethod.GET)
		public ModelAndView create(@RequestParam int customerId) {
			Customer recipient = (Customer) actorService.findOne(customerId);
			Customer sender= (Customer) actorService.findByPrincipal();
			ModelAndView result;
			Comment comment;			
			comment = commentService.create();
			comment.setRecipient(recipient);
			comment.setSender(sender);
			result = createEditModelAndView(comment);
			return result;
			
		}
			
	
		// Save ---------------------------------------------------------------
		@RequestMapping(value = "/customer/create", method = RequestMethod.POST, params = "save")
		public @ResponseBody ModelAndView save(@Valid Comment comment, BindingResult binding) {
			ModelAndView result;
			if (binding.hasErrors()) {
				System.out.println(binding.getAllErrors());
				result = createEditModelAndView(comment);
			} else {
				try {
					Date currentMoment = new Date(System.currentTimeMillis() -1000 );
					comment.setPostMoment(currentMoment);
					commentService.save(comment);		
					result = new ModelAndView("redirect:../list.do");
				} catch (Throwable oops) {
					result = createEditModelAndView(comment, "comment.commit.error");				
				}
			}

			return result;
		}
		
	
		// View ---------------------------------------------------------------
		
		@RequestMapping(value = "/view", method = RequestMethod.GET)
		public ModelAndView view(@RequestParam(required = true) Integer commentId) {
			ModelAndView result;
			result = new ModelAndView("comment/view");
			Comment comment  = commentService.findOne(commentId);
			result.addObject("comment", comment);
			return result;
		}
		
		
		// Ancillary methods ------------------------------------------------------

		protected ModelAndView createEditModelAndView(Comment comment) {
			ModelAndView result;

			result = createEditModelAndView(comment, null);

			return result;
		}

		protected ModelAndView createEditModelAndView(Comment comment, String message) {
			ModelAndView result;
			result = new ModelAndView("comment/customer/create");
			result.addObject("comment",comment);
			result.addObject("message", message);
			return result;
		}


	

}

