package intelli.crawler.common.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class IocManager 
{
	private static final Logger LOG = LoggerFactory.getLogger(IocManager.class);
	
	protected  static  ApplicationContext context ;
	
	private static  IocManager  instance = new IocManager();

	private  IocManager()
    {
        try
        {
            context =  new ClassPathXmlApplicationContext(new String[] { "conf/mybatis/spring-context.xml" });
         }
        catch(Throwable ex)
        {
            LOG.error("初始化 Ioc 容器出错,参数文件：conf/mybatis/mysql-spring-context.xml ！error:" ,ex);
            throw new IllegalArgumentException(ex);
        }
    }
   public static IocManager getInstance()
    {
        return instance;
    }
    /**
     * 根据bean 名称 ，返回具体的dao 对象;
     * @param beanName
     * @param t
     * @return
     */
    @SuppressWarnings({ "unchecked" })
  	public <T>  T getDao( String beanName)
    {
        return (T) context.getBean(beanName);
	}

   
    @SuppressWarnings({ "unchecked" })
    public <T>  T getMapper( String beanName)
    {
        return (T) context.getBean(beanName);
    }
    
    
    @SuppressWarnings({ "unchecked" })
	public <T>  T getService( String beanName)
    {
    	return (T) context.getBean(beanName);
	}
}
