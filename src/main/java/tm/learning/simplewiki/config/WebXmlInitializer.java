package tm.learning.simplewiki.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

//import org.springframework.web.servlet.support.*;



public class WebXmlInitializer extends AbstractAnnotationConfigDispatcherServletInitializer  {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { RootServletConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() { 
		return null;
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}
}
