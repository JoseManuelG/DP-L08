
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;

@Service
@Transactional
public class ActorService {

	@Autowired
	LoginService			loginService;
	@Autowired
	TenantService			tenantService;
	@Autowired
	LessorService			lessorService;
	@Autowired
	AdministratorService	administratorService;
	@Autowired
	AuditorService			auditorService;


	@SuppressWarnings("static-access")
	public Actor findActorByPrincial() {
		UserAccount userAcc = loginService.getPrincipal();
		Actor result = null;

		Authority[] authorities = new Authority[userAcc.getAuthorities().size()];
		authorities = userAcc.getAuthorities().toArray(authorities);

		if (authorities[0].getAuthority().equals(Authority.TENANT)) {
			result = tenantService.findByPrincipal();
		} else if (authorities[0].getAuthority().equals(Authority.LESSOR)) {
			result = lessorService.findByPrincipal();
		} else if (authorities[0].getAuthority().equals(Authority.ADMIN)) {
			result = administratorService.findByPrincipal();
		} else if (authorities[0].getAuthority().equals(Authority.AUDITOR)) {
			result = auditorService.findByPrincipal();
		}
		return result;
	}
	public Actor findOne(int id) {
		Actor result = null;

		try {
			result = auditorService.findOne(id);
		} catch (Exception e) {
		}
		if (result == null) {
			try {
				result = tenantService.findOne(id);
			} catch (Exception e) {
			}
		}
		if (result == null) {
			try {
				result = lessorService.findOne(id);
			} catch (Exception e) {
			}
		}
		if (result == null) {
			try {
				result = administratorService.findOne(id);
			} catch (Exception e) {
			}
		}
		return result;
	}
}
