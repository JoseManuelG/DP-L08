package controllers.customer;

import java.util.ArrayList;
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

import repositories.ComentableRepository;
import security.Authority;
import services.ActorService;
import services.CommentService;
import controllers.AbstractController;
import domain.Actor;
import domain.Comentable;
import domain.Comment;
import domain.Customer;

@Controller
@RequestMapping("/comment")
public class CommentCustomerController extends AbstractController {

	@Autowired
	private CommentService commentService ;

	@Autowired
	private ComentableRepository comentableRepository;
	
	@Autowired
	private ActorService actorService ;
	

	// List --------------------------------------------------------------------
	
	
	
	
	
	// Create --------------------------------------------------------------------
		@RequestMapping(value = "/customer/create", method = RequestMethod.GET)
		public ModelAndView create(@RequestParam int customerId) {
			Collection<Comentable> recipient2 =  comentableRepository.findAll();
			Comentable recipient =  comentableRepository.findOne(customerId);
			Customer sender= (Customer) actorService.findByPrincipal();
			ModelAndView result;
			Comment comment;
			comment = commentService.create();
			comment.setRecipient(recipient);
			comment.setSender(sender);
			comment.setRecipient(recipient);
			Date currentMoment = new Date(System.currentTimeMillis() -10000 );
			comment.setPostMoment(currentMoment);
			result = createEditModelAndView(comment);
			return result;
			
		}
			
	
		// Save ---------------------------------------------------------------
		@RequestMapping(value = "/customer/create", method = RequestMethod.POST, params = "save")
		public @ResponseBody ModelAndView save( @Valid Comment comment, BindingResult binding) {
			ModelAndView result;
			if (binding.hasErrors()) {
				System.out.println(binding.getAllErrors());
				result = createEditModelAndView(comment);
			} else {
				try {
					Date currentMoment = new Date(System.currentTimeMillis() -10000 );
					comment.setPostMoment(currentMoment);
					commentService.save(comment);	
					Actor actor=(Actor)comment.getRecipient();
					ArrayList<Authority> authorities=new ArrayList<Authority>();
					authorities.addAll(	actor.getUserAccount().getAuthorities());
					String aux= authorities.get(0).getAuthority().toLowerCase();
					result = new ModelAndView("redirect:../../"+aux+"/view.do?"+aux+"Id="+actor.getId());
				} catch (Throwable oops) {
					result = createEditModelAndView(comment, "comment.commit.error");				
				}
			}

			return result;
		}
		
	
		// View ---------------------------------------------------------------
		
	
		
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

