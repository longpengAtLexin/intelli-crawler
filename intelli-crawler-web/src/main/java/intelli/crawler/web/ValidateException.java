package intelli.crawler.web;

public class ValidateException extends RuntimeException
{
	
	private static final long serialVersionUID = 7439804442942320334L;

	public ValidateException() {
		super();
		
	}
	
	public ValidateException(String message) {
        super(message);
    }
	
	public ValidateException(String message, Throwable cause) {
        super(message, cause);
    }
	
	public ValidateException(Throwable cause) {
        super(cause);
    }

}
