package cn.ms.sys.exception;

/**
 * 自定义异常-实体类
 *
 */
public class BusinessException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private String msg;
	private Exception exception;

	public BusinessException(String msg) {
		this.msg = msg;
	}

	public BusinessException(String msg, Exception e) {
		this.msg = msg;
		this.exception = e;
	}

	public String getMsg() {
		return msg;
	}

	public Exception getException() {
		return exception;
	}
}
