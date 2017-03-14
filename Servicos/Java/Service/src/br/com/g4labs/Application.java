package br.com.g4labs;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;

import br.com.g4labs.route.Tasks;

@ApplicationPath("api")
public class Application extends javax.ws.rs.core.Application {

	@Override
	public Set<Class<?>> getClasses() {

		Set<Class<?>> clazz = new HashSet<Class<?>>();
		clazz.add(Tasks.class);

		return super.getClasses();
	}

}
