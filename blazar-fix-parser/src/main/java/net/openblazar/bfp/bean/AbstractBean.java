package net.openblazar.bfp.bean;

import com.google.inject.Injector;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import java.io.Serializable;

/**
 * @author Wojciech Zankowski
 */
public abstract class AbstractBean implements Serializable {

	private Injector injector;

	public Injector getInjector() {
		if (injector == null) {
			ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance()
					.getExternalContext().getContext();
			injector = (Injector) servletContext.getAttribute(Injector.class.getName());
		}
		return injector;
	}

	public void setInjector(Injector injector) {
		this.injector = injector;
	}

	public void init() {
		getInjector().injectMembers(this);
	}

}
