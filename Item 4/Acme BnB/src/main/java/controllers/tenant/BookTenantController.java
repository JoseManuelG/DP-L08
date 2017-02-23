package controllers.tenant;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BookService;
import services.CreditCardService;
import services.TenantService;
import controllers.AbstractController;
import domain.Book;
import forms.BookForm;

@Controller
@RequestMapping("/book/tenant")
public class BookTenantController extends AbstractController {

	@Autowired
	private TenantService tenantService;

	@Autowired
	private BookService bookService;

	@Autowired
	private CreditCardService creditCardService;
	

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Book> books;
		Map<Integer,String> maskedCards;
		
		books = tenantService.findAllBooksByPrincipal();
		maskedCards = creditCardService.maskCreditCardsFromBooks(books);
		
		result = new ModelAndView("book/list");
		result.addObject("books",books);
		result.addObject("maskedCards",maskedCards);
		result.addObject("requestURI","book/tenant/list.do");
		
		return result;
	}
	

	@RequestMapping(value = "/book", method = RequestMethod.GET)
	public ModelAndView book(@RequestParam int propertyId) {
		ModelAndView result;
		BookForm bookForm;
		
		bookForm = new BookForm();
		bookForm.setPropertyId(propertyId);
		result = createEditModelAndView(bookForm);
		
		return result;
	}
	

	@RequestMapping(value = "/book", method = RequestMethod.POST, params = "book")
	public ModelAndView edit(BookForm bookForm, BindingResult bindingResult) {
		ModelAndView result;
		Book book;
		
		book = bookService.reconstruct(bookForm, bindingResult);
		if (bindingResult.hasErrors()){
			result = createEditModelAndView(bookForm);
		} else {
			try{
				bookService.save(book);
				result = new ModelAndView("redirect:list.do");
			} catch (IllegalArgumentException e) {
				result = createEditModelAndView(bookForm, e.getMessage());
			}
		}
		
		return result;
	}

	protected ModelAndView createEditModelAndView(BookForm bookForm) {
		ModelAndView result;

		result = createEditModelAndView(bookForm, null);
		
		return result;
	}	
	
	protected ModelAndView createEditModelAndView(BookForm bookForm, String message) {
		ModelAndView result;

		result = new ModelAndView("book/edit");
		result.addObject("bookForm", bookForm);
		result.addObject("message", message);

		return result;
	}
	
}

