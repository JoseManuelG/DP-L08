package controllers.tenant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BookService;
import services.InvoiceService;
import controllers.AbstractController;
import domain.Book;
import domain.Invoice;

@Controller
@RequestMapping("/invoice/tenant")
public class BookTenantController extends AbstractController {

	@Autowired
	private BookService bookService;
	
	@Autowired
	private InvoiceService invoiceService;


	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int bookId) {
		ModelAndView result;
		Book book;
		Invoice invoice;
		
		book = bookService.findOne(bookId);
		bookService.checkOwnerTenantIsPrincipal(book);
		
		if (book.getInvoice() != null){
			invoice = book.getInvoice();
		} else {
			invoice = invoiceService.create(book);
		}
		
		result = new ModelAndView("invoice/view");
		result.addObject("invoice",invoice);
		
		return result;
	}
}