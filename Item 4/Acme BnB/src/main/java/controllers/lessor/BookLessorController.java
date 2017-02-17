package controllers.lessor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BookService;
import services.LessorService;
import controllers.AbstractController;
import domain.Book;

@Controller
@RequestMapping("/book/lessor")
public class BookLessorController extends AbstractController {

	@Autowired
	LessorService lessorService ;

	@Autowired
	BookService bookService ;
	

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Book> books;
		
		books = lessorService.findAllBooksByPrincipal();
		result = new ModelAndView("book/list");
		result.addObject("books",books);
		result.addObject("requestURI","book/lessor/list.do");
		
		return result;
	}

	@RequestMapping(value = "/acceptBook", method = RequestMethod.GET)
	public ModelAndView acceptBook(@RequestParam int bookId) {
		ModelAndView result;
		
		bookService.acceptBook(bookId);
		result = new ModelAndView("redirect:list.do");
		
		return result;
	}
	
	@RequestMapping(value = "/denyBook", method = RequestMethod.GET)
	public ModelAndView denyBook(@RequestParam int bookId) {
		ModelAndView result;
		
		bookService.denyBook(bookId);
		result = new ModelAndView("redirect:list.do");
		
		return result;
	}
	

}

