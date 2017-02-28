
package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AuditService;
import services.BookService;
import services.FinderService;
import services.InvoiceService;
import services.LessorService;
import services.TenantService;
import controllers.AbstractController;

@Controller
@RequestMapping("/dashboard/administrator")
public class DashboardAdministratorController extends AbstractController {

	@Autowired
	private FinderService	finderService;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private BookService		bookService;

	@Autowired
	private LessorService	lessorService;

	@Autowired
	private TenantService	tenantService;

	@Autowired
	private InvoiceService	invoiceService;

	@Autowired
	private AuditService	auditService;


	// List --------------------------------------------------------------------

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboard() {
		ModelAndView result;

		result = new ModelAndView("dashboard/administrator/dashboard");
		//Queries 1-11
		result.addObject("AverageAcceptedBooksPerLessor", bookService.getAverageAcceptedBooksPerLessor());
		result.addObject("AverageDeniedBooksPerLessor", bookService.getAverageDeniedBooksPerLessor());
		result.addObject("AverageAcceptedBooksPerTenant", bookService.getAverageAcceptedBooksPerTenant());
		result.addObject("AverageDeniedBooksPerTenant", bookService.getAverageDeniedBooksPerTenant());
		result.addObject("LessorWithMoreAcceptedBooks", lessorService.getLessorWithMoreAcceptedBooks());
		result.addObject("LessorWithMoreDeniedBooks", lessorService.getLessorWithMoreDeniedBooks());
		result.addObject("LessorWithMorePendingBooks", lessorService.getLessorWithMorePendingBooks());
		result.addObject("TenantWithMoreAcceptedBooks", tenantService.getTenantWithMoreAcceptedBooks());
		result.addObject("TenantWithMoreDeniedBooks", tenantService.getTenantWithMoreDeniedBooks());
		result.addObject("TenantWithMorePendingBooks", tenantService.getTenantWithMorePendingBooks());
		result.addObject("LessorWithMaxAcceptedVersusTotalBooksRatio", lessorService.getLessorWithMaxAcceptedVersusTotalBooksRatio());
		result.addObject("LessorWithMinAcceptedVersusTotalBooksRatio", lessorService.getLessorWithMinAcceptedVersusTotalBooksRatio());
		result.addObject("TenantWithMaxAcceptedVersusTotalBooksRatio", tenantService.getTenantWithMaxAcceptedVersusTotalBooksRatio());
		result.addObject("TenantWithMinAcceptedVersusTotalBooksRatio", tenantService.getTenantWithMinAcceptedVersusTotalBooksRatio());
		result.addObject("AverageResultsPerFinder", finderService.getAverageResultsPerFinder());
		result.addObject("MinimumResultsPerFinder", finderService.getMinimumResultsPerFinder());
		result.addObject("MaximumResultsPerFinder", finderService.getMaximumResultsPerFinder());
		//Queries 17-21 
		result.addObject("MinimumSocialIdentitiesPerActor", actorService.getMinimumSocialIdentitiesPerActor());
		result.addObject("AverageSocialIdentitiesPerActor", actorService.getAverageSocialIdentitiesPerActor());
		result.addObject("MaximumSocialIdentitiesPerActor", actorService.getMaximumSocialIdentitiesPerActor());
		result.addObject("MinimumInvoicesPerTenant", invoiceService.getMinimumInvoicesPerTenant());
		result.addObject("AverageInvoicesPerTenant", invoiceService.getAverageInvoicesPerTenant());
		result.addObject("MaximumInvoicesPerTenant", invoiceService.getMaximumInvoicesPerTenant());
		result.addObject("TotalDueMoneyOfInvoices", invoiceService.getTotalDueMoneyOfInvoices());
		result.addObject("AverageRequestsWithAuditsVersusNoAudits", bookService.getAverageRequestsWithAuditsVersusNoAudits());
		result.addObject("MinimumAuditsPerProperty", auditService.getMinimumAuditsPerProperty());
		result.addObject("AverageAuditsPerProperty", auditService.getAverageAuditsPerProperty());
		result.addObject("MaximumAuditsPerProperty", auditService.getMaximumAuditsPerProperty());

		result.addObject("requestURI", "dashboard/administrator/dashboard.do");

		return result;
	}
}
