package spring.h2.poc;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service
public class BeanUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	private BeanUtil() {
		super();
	}

	public static void autowire(Object classToAutowire, Object... beansToAutowireInClass) {
		for (Object bean : beansToAutowireInClass) {
			if (bean == null) {
				applicationContext.getAutowireCapableBeanFactory().autowireBean(classToAutowire);
			}
		}
	}

	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) {
		BeanUtil.applicationContext = applicationContext;
	}

}
