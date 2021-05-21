package pe.com.uba.security;


import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

class CustomRequestCache extends HttpSessionRequestCache {

	@Override
	public void saveRequest(HttpServletRequest request, HttpServletResponse response) { 
		System.out.println(" saveRequest");
		if (!SecurityUtils.isFrameworkInternalRequest(request)) {
			System.out.println(" saveRequest 2");
			super.saveRequest(request, response);
		}
	}

}
