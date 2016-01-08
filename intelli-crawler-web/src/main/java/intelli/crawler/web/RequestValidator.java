package intelli.crawler.web;

/**
 * 请求验证组件;
 * @author penglong
 *
 * @param <T>
 */
public interface RequestValidator<T>
{
	void validate(T t) throws ValidateException ;
}
