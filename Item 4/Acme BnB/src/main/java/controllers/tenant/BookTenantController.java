package controllers.tenant;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.TenantService;
import controllers.AbstractController;
import domain.Book;

@Controller
@RequestMapping("/book/tenant")
public class BookTenantController extends AbstractController {

	@Autowired
	private TenantService tenantService;
	

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Book> books;
		
		books = tenantService.findAllBooksByPrincipal();
		result = new ModelAndView("book/list");
		result.addObject("books",books);
		result.addObject("requestURI","book/lessor/list.do");
		
		return result;
	}
	

}

