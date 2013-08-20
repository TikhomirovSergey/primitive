package org.primitive.logging;

import org.primitive.logging.Log.LogRecWithAttach;

public interface ILogConverter {
	public void convert(LogRecWithAttach record);
}
