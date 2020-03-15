package cn.ms.common.schema;

import lombok.Data;

@Data
public class Progress {
	private long bytesRead;
	private long contentLength;
	private long items;
}
