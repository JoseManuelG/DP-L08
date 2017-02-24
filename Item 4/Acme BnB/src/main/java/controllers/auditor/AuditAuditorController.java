package controllers.auditor;

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
import services.PropertyService;
import controllers.AbstractController;
import domain.Audit;
import domain.Auditor;
import domain.Property;

@Controller
@RequestMapping("/audit")
public class AuditAuditorController extends AbstractController {

	@Autowired
	private AuditService auditService ;

	@Autowired
	private AuditorService auditorService ;
	
	@Autowired
	private PropertyService propertyService ;
	
	@Autowired
	private ActorService actorService ;
	

	// List --------------------------------------------------------------------
	
	@RequestMapping(value = "/propertylist", method = RequestMethod.GET)
	public ModelAndView propertylist(@RequestParam int propertyId) {
		ModelAndView result;
		Collection<Audit> audits;
		
		audits = auditService.findAuditsForProperty(propertyService.findOne(propertyId));
		result = new ModelAndView("audit/list");
		result.addObject("audits",audits);
		result.addObject("requestURI","audit/propertylist.do");
		
		return result;
	}
	
	@RequestMapping(value = "/auditor/auditorlist", method = RequestMethod.GET)
	public ModelAndView auditorlist() {
		ModelAndView result;
		Collection<Audit> audits;
		Auditor auditor = auditorService.findActorByPrincial();
		audits = auditService.findAuditsForAuditor(auditorService.findOne(auditor.getId()));
		result = new ModelAndView("audit/auditor/auditorlist");
		result.addObject("audits",audits);
		result.addObject("requestURI","audit/auditor/auditorlist.do");
		
		return result;
	}
	
	// Create --------------------------------------------------------------------
		@RequestMapping(value = "/auditor/create", method = RequestMethod.GET)
		public ModelAndView create(@RequestParam int propertyId) {
			Auditor auditor= (Auditor) actorService.findByPrincipal();
			ModelAndView result;
			Audit audit;
			if(auditService.checkUnique(propertyService.findOne(propertyId), auditor)){
				
				audit = auditService.create();
				
				Date currentMoment = new Date(System.currentTimeMillis() -1000 );
				audit.setAuditor(auditor);
				audit.setWritingMoment(currentMoment);
				
			}else{
				audit=auditService.getAuditForPropertyAndAuditor(propertyService.findOne(propertyId), auditor);
			}
			result = createEditModelAndView(audit);
			return result;
			
		}
			
		// Edit ---------------------------------------------------------------
		@RequestMapping(value="/auditor/edit", method=RequestMethod.GET)
		public ModelAndView edit(@RequestParam int auditId){
			ModelAndView result;
			Audit audit = auditService.findOne(auditId);
			result = createEditModelAndView(audit);
			return result;
		}
		// Save ---------------------------------------------------------------
		@RequestMapping(value = "/auditor/edit", method = RequestMethod.POST, params = "save")
		public @ResponseBody ModelAndView save(@Valid Audit audit, BindingResult binding) {
			ModelAndView result;
			if (binding.hasErrors()) {
				System.out.println(binding.getAllErrors());
				result = createEditModelAndView(audit);
			} else {
				try {
					auditService.save(audit);		
					result = new ModelAndView("redirect:../auditorlist.do");
				} catch (Throwable oops) {
					result = createEditModelAndView(audit, "audit.commit.error");				
				}
			}

			return result;
		}
		
		// Delete -------------------------------------------------------------
		
		@RequestMapping(value = "/auditor/edit", method = RequestMethod.POST, params = "delete")
		public ModelAndView delete(Audit audit, BindingResult binding) {
			ModelAndView result;

			try {			
				auditService.delete(audit);
				result = new ModelAndView("redirect:../auditorlist.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(audit, "audit.commit.error");
			}

			return result;
		}
		
		// View ---------------------------------------------------------------
		
		@RequestMapping(value = "/view", method = RequestMethod.GET)
		public ModelAndView view(@RequestParam(required = true) Integer auditId) {
			ModelAndView result;
			result = new ModelAndView("audit/view");
			Audit audit  = auditService.findOne(auditId);
			result.addObject("audit", audit);
			return result;
		}
		
		
		// Ancillary methods ------------------------------------------------------

		protected ModelAndView createEditModelAndView(Audit audit) {
			ModelAndView result;

			result = createEditModelAndView(audit, null);

			return result;
		}

		protected ModelAndView createEditModelAndView(Audit audit, String message) {
			ModelAndView result;
			result = new ModelAndView("audit/auditor/edit");
			result.addObject("audit",audit);
			result.addObject("message", message);
			return result;
		}


	

}

