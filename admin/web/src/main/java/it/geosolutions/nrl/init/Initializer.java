/*
 */
package it.geosolutions.nrl.init;



import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**C
 *
 * @author Lorenzo Natali
 */
public class Initializer implements InitializingBean, ApplicationContextAware {

    
    private ApplicationContext applicationContext; 

    
//     <bean id="restSurvey" class="it.geosolutions.fra2015.services.rest.impl.SurveyServiceImpl">
//<bean id="userService" class="it.geosolutions.fra2015.services.UserServiceImpl">
    
    @Override
    public void afterPropertiesSet() throws Exception {

       
    }

    
    @Override
    public void setApplicationContext(ApplicationContext ac) throws BeansException {
        this.applicationContext = ac;
    }


    
}
